package com.example.contacts.presentation.common.model

data class EditContact(
    val id: Long? = null,
    var firstName: String = String(),
    var lastName: String = String(),
    var phone : String = String(),
    var ringtone: String = String(),
    var note: String? = null,
    var avatar: String? = null,
    val favourite: Boolean = false
)