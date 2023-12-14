package com.saad.quicknotes.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saad.quicknotes.models.Note
import com.saad.quicknotes.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(private val repo: Repository) : ViewModel() {

    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    fun insertNotes(note: Note) {
        viewModelScope.launch {
            repo.insertNotes(note)
        }
    }
}