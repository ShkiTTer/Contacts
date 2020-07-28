package com.example.contacts.domain.contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.contacts.domain.common.model.Contact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class ContactListInteractorImpl(private val contactDbRepository: IContactDbRepository) :
    IContactListInteractor, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun getAllContacts(): LiveData<Result<List<Contact>>> = liveData(coroutineContext) {
        try {
            val data = contactDbRepository.getContacts()
            emit(Result.success(data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }

    override fun searchContact(query: String): LiveData<Result<List<Contact>>> =
        liveData(coroutineContext) {
            try {
                val data = contactDbRepository.searchContacts(query)
                emit(Result.success(data))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.failure(e))
            }
        }
}