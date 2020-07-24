package com.example.contacts.presentation.editcontact

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.contacts.R
import com.example.contacts.databinding.ActivityEditContactBinding
import com.example.contacts.databinding.DialogNoteBinding
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
    }

    private fun initNoteDialog(): AlertDialog {
        val view = LayoutInflater.from(this)
            .inflate(R.layout.dialog_note, null, false)
            .apply {
                etNote.setText(viewModel.contact.value?.note)
            }

        return AlertDialog.Builder(this)
            .setTitle(R.string.title_dialog_add_note)
            .setView(view)
            .setNegativeButton(R.string.button_negative, null)
            .setPositiveButton(R.string.button_positive) { _, _ ->
                viewModel.contact.value?.note = view.etNote.text.toString()
                binding.invalidateAll()
            }.create()
    }
}