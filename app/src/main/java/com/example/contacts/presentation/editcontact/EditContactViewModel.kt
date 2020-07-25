package com.example.contacts.presentation.editcontact

import android.app.Application
import androidx.lifecycle.*
import com.example.contacts.R
import com.example.contacts.domain.contact.IContactUseCase
import com.example.contacts.presentation.common.converters.toDomain
import com.example.contacts.presentation.common.converters.toUi
import com.example.contacts.presentation.common.model.ContactUi

class EditContactViewModel(
    private val app: Application,
    private val contactUseCase: IContactUseCase,
    val contactId: Long?
) : AndroidViewModel(app) {

    val ringtoneList: List<String> by lazy {
        app.resources.getStringArray(R.array.array_ringtone).toList()
    }

    val contact = if (contactId != null) {
        contactUseCase.getContactById(contactId).switchMap { result ->
            liveData {
                result.onSuccess { emit(it.toUi()) }
            }
        }
    } else MutableLiveData(ContactUi(ringtone = ringtoneList.first()))

    private val mError = MutableLiveData<String>()
    val error: LiveData<String> = mError

    fun saveContact(): LiveData<Boolean> {
        return if (contactId == null)
            addContact()
        else updateContact()
    }

    private fun addContact(): LiveData<Boolean> {
        val contactValue = contact.value

        if (contactValue == null) {
            mError.value = app.getString(R.string.error_internal)
            return MutableLiveData(false)
        }

        if (!validateContact(contactValue)) {
            mError.value = app.getString(R.string.error_empty_fields)
            return MutableLiveData(false)
        }

        return contactUseCase.addContact(contactValue.toDomain()).switchMap { result ->
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

    private fun updateContact(): LiveData<Boolean> {
        val contactValue = contact.value

        if (contactValue == null) {
            mError.value = app.getString(R.string.error_internal)
            return MutableLiveData(false)
        }

        if (!validateContact(contactValue)) {
            mError.value = app.getString(R.string.error_empty_fields)
            return MutableLiveData(false)
        }

        return contactUseCase.updateContact(contactValue.toDomain()).switchMap { result ->
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

    private fun validateContact(contactValue: ContactUi): Boolean =
        contactValue.firstName.isNotEmpty() && contactValue.lastName.isNotEmpty()
                && contactValue.phone.isNotEmpty()
}