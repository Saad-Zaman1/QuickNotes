package com.saad.quicknotes.di

import android.content.Context
import androidx.room.Room
import com.saad.quicknotes.database.room.NoteDB
import com.saad.quicknotes.database.room.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun providesImageDao(database: NoteDB): NoteDao {
        return database.noteDao()
    }

    @Singleton
    @Provides
    fun provideRoomDb(
        @ApplicationContext appContext: Context
    ): NoteDB {
        return Room.databaseBuilder(
            appContext,
            NoteDB::class.java,
            "QuickNotes"
        )
            .build()
    }
}