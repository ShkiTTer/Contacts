package com.example.contacts.presentation.common.converters

import com.example.contacts.domain.common.model.Contact
import com.example.contacts.presentation.common.model.ContactUi

fun ContactUi.toDomain() = Contact(
    id = id,
    firstName = firstName,
    lastName = lastName,
    phone = phone,
    ringtone = ringtone,
    note = note,
    avatar = avatar
)

fun Contact.toUi() = ContactUi(
    id = id,
    firstName = firstName,
    lastName = lastName,
    phone = phone,
    ringtone = ringtone,
    note = note,
    avatar = avatar
)