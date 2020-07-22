package com.example.contacts.app

import com.example.contacts.presentation.contact.ContactViewModel
import com.example.contacts.presentation.contactslist.ContactsListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val viewModelModule = module {
    viewModel {
        ContactsListViewModel(
            app = androidApplication()
        )
    }

    viewModel {
        ContactViewModel()
    }
}

val koinModule = listOf(viewModelModule)