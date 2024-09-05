package com.devedra.androidnotesedra.ui.list


import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.devedra.androidnotesedra.R
import com.devedra.androidnotesedra.adapter.NoteAdapter
import com.devedra.androidnotesedra.database.NoteDatabase
import com.devedra.androidnotesedra.database.NoteDatabaseDao
import com.devedra.androidnotesedra.databinding.FragmentListNoteBinding
import com.devedra.androidnotesedra.repository.NoteRepository
import com.devedra.androidnotesedra.ui.dialog.DialogPolicyPrivacyWebView
import com.devedra.androidnotesedra.util.hideKeyboard



class NoteListFragment : Fragment() {

    private lateinit var binding: FragmentListNoteBinding
    private lateinit var repository: NoteRepository
    private lateinit var noteDatabaseDao: NoteDatabaseDao
    private lateinit var application: Application
    private lateinit var viewModel: NoteListViewModel
    private lateinit var adapter: NoteAdapter
    private lateinit var searchView: SearchView
    private lateinit var menuItem: MenuItem

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListNoteBinding.inflate(inflater)
        appUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topAppBar.setNavigationOnClickListener {
            viewModel.navigateToEditor()
        }
    }

    private fun appUI() {
        application = requireNotNull(activity).application

        noteDatabaseDao = NoteDatabase.getInstance(application).noteDatabaseDao
        repository = NoteRepository(noteDatabaseDao)
        val viewModelFactory = NoteListViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[(NoteListViewModel::class.java)]

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        initObservers()

        adapter = NoteAdapter(NoteAdapter.OnClickListener {
            viewModel.onNoteClicked(it.noteId)
        })

        binding.itemGrid.adapter = adapter

        menuItem = binding.topAppBar.menu.findItem(R.id.search)
        searchView = menuItem.actionView as SearchView
        searchView.queryHint = getString(R.string.escribir_busqueda)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }

        })

        binding.topAppBar.setOnMenuItemClickListener {menuItem->
            when(menuItem.itemId){
                R.id.politic_privacy ->{
                    fullDialogPoliticPrivacy()
                    true
                }
                else->false
            }
        }

    }

    private fun initObservers() {
        viewModel.navigateToEditor.observe(viewLifecycleOwner) {
            if (it) {
                this.findNavController().navigate(
                    NoteListFragmentDirections
                        .actionNoteListFragmentToNoteEditorFragment(0L)
                )
                viewModel.navigateToEditorDone()
                hideKeyboard()
            }
        }

        viewModel.navigateToNoteDetail.observe(viewLifecycleOwner) {
            if (it != null) {
                this.findNavController().navigate(
                    NoteListFragmentDirections
                        .actionNoteListFragmentToNoteEditorFragment(it)
                )
                viewModel.onNoteNavigated()
                hideKeyboard()
            }
        }
    }

    private fun fullDialogPoliticPrivacy(){
        val dialog = DialogPolicyPrivacyWebView()
        requireActivity().supportFragmentManager.let { dialog.show(it,"DialogPolicyPrivacyWebView") }

    }

}