package com.example.contacts.presentation.editcontact

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.contacts.R
import com.example.contacts.presentation.common.model.ContactUi

class EditContactViewModel(private val app: Application) : AndroidViewModel(app) {
    val ringtoneList: List<String> by lazy {
        app.resources.getStringArray(R.array.array_ringtone).toList()
    }

    val contact = MutableLiveData<ContactUi>(
        ContactUi(ringtone = ringtoneList.first())
    )
}