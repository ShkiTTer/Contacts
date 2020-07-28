package com.example.contacts.app

import com.example.contacts.data.ContactsDatabase
import com.example.contacts.data.Database
import com.example.contacts.data.contact.ContactDbRepositoryImpl
import com.example.contacts.domain.contact.*
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
            app = androidApplication(),
            contactListInteractor = get()
        )
    }

    viewModel { (contactId: Long) ->
        ContactViewModel(
            app = androidApplication(),
            contactInteractor = get(),
            contactId = contactId
        )
    }

    viewModel { (contactId: Long?) ->
        EditContactViewModel(
            app = androidApplication(),
            contactInteractor = get(),
            contactId = contactId
        )
    }
}

private val databaseModule = module {
    single { Database.build(androidContext()) }
    single { get<ContactsDatabase>().contactDao() }
}

private val repositoryModule = module {
    factory<IContactDbRepository> {
        ContactDbRepositoryImpl(
            contactDao = get()
        )
    }
}

private val interactorModule = module {
    factory<IContactInteractor> {
        ContactInteractorImpl(
            contactDbRepository = get()
        )
    }

    factory<IContactListInteractor> {
        ContactListInteractorImpl(
            contactDbRepository = get()
        )
    }
}

val koinModule = listOf(databaseModule, repositoryModule, interactorModule, viewModelModule)