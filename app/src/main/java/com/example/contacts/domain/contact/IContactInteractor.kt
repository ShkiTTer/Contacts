package com.example.contacts.domain.contact

import androidx.lifecycle.LiveData
import com.example.contacts.domain.common.model.Contact

interface IContactInteractor {
    fun getContactById(contactId: Long): LiveData<Result<Contact?>>
    fun saveContact(contact: Contact): LiveData<Result<Boolean>>
    fun deleteContact(contactId: Long): LiveData<Result<Boolean>>
}