package com.example.contacts.presentation.common.model

data class EditContact(
    val id: Long? = null,
    var firstName: String = "",
    var lastName: String = "",
    var phone : String = "",
    var ringtone: String = "",
    var note: String? = null,
    var avatar: String? = null,
    val favourite: Boolean = false
)