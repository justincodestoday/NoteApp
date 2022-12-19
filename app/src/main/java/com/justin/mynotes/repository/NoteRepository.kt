package com.justin.mynotes.repository

import com.justin.mynotes.models.Note

class NoteRepository {
    private var counter = -1
    private val noteMap: MutableMap<Int, Note> = mutableMapOf()
    fun getNotes(): List<Note> {
        return noteMap.values.toList()
    }

    fun getNoteById(id: Int): Note? {
        return noteMap[id]
    }

    fun addNote(note: Note): Note? {
        noteMap[++counter] = note.copy(id = counter)
        return noteMap[counter]
    }

    fun editNote(id: Int, note: Note): Note? {
        noteMap[id] = note
        return noteMap[id]
    }

    fun deleteNote(id: Int) {
        noteMap.remove(id)
    }

    companion object {
        private var noteRepository: NoteRepository? = null
        fun getInstance(): NoteRepository {
            if (noteRepository == null) {
                noteRepository = NoteRepository()
            }

            return noteRepository!!
        }
    }
}