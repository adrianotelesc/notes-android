package com.adrianotelesc.notes.data.repository

import com.adrianotelesc.notes.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    val notes: Flow<List<Note>>
    fun addNote(text: String)
}
