package com.example.contacts.presentation.editcontact

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.contacts.R
import com.example.contacts.databinding.ActivityEditContactBinding
import kotlinx.android.synthetic.main.activity_edit_contact.*
import kotlinx.android.synthetic.main.dialog_note.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditContactActivity : AppCompatActivity() {
    companion object {
        fun newIntent(context: Context) = Intent(context, EditContactActivity::class.java)
    }

    private val viewModel: EditContactViewModel by viewModel()
    private lateinit var binding: ActivityEditContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_contact)

        binding.apply {
            lifecycleOwner = this@EditContactActivity
            contact = viewModel.contact
        }

        initToolbar()
        initView()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationIcon(R.drawable.done)

        toolbar.setNavigationOnClickListener {
            // TODO: Add or update contact
        }
    }

    private fun initView() {
        gNote.setOnClickListener {
            initNoteDialog().show()
        }

        gRingtone.setOnClickListener {
            initRingtoneDialog().show()
        }
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
}