package com.example.contacts.data.contact

import com.example.contacts.data.common.converters.toDb
import com.example.contacts.data.common.converters.toDomain
import com.example.contacts.domain.common.model.Contact
import com.example.contacts.domain.contact.IContactDbRepository

class ContactDbRepositoryImpl(
    private val contactDao: ContactDao
) : IContactDbRepository {

    override fun getContacts(): List<Contact> {
        return contactDao.getAllContacts().map { it.toDomain() }
    }

    override fun getContactById(id: Long): Contact? {
        return contactDao.getContactById(id)?.toDomain()
    }

    override fun addContact(contact: Contact) {
        contactDao.addContact(contact.toDb())
    }

    override fun updateContact(contact: Contact) {
        contactDao.updateContact(contact.toDb())
    }

    override fun deleteContact(id: Long) {
        contactDao.deleteContact(id)
    }
}