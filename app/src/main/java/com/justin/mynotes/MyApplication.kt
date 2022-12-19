package com.justin.mynotes

import android.app.Application
import com.justin.mynotes.repository.NoteRepository

class MyApplication : Application() {
    val noteRepository = NoteRepository.getInstance()
}