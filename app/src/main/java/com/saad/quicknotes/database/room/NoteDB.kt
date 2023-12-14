package com.saad.quicknotes.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saad.quicknotes.models.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDB : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}