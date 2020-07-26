package com.example.contacts.domain.contact

import com.example.contacts.domain.common.model.Contact

interface IContactDbRepository {
    fun getContacts(): List<Contact>
    fun getContactById(id: Long): Contact?
    fun addContact(contact: Contact)
    fun updateContact(contact: Contact)
    fun deleteContact(id: Long)
    fun searchContacts(query: String): List<Contact>
}