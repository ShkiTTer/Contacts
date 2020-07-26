package com.example.contacts.presentation.contactslist

import android.app.Application
import androidx.lifecycle.*
import com.example.contacts.domain.contact.IContactUseCase

class ContactsListViewModel(
    private val app: Application,
    private val contactUseCase: IContactUseCase
) : AndroidViewModel(app) {

    val searchQuery = MutableLiveData<String>(String())

    val contacts = Transformations.switchMap(searchQuery) {
        if (it.isNullOrEmpty()) {
            readContacts()
        } else searchContacts(it)
    }

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


    private fun searchContacts(query: String) = contactUseCase.searchContacts(query).switchMap { result ->
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