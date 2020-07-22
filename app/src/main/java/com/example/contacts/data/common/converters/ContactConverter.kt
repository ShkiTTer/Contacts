package com.example.contacts.data.common.converters

import com.example.contacts.data.common.entity.ContactEntity
import com.example.contacts.domain.common.model.Contact

fun ContactEntity.toDomain() = Contact(
    id = id,
    firstName = firstName,
    lastName = lastName,
    phone = phone,
    ringtone = ringtone,
    note = note,
    avatar = avatar
)

fun Contact.toDb() = ContactEntity(
    id = id,
    firstName = firstName,
    lastName = lastName,
    phone = phone,
    ringtone = ringtone,
    note = note,
    avatar = avatar
)