package com.example.contacts.presentation.editcontact

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contacts.presentation.common.model.ContactUi

class EditContactViewModel : ViewModel() {
    val contact = MutableLiveData<ContactUi>(
        ContactUi()
    )
}