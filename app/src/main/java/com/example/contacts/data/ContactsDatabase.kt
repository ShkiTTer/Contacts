package com.example.contacts.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contacts.data.common.entity.ContactEntity
import com.example.contacts.data.contact.ContactDao

@Database(entities = [ContactEntity::class], version = 1)
abstract class ContactsDatabase: RoomDatabase() {
    abstract fun contactDao(): ContactDao
}