package com.example.contacts.presentation.editcontact

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.contacts.R
import com.example.contacts.databinding.ActivityEditContactBinding
import kotlinx.android.synthetic.main.activity_edit_contact.*
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