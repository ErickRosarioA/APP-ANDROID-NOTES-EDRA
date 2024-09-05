package com.devedra.androidnotesedra.ui.editor

import androidx.lifecycle.*
import com.devedra.androidnotesedra.model.Note
import com.devedra.androidnotesedra.repository.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class NoteEditorViewModel (private val notesRepository: NoteRepository,
                           noteKey: Long,) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val note = MediatorLiveData<Note>()

    fun getNote() = note

    private val _navigateToList = MutableLiveData<Boolean>()
    val navigateToList: LiveData<Boolean>
        get() = _navigateToList

    var isEdit: LiveData<Boolean> = Transformations.map(note) {
            null != it
    }

    init {
        println(noteKey)
        note.addSource(notesRepository.getNote(noteKey), note::setValue)
    }

    fun saveNote(note: Note) {
        uiScope.launch {
            notesRepository.addNote(note)
            navigateToList()
        }
    }

    fun deleteNote(note: Note) {
        uiScope.launch {
            notesRepository.deleteNote(note)
            navigateToList()
        }
    }

    fun updateNote(note: Note) {
        uiScope.launch {
            notesRepository.updateNote(note)
            navigateToList()
        }
    }

    private fun navigateToList() {
        _navigateToList.value = true
    }

    fun navigateToListDone() {
        _navigateToList.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}