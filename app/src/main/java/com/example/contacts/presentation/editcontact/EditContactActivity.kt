package com.example.contacts.presentation.editcontact

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.example.contacts.R
import com.example.contacts.databinding.ActivityEditContactBinding
import com.example.contacts.presentation.common.extentions.getExtra
import com.example.contacts.presentation.common.extentions.requirePermission
import com.example.contacts.presentation.common.extentions.showLongToast
import com.example.contacts.presentation.common.utils.IntentUtils
import kotlinx.android.synthetic.main.dialog_note.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EditContactActivity : AppCompatActivity(), SelectPhotoBottomSheet.OnSelectPhotoListener {
    companion object {
        private const val EXTRA_CONTACT_ID = "contact_id"

        private const val RC_CAMERA = 0
        private const val RC_GALLERY = 1

        fun newIntent(context: Context) =
            Intent(context, EditContactActivity::class.java)

        fun newIntent(context: Context, contactId: Long) =
            Intent(context, EditContactActivity::class.java)
                .putExtra(EXTRA_CONTACT_ID, contactId)
    }

    private val viewModel: EditContactViewModel by viewModel {
        parametersOf(getExtra(EXTRA_CONTACT_ID))
    }

    private lateinit var binding: ActivityEditContactBinding
    private lateinit var photoUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_contact)

        binding.apply {
            lifecycleOwner = this@EditContactActivity
            contact = viewModel.contact

            setTitle(
                if (viewModel.contactId == null)
                    R.string.title_activity_add_contact
                else R.string.title_activity_edit_contact
            )
        }

        initToolbar()
        initView()
        initObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuId = if (viewModel.contactId == null) R.menu.add_menu else R.menu.edit_menu
        menuInflater.inflate(menuId, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> {
                viewModel.deleteContact().observe(this, Observer {
                    if (it) {
                        showLongToast(R.string.message_contact_deleted)
                        finish()
                    }
                })
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) return

        when (requestCode) {
            RC_GALLERY -> {
                data?.data?.let {
                    contentResolver.takePersistableUriPermission(
                        it,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )

                    loadPhoto(it)
                    hideBottomSheet()
                }
            }

            RC_CAMERA -> {
                loadPhoto(photoUri)
                hideBottomSheet()
            }
        }
    }

    override fun takeByCamera() {
        requirePermission(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            {
                val uri = viewModel.createNewFile()
                photoUri = uri

                IntentUtils.createCaptureCameraIntent(packageManager, uri)?.let {
                    startActivityForResult(it, RC_CAMERA)
                }
            },
            {
                initErrorDialog(getString(R.string.error_deny_permission)).show()
            }
        )
    }

    override fun takeByGallery() {
        IntentUtils.createImageGalleryIntent().apply {
            startActivityForResult(this, RC_GALLERY)
        }
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationIcon(R.drawable.done)

        binding.toolbar.setNavigationOnClickListener {
            viewModel.saveContact().observe(this, Observer {
                if (it) {
                    showLongToast(
                        if (viewModel.contactId == null) R.string.message_contact_created
                        else R.string.message_contact_updated
                    )
                    finish()
                }
            })
        }
    }

    private fun initView() {
        binding.apply {
            etPhone.addTextChangedListener(
                PhoneNumberFormattingTextWatcher()
            )

            gNote.setOnClickListener {
                initNoteDialog().show()
            }

            gRingtone.setOnClickListener {
                initRingtoneDialog().show()
            }

            btnChangeAvatar.setOnClickListener {
                showBottomSheet()
            }
        }
    }

    private fun initObserver() {
        viewModel.error.observe(this, Observer {
            initErrorDialog(it).show()
        })
    }

    private fun initNoteDialog(): AlertDialog {
        val view = LayoutInflater.from(this)
            .inflate(R.layout.dialog_note, null, false)
            .apply {
                etNote.setText(viewModel.contact.value?.note)
            }

        return AlertDialog.Builder(this)
            .setTitle(R.string.title_dialog_note)
            .setView(view)
            .setNegativeButton(R.string.dialog_note_button_negative, null)
            .setPositiveButton(R.string.dialog_note_button_positive) { _, _ ->
                viewModel.contact.value?.note = view.etNote.text.toString()
                binding.invalidateAll()
            }.create()
    }

    private fun initRingtoneDialog(): AlertDialog {
        var selectedRingtone = viewModel.ringtoneList.indexOf(viewModel.contact.value?.ringtone)

        return AlertDialog.Builder(this)
            .setTitle(R.string.title_dialog_ringtone)
            .setSingleChoiceItems(
                ArrayAdapter(this, R.layout.dialog_choise_item, viewModel.ringtoneList),
                selectedRingtone
            )
            { _, which ->
                selectedRingtone = which
            }
            .setNegativeButton(R.string.dialog_ringtone_button_negative, null)
            .setPositiveButton(R.string.dialog_ringtone_button_positive)
            { _, _ ->
                viewModel.contact.value?.ringtone = viewModel.ringtoneList[selectedRingtone]
                binding.invalidateAll()
            }
            .create()
    }

    private fun initErrorDialog(error: String): AlertDialog {
        return AlertDialog.Builder(this)
            .setTitle(R.string.title_dialog_error)
            .setMessage(error)
            .setPositiveButton(R.string.dialog_error_positive_button, null)
            .create()
    }

    private fun loadPhoto(uri: Uri) {
        viewModel.contact.value?.avatar = uri.toString()
        binding.invalidateAll()
    }

    private fun showBottomSheet() {
        SelectPhotoBottomSheet.newInstance()
            .show(supportFragmentManager, SelectPhotoBottomSheet.TAG)
    }

    private fun hideBottomSheet() {
        (supportFragmentManager.findFragmentByTag(SelectPhotoBottomSheet.TAG) as? DialogFragment)
            ?.dismiss()
    }
}