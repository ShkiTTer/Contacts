package com.example.contacts.domain.contact

import androidx.lifecycle.LiveData
import com.example.contacts.domain.common.model.Contact

interface IContactUseCase {
    fun getContacts(): LiveData<Result<List<Contact>>>
    fun getContactById(id: Long): LiveData<Result<Contact>>
    fun addContact(contact: Contact): LiveData<Result<Boolean>>
    fun updateContact(contact: Contact): LiveData<Result<Boolean>>
    fun deleteContact(id: Long): LiveData<Result<Boolean>>
}