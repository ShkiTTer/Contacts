package com.example.contacts.presentation.contacts

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.contacts.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactsFragment : Fragment() {

    private val contactsViewModel: ContactsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
        initSearchView(menu)
    }

    private fun initSearchView(menu: Menu) {
        val searchItem = menu.findItem(R.id.action_search)

        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            val searchManager =
                requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager

            searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))

            searchView.setOnCloseListener {
                // TODO: Update list
                true
            }

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    // TODO: Filter list

                    return false
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
            })
        }
    }
}