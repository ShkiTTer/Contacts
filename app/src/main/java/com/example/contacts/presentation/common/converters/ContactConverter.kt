package com.example.contacts.presentation.common.converters

import com.example.contacts.domain.common.model.Contact
import com.example.contacts.presentation.common.model.EditContact

fun EditContact.toDomain() = Contact(
    id = id,
    firstName = firstName,
    lastName = lastName,
    phone = phone,
    ringtone = ringtone,
    note = note,
    avatar = avatar,
    favourite = favourite
)

fun Contact.toUi() = EditContact(
    id = id,
    firstName = firstName,
    lastName = lastName,
    phone = phone,
    ringtone = ringtone,
    note = note,
    avatar = avatar,
    favourite = favourite
)