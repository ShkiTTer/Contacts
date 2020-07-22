package com.example.contacts.data.common.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val ringtone: String,
    val note: String?,
    val avatar: String?
)