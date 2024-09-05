package com.devedra.androidnotesedra.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.devedra.androidnotesedra.model.Note
import com.devedra.androidnotesedra.repository.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job


class NoteListViewModel (private val noteRepository: NoteRepository) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _notesList = noteRepository.getNotes()

    val properties: LiveData<List<Note>>
        get() = _notesList

    private val _navigateToEditor = MutableLiveData<Boolean>()
    val navigateToEditor: LiveData<Boolean>
        get() = _navigateToEditor

    private val _navigateToNoteDetail = MutableLiveData<Long?>()
    val navigateToNoteDetail
        get() = _navigateToNoteDetail


    val isEmpty: LiveData<Boolean> = properties.map {
        it.isEmpty()
    }

    fun navigateToEditor() {
        _navigateToEditor.value = true
    }

    fun navigateToEditorDone() {
        _navigateToEditor.value = false
    }

    fun onNoteClicked(id: Long) {
        _navigateToNoteDetail.value = id
    }

    fun onNoteNavigated() {
        _navigateToNoteDetail.value = null
    }


}