package com.example.contacts.presentation.contact

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.contacts.R
import com.example.contacts.databinding.ActivityContactBinding
import com.example.contacts.presentation.common.extentions.getExtra
import com.example.contacts.presentation.editcontact.EditContactActivity
import kotlinx.android.synthetic.main.activity_contact.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ContactActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_CONTACT_ID = "contact_id"

        fun newIntent(context: Context, contactId: Long) =
            Intent(context, ContactActivity::class.java)
                .putExtra(EXTRA_CONTACT_ID, contactId)
    }

    private lateinit var binding: ActivityContactBinding
    private lateinit var mMenu: Menu

    private val viewModel: ContactViewModel by viewModel {
        parametersOf(getExtra(EXTRA_CONTACT_ID))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact)

        binding.apply {
            lifecycleOwner = this@ContactActivity
            contact = viewModel.contact
        }

        initToolbar()
        initView()
        initObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.contact_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.action_favourite)?.setIcon(
            if (viewModel.isFavourite()) R.drawable.favourite else R.drawable.not_favourite
        )

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()

        binding.contact = viewModel.contact

        viewModel.contact.observe(this, Observer {
            if (it == null) finish()
            else {
                invalidateOptionsMenu()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favourite -> {
                viewModel.changeFavourite().observe(this, Observer {
                    if (it) {
                        invalidateOptionsMenu()
                    }
                })
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationIcon(R.drawable.arrow_back)
    }

    private fun initView() {
        fabEdit.setOnClickListener {
            EditContactActivity.newIntent(this, viewModel.contactId).apply {
                startActivity(this)
            }
        }
    }

    private fun initObserver() {
        viewModel.error.observe(this, Observer {
            initErrorDialog(it).show()
        })
    }

    private fun initErrorDialog(error: String): AlertDialog {
        return AlertDialog.Builder(this)
            .setTitle(R.string.title_dialog_error)
            .setMessage(error)
            .setPositiveButton(R.string.dialog_error_positive_button, null)
            .create()
    }
}