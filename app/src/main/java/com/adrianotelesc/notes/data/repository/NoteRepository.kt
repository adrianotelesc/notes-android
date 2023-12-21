package com.adrianotelesc.notes.data.repository

import com.adrianotelesc.notes.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    val notes: Flow<List<Note>>

    fun add(note: Note)

    fun delete(note: Note)

    fun update(note: Note)

    fun replace(oldNote: Note, newNote: Note)

    fun findBy(id: String): Note?
}
