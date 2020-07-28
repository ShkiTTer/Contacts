package com.example.contacts.domain.contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.contacts.domain.common.model.Contact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class ContactInteractorImpl(private val contactDbRepository: IContactDbRepository) :
    IContactInteractor, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun getContactById(contactId: Long): LiveData<Result<Contact?>> =
        liveData(coroutineContext) {
            try {
                val data = contactDbRepository.getContactById(contactId)
                emit(Result.success(data))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.failure(e))
            }
        }

    override fun saveContact(contact: Contact): LiveData<Result<Boolean>> =
        liveData(coroutineContext) {
            try {
                if (contact.id == null) {
                    contactDbRepository.addContact(contact)
                } else {
                    contactDbRepository.updateContact(contact)
                }

                emit(Result.success(true))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.failure(e))
            }
        }

    override fun deleteContact(contactId: Long): LiveData<Result<Boolean>> =
        liveData(coroutineContext) {
            try {
                contactDbRepository.deleteContact(contactId)
                emit(Result.success(true))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.failure(e))
            }
        }
}