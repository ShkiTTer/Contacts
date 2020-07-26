package com.example.contacts.presentation.editcontact

import android.app.Application
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.lifecycle.*
import com.example.contacts.R
import com.example.contacts.domain.contact.IContactUseCase
import com.example.contacts.presentation.common.converters.toDomain
import com.example.contacts.presentation.common.converters.toUi
import com.example.contacts.presentation.common.model.ContactUi
import com.example.contacts.presentation.common.utils.DateTimeUtils
import java.io.File

class EditContactViewModel(
    private val app: Application,
    private val contactUseCase: IContactUseCase,
    val contactId: Long?
) : AndroidViewModel(app) {

    companion object {
        private const val FILE_EXTENSION = ".jpg"
    }

    val ringtoneList: List<String> by lazy {
        app.resources.getStringArray(R.array.array_ringtone).toList()
    }

    val contact = if (contactId != null) {
        contactUseCase.getContactById(contactId).switchMap { result ->
            liveData {
                result.onSuccess { emit(it?.toUi()) }
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

    fun deleteContact(): LiveData<Boolean> {
        if (contactId == null) {
            mError.value = app.getString(R.string.error_internal)
            return MutableLiveData(false)
        }

        return contactUseCase.deleteContact(contactId).switchMap { result ->
            liveData {
                result
                    .onSuccess {
                        emit(it)
                    }
                    .onFailure {
                        mError.postValue(app.getString(R.string.error_delete))
                        emit(false)
                    }
            }
        }
    }

    fun createNewFile(): Uri {
        val timeStamp = DateTimeUtils.getTimeStamp()
        val dir = app.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(timeStamp, FILE_EXTENSION, dir)

        return FileProvider.getUriForFile(app.applicationContext, "${app.getString(R.string.app_id)}.fileprovider", file)
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