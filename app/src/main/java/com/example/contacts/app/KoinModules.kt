package com.example.contacts.app

import com.example.contacts.data.ContactsDatabase
import com.example.contacts.data.Database
import com.example.contacts.presentation.contact.ContactViewModel
import com.example.contacts.presentation.contactslist.ContactsListViewModel
import com.example.contacts.presentation.editcontact.EditContactViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
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

    viewModel {
        EditContactViewModel()
    }
}

private val databaseModule = module {
    single { Database.build(androidContext()) }
    single { get<ContactsDatabase>().contactDao() }
}

val koinModule = listOf(databaseModule, viewModelModule)