package com.justin.mynotes.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.justin.mynotes.models.Note
import com.justin.mynotes.repository.NoteRepository

class HomeViewModel(private val repository: NoteRepository) : ViewModel() {
    val notes: MutableLiveData<List<Note>> = MutableLiveData()

    init {
        getNotes()
    }

    fun getNotes() {
        val res = repository.getNotes()
        notes.value = res
    }

    fun deleteNote(id: Int) {
        repository.deleteNote(id)
    }

    class Provider(private val repository: NoteRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(repository) as T
        }
    }
}