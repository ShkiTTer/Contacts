package com.example.contacts.presentation.contact

import android.app.Application
import androidx.lifecycle.*
import com.example.contacts.R
import com.example.contacts.domain.common.model.Contact
import com.example.contacts.domain.contact.IContactUseCase

class ContactViewModel(
    private val app: Application,
    private val contactUseCase: IContactUseCase,
    val contactId: Long
) : AndroidViewModel(app) {

    val contact
        get() = readContact()

    private val mError = MutableLiveData<String>()
    val error: LiveData<String> = mError

    private fun readContact(): LiveData<Contact?> {
        return contactUseCase.getContactById(contactId).switchMap { result ->
            liveData {
                result
                    .onSuccess {
                        emit(it)
                    }
                    .onFailure {
                        mError.postValue(app.getString(R.string.error_internal))
                    }
            }
        }
    }
}