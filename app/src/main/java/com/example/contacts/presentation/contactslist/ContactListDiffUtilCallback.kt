package com.example.contacts.presentation.contactslist

import androidx.recyclerview.widget.DiffUtil
import com.example.contacts.domain.common.model.Contact

class ContactListDiffUtilCallback : DiffUtil.Callback() {
    private val oldItems = mutableListOf<Contact>()
    private val newItems = mutableListOf<Contact>()

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldItems[oldItemPosition]
        val new = newItems[newItemPosition]

        return old.id == new.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldItems[oldItemPosition]
        val new = newItems[newItemPosition]

        return old == new
    }

    fun setItems(oldItems: List<Contact>, newItems: List<Contact>) {
        this.oldItems.apply {
            clear()
            addAll(oldItems)
        }

        this.newItems.apply {
            clear()
            addAll(newItems)
        }
    }
}