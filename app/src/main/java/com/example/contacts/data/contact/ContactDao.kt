package com.example.contacts.data.contact

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.contacts.data.common.entity.ContactEntity

@Dao
interface ContactDao {
    @Query("Select * From contact Order by firstName")
    fun getAllContacts(): List<ContactEntity>

    @Query("Select * From contact Where id = :contactId")
    fun getContactById(contactId: Long): ContactEntity?

    @Insert
    fun addContact(contact: ContactEntity)

    @Update
    fun updateContact(contact: ContactEntity)

    @Query("DELETE FROM contact WHERE id = :contactId")
    fun deleteContact(contactId: Long)
}