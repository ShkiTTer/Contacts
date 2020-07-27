package com.example.contacts.presentation.contactslist

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.contacts.R
import com.example.contacts.domain.common.model.Contact
import com.example.contacts.presentation.contact.ContactActivity
import kotlinx.android.synthetic.main.fragment_contacts_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactsListFragment : Fragment(R.layout.fragment_contacts_list) {
    private val viewModel: ContactsListViewModel by viewModel()
    private lateinit var callback: ContactsListNavigation
    private lateinit var contactListAdapter: ContactListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as ContactsListNavigation
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    override fun onResume() {
        super.onResume()

        viewModel.contacts.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                contactListAdapter.setItems(it)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        viewModel.contacts.removeObservers(viewLifecycleOwner)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
        initSearchView(menu)
    }

    private fun initView() {
        fabNew.setOnClickListener {
            callback.onNewClick()
        }

        contactListAdapter = ContactListAdapter().apply {
            addOnItemClickListener(object : ContactListAdapter.OnItemClickListener {
                override fun onItemClick(contact: Contact) {
                    contact.id ?: return

                    ContactActivity.newIntent(requireContext(), contact.id).apply {
                        startActivity(this)
                    }
                }
            })
        }

        rvContacts.adapter = contactListAdapter
        rvContacts.addItemDecoration(ContactListItemDecoration(contactListAdapter))
    }

    private fun initSearchView(menu: Menu) {
        val searchItem = menu.findItem(R.id.action_search)

        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            val searchManager =
                requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager

            searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.searchQuery.value = newText
                    return false
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
            })
        }
    }

    interface ContactsListNavigation {
        fun onNewClick()
    }
}