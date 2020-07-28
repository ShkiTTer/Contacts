package com.example.contacts.domain.contact

import androidx.lifecycle.LiveData
import com.example.contacts.domain.common.model.Contact

interface IContactListInteractor {
    fun getAllContacts(): LiveData<Result<List<Contact>>>
    fun searchContact(query: String): LiveData<Result<List<Contact>>>
}