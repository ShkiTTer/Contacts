package com.example.contacts.presentation.contactslist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.contacts.domain.contact.IContactUseCase

class ContactsListViewModel(
    private val app: Application,
    private val contactUseCase: IContactUseCase
) : AndroidViewModel(app) {

    val contacts
        get() = readContacts()

    private fun readContacts() = contactUseCase.getContacts().switchMap { result ->
        liveData {
            result
                .onSuccess {
                    emit(it)
                }
                .onFailure {

                }
        }
    }
}