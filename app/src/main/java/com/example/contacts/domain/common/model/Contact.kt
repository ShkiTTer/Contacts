package com.example.contacts.domain.common.model

data class Contact(
    val id: Long? = null,
    var firstName: String,
    var lastName: String,
    var phone: String,
    var ringtone: String,
    var note: String? = null,
    var avatar: String? = null
)