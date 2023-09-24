package com.adrianotelesc.notes.data.repository

import com.adrianotelesc.notes.data.model.Note

class NoteRepositoryImpl : NoteRepository {
    override fun getNotes(): List<Note> =
        List(5) { index ->
            Note(text = "This is note ${index + 1}")
        }
}
