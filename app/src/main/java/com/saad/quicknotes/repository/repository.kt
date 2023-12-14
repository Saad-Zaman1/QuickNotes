package com.saad.quicknotes.repository

import androidx.lifecycle.LiveData
import com.saad.quicknotes.database.room.NoteDao
import com.saad.quicknotes.models.Note
import javax.inject.Inject

class Repository @Inject constructor(
    private val noteDao: NoteDao
) {
    suspend fun insertNotes(note: Note) {
        noteDao.insertNote(note)
    }

    fun getNotes(): LiveData<List<Note>> {
        return noteDao.getData()
    }
}