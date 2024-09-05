package com.devedra.androidnotesedra.ui.editor

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.devedra.androidnotesedra.R
import com.devedra.androidnotesedra.database.NoteDatabase
import com.devedra.androidnotesedra.database.NoteDatabaseDao
import com.devedra.androidnotesedra.databinding.FragmentNoteEditorBinding
import com.devedra.androidnotesedra.model.Note
import com.devedra.androidnotesedra.repository.NoteRepository
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar


class NoteEditorFragment : Fragment() {
    private lateinit var binding: FragmentNoteEditorBinding
    private lateinit var repository: NoteRepository
    private lateinit var noteDatabaseDao: NoteDatabaseDao
    private lateinit var application: Application
    private lateinit var viewModel: NoteEditorViewModel
    private lateinit var arguments: NoteEditorFragmentArgs
    private var note: Note = Note()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteEditorBinding.inflate(inflater)
        initUI()
        return binding.root
    }

    private fun initUI() {
        application = requireNotNull(this.activity).application
        arguments = NoteEditorFragmentArgs.fromBundle(requireArguments())

        noteDatabaseDao = NoteDatabase.getInstance(application).noteDatabaseDao
        repository = NoteRepository(noteDatabaseDao)
        val viewModelFactory = NoteViewModelFactory(repository, arguments.noteKey)
        viewModel = ViewModelProvider(this, viewModelFactory).get((NoteEditorViewModel::class.java))

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        initObservers()

        binding.topAppBar.setNavigationOnClickListener {
            this.findNavController().navigateUp()
        }



        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.saveNoteBtn -> {
                    note.updatedAt = System.currentTimeMillis()
                    binding.root.hideKeyboard()
                    if (binding.titleEditText.editText?.text?.trim()
                            ?.isEmpty()!! || binding.titleEditText.editText!!.text?.trim()
                            ?.isEmpty()!!
                    ) {
                        Snackbar.make(
                            binding.noteEditorLayout,
                            getString(R.string.data_required),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    } else {
                        note.title = binding.titleEditText.editText!!.text.toString()
                        note.content = binding.contentEditText.editText!!.text.toString()
                        if (note.noteId == 0L) {
                            viewModel.saveNote(note)
                        } else {
                            viewModel.updateNote(note)
                        }
                    }
                    true
                }
                R.id.deleteNoteButton -> {
                    showMessageConfirmDelete()
                    true
                }

                else -> false
            }
        }
    }

    private fun initObservers() {
        viewModel.navigateToList.observe(viewLifecycleOwner) {
            if (it) {
                this.findNavController().navigateUp()
                viewModel.navigateToListDone()
            }
        }
        viewModel.getNote().observe(viewLifecycleOwner) {
            if (it != null) {
                note = it
            }
        }
        viewModel.isEdit.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.topAppBar.menu.findItem(R.id.deleteNoteButton).isVisible = it
            }
        }
    }

    private fun showMessageConfirmDelete() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.title_delete_note_message))
            .setMessage(resources.getString(R.string.content_delete_note_message))
            .setNeutralButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, _ ->
                viewModel.deleteNote(note)
                dialog.dismiss()
            }
            .show()
    }

    private fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }


}