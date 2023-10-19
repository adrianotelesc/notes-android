package com.adrianotelesc.notes.data.repository

import com.adrianotelesc.notes.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    val notes: Flow<List<Note>>

    fun add(note: Note): Boolean

    fun delete(note: Note): Boolean

    fun update(note: Note): Boolean

    fun replace(oldNote: Note, newNote: Note): Boolean

    fun findBy(id: String): Note?
}
