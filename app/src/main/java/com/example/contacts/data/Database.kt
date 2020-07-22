package com.example.contacts.data

import android.content.Context
import androidx.room.Room

object Database {
    private const val DB_NAME = "contact_db"

    fun build(context: Context): ContactsDatabase = Room.databaseBuilder(
        context,
        ContactsDatabase::class.java,
        DB_NAME
    ).build()
}