package com.example.contacts.app

import com.example.contacts.data.ContactsDatabase
import com.example.contacts.data.Database
import com.example.contacts.data.contact.ContactDbRepositoryImpl
import com.example.contacts.domain.contact.ContactUseCaseImpl
import com.example.contacts.domain.contact.IContactDbRepository
import com.example.contacts.domain.contact.IContactUseCase
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

    viewModel { (contactId: Long) ->
        ContactViewModel(
            app = androidApplication(),
            contactUseCase = get(),
            contactId = contactId
        )
    }

    viewModel { (contactId: Long?) ->
        EditContactViewModel(
            app = androidApplication(),
            contactUseCase = get(),
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

private val useCaseModule = module {
    factory<IContactUseCase> {
        ContactUseCaseImpl(
            contactDbRepositoryImpl = get()
        )
    }
}

val koinModule = listOf(databaseModule, repositoryModule, useCaseModule, viewModelModule)