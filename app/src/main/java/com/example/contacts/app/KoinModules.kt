package com.example.contacts.app

import com.example.contacts.presentation.contacts.ContactsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val viewModelModule = module {
    viewModel {
        ContactsViewModel(
            app = androidApplication()
        )
    }
}

val koinModule = listOf(viewModelModule)