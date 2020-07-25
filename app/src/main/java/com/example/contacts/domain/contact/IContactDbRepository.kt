package com.example.contacts.domain.contact

import androidx.lifecycle.LiveData
import com.example.contacts.domain.common.model.Contact

interface IContactDbRepository {
    fun getContacts(): List<Contact>
    fun getContactById(id: Long): Contact?
    fun addContact(contact: Contact)
    fun updateContact(contact: Contact)
    fun deleteContact(id: Long)
}