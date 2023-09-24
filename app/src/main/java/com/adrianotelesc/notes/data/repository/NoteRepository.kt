package com.adrianotelesc.notes.data.repository

import com.adrianotelesc.notes.data.model.Note

interface NoteRepository {
    fun getNotes(): List<Note>
}
