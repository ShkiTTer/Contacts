package com.example.contacts.domain.contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.contacts.domain.common.model.Contact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class ContactUseCaseImpl(
    private val contactDbRepositoryImpl: IContactDbRepository
) : IContactUseCase, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun getContacts(): LiveData<Result<List<Contact>>> = liveData(coroutineContext) {
        try {
            val data = contactDbRepositoryImpl.getContacts()
            emit(Result.success(data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }

    override fun getContactById(id: Long): LiveData<Result<Contact>> = liveData(coroutineContext) {
        try {
            val data = contactDbRepositoryImpl.getContactById(id)
            emit(Result.success(data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }

    override fun addContact(contact: Contact): LiveData<Result<Boolean>> =
        liveData(coroutineContext) {
            try {
                contactDbRepositoryImpl.addContact(contact)
                emit(Result.success(true))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.failure(e))
            }
        }

    override fun updateContact(contact: Contact): LiveData<Result<Boolean>> =
        liveData(coroutineContext) {
            try {
                contactDbRepositoryImpl.updateContact(contact)
                emit(Result.success(true))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.failure(e))
            }
        }

    override fun deleteContact(id: Long): LiveData<Result<Boolean>> = liveData(coroutineContext) {
        try {
            contactDbRepositoryImpl.deleteContact(id)
            emit(Result.success(true))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }

}