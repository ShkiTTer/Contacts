package com.example.contacts.presentation.editcontact

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contacts.R
import kotlinx.android.synthetic.main.activity_edit_contact.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditContactActivity : AppCompatActivity(R.layout.activity_edit_contact) {
    companion object {
        fun newIntent(context: Context) = Intent(context, EditContactActivity::class.java)
    }

    private val viewModel: EditContactViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar()
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
}