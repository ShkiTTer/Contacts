package com.example.contacts.presentation.contactslist

import android.app.Application
import androidx.lifecycle.*
import com.example.contacts.domain.contact.IContactListInteractor

class ContactsListViewModel(
    private val app: Application,
    private val contactListInteractor: IContactListInteractor
) : AndroidViewModel(app) {

    val searchQuery = MutableLiveData<String>("")

    val contacts
        get() = Transformations.switchMap(searchQuery) {
            if (it.isNullOrEmpty()) {
                readContacts()
            } else searchContacts(it)
        }

    private fun readContacts() = contactListInteractor.getAllContacts().switchMap { result ->
        liveData {
            result
                .onSuccess {
                    emit(it)
                }
                .onFailure {

                }
        }
    }

    private fun searchContacts(query: String) =
        contactListInteractor.searchContact(query).switchMap { result ->
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