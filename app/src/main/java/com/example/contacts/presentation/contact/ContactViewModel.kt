package com.example.contacts.presentation.contact

import android.app.Application
import androidx.lifecycle.*
import com.example.contacts.R
import com.example.contacts.domain.common.model.Contact
import com.example.contacts.domain.contact.IContactInteractor

class ContactViewModel(
    private val app: Application,
    private val contactInteractor: IContactInteractor,
    val contactId: Long?
) : AndroidViewModel(app) {

    private lateinit var contactValue: Contact

    private val mError = MutableLiveData<String>()
    val error: LiveData<String> = mError

    val contact
        get() = if (contactId != null) {
            contactInteractor.getContactById(contactId).switchMap { result ->
                liveData {
                    result
                        .onSuccess {
                            if (it != null) contactValue = it
                            emit(it)
                        }
                        .onFailure {
                            mError.postValue(app.getString(R.string.error_internal))
                        }
                }
            }
        } else {
            mError.value = app.getString(R.string.error_internal)
            MutableLiveData()
        }

    fun changeFavourite(): LiveData<Boolean> {
        contactValue.favourite = !contactValue.favourite

        return contactInteractor.saveContact(contactValue).switchMap { result ->
            liveData {
                result
                    .onSuccess {
                        emit(it)
                    }
                    .onFailure {
                        mError.postValue(app.getString(R.string.error_save))
                        emit(false)
                    }
            }
        }
    }

    fun isFavourite(): Boolean = contactValue.favourite
}