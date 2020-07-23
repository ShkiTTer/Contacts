package com.example.contacts.presentation.common.model

data class ContactUi(
    val id: Long? = null,
    var firstName: String = String(),
    var lastName: String = String(),
    var phone : String = String(),
    var ringtone: String = String(),
    var note: String? = null,
    var avatar: String? = null
)