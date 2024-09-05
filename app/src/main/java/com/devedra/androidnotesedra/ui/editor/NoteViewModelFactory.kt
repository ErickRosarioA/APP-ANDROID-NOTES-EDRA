package com.devedra.androidnotesedra.ui.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devedra.androidnotesedra.repository.NoteRepository


@Suppress("UNCHECKED_CAST")
class NoteViewModelFactory (
    private val repository: NoteRepository,
    private val noteKey: Long
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteEditorViewModel::class.java)) {
            return NoteEditorViewModel(repository, noteKey) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}