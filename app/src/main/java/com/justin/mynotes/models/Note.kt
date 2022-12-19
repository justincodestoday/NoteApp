package com.justin.mynotes.models

data class Note(
    val id: Int? = null,
    val title: String,
    val details: String,
    val color: String
)