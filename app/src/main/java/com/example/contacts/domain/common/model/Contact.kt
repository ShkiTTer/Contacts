package com.example.contacts.domain.common.model

data class Contact(
    val id: Long? = null,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val ringtone: String,
    val note: String?,
    val avatar: String? = null
)