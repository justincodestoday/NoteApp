package com.justin.mynotes.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.justin.mynotes.models.Note
import com.justin.mynotes.repository.NoteRepository

class DetailsViewModel(private val repository: NoteRepository) : ViewModel() {
    val note: MutableLiveData<Note> = MutableLiveData()

    fun getNoteById(id: Int) {
        val res = repository.getNoteById(id)
        res?.let {
            note.value = it
        }
    }

    fun deleteNote(id: Int) {
        repository.deleteNote(id)
    }

    class Provider(private val repository: NoteRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailsViewModel(repository) as T
        }
    }
}