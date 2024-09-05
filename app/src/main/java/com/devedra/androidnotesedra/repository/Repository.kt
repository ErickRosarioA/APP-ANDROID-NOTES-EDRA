package com.devedra.androidnotesedra.repository

import androidx.lifecycle.LiveData
import com.devedra.androidnotesedra.model.Note

interface Repository {
    suspend fun addNote(note: Note)

    suspend fun updateNote(note: Note)

    fun getNotes(): LiveData<List<Note>>

    fun getNote(key: Long): LiveData<Note>

    suspend fun deleteNote(note: Note)
}