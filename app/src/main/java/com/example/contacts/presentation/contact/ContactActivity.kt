package com.example.contacts.presentation.contact

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.contacts.R
import com.example.contacts.presentation.editcontact.EditContactActivity
import kotlinx.android.synthetic.main.activity_contact.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactActivity : AppCompatActivity(R.layout.activity_contact) {
    companion object {
        fun newIntent(context: Context) = Intent(context, ContactActivity::class.java)
    }

    private val viewModel: ContactViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar()
        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.contact_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationIcon(R.drawable.arrow_back)
    }

    private fun initView() {
        fabEdit.setOnClickListener {
            EditContactActivity.newIntent(this).apply {
                startActivity(this)
            }
        }
    }
}