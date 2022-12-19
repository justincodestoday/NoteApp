package com.justin.mynotes.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.justin.mynotes.models.Note
import com.justin.mynotes.repository.NoteRepository

class EditNoteViewModel(private val repository: NoteRepository): ViewModel() {
    val note: MutableLiveData<Note> = MutableLiveData()

    fun getNoteById(id: Int) {
        val res = repository.getNoteById(id)
        res?.let {
            note.value = it
        }
    }

    fun editNote(id: Int, note: Note) {
        repository.editNote(id, note)
    }

    class Provider(private val repository: NoteRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EditNoteViewModel(repository) as T
        }
    }
}