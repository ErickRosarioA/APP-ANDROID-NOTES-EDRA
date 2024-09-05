package com.devedra.androidnotesedra.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devedra.androidnotesedra.repository.NoteRepository

@Suppress("UNCHECKED_CAST")
class NoteListViewModelFactory (
    private val repository: NoteRepository
) : ViewModelProvider.Factory {
     override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteListViewModel::class.java)) {
            return NoteListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}