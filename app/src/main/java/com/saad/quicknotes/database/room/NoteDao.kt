package com.saad.quicknotes.database.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.saad.quicknotes.models.Note

@Dao
interface NoteDao {
    @Insert
    suspend fun insertNote(data: Note)

    @Query("Select * from notes")
    fun getData(): LiveData<List<Note>>
}