package com.justin.mynotes.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.justin.mynotes.models.Note
import com.justin.mynotes.repository.NoteRepository

class AddNoteViewModel(val repository: NoteRepository) : ViewModel() {
    fun addNote(note: Note) {
        repository.addNote(note)
    }

    class Provider(private val repository: NoteRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddNoteViewModel(repository) as T
        }
    }
}