package com.example.contacts.presentation.contactslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.R
import com.example.contacts.databinding.ItemContactBinding
import com.example.contacts.domain.common.model.Contact

class ContactListAdapter : RecyclerView.Adapter<ContactListAdapter.ViewHolder>(),
    ContactListItemDecoration.HeaderCallback {

    private val items = mutableListOf<Contact>()
    private var onItemClickListener: OnItemClickListener? = null

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_contact, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun isHeader(pos: Int): Boolean {
        return pos == 0 || !getHeaderName(pos).equals(getHeaderName(pos - 1), true)
    }

    override fun getHeaderName(pos: Int): String {
        return items[pos].fullName.first().toString()
    }

    fun setItems(items: List<Contact>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    fun addOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    inner class ViewHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClickListener?.onItemClick(items[adapterPosition])
            }
        }

        fun bind(contact: Contact) {
            binding.contact = contact
        }
    }

    interface OnItemClickListener {
        fun onItemClick(contact: Contact)
    }
}