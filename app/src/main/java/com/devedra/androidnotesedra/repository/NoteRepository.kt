package com.devedra.androidnotesedra.repository

import androidx.lifecycle.LiveData
import com.devedra.androidnotesedra.database.NoteDatabaseDao
import com.devedra.androidnotesedra.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class NoteRepository(
    private val noteDatabaseDao: NoteDatabaseDao,

    ) : Repository {


    override suspend fun addNote(note: Note) {
        insert(note)
    }

    override suspend fun updateNote(note: Note) {
        update(note)
    }

    override fun getNotes(): LiveData<List<Note>> {
        return noteDatabaseDao.getAllNotes()
    }

    override fun getNote(key: Long): LiveData<Note> {
        return noteDatabaseDao.getNoteWithId(key)
    }

    override suspend fun deleteNote(note: Note) {
        delete(note)
    }

    private suspend fun insert(note: Note) {
        withContext(Dispatchers.IO) {
            noteDatabaseDao.insert(note)
        }
    }

    private suspend fun update(note: Note) {
        withContext(Dispatchers.IO) {
            noteDatabaseDao.update(note)
        }
    }

    private suspend fun delete(note: Note) {
        withContext(Dispatchers.IO) {
            noteDatabaseDao.delete(note)
        }
    }
}