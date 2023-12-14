package com.saad.quicknotes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.saad.quicknotes.models.Note
import com.saad.quicknotes.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: Repository) : ViewModel() {
    val notes: LiveData<List<Note>> = repo.getNotes()
}