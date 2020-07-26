package com.example.contacts.domain.common.model

data class Contact(
    val id: Long? = null,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val ringtone: String,
    var favourite: Boolean = false,
    val note: String? = null,
    val avatar: String? = null,
    val fullName: String = "$firstName $lastName"
)