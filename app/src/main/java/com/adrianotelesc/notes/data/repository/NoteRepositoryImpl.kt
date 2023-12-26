package com.adrianotelesc.notes.data.repository

import com.adrianotelesc.notes.data.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class NoteRepositoryImpl : NoteRepository {
    private val _notes = MutableStateFlow<List<Note>>(value = emptyList())
    override val notes: Flow<List<Note>> = _notes

    override fun update(note: Note) {
        findBy(id = note.id)?.let { existingNote ->
            if (existingNote.isNotEmpty && note.isEmpty) {
                delete(note = existingNote)
            } else if (existingNote != note) {
                replace(oldNote = existingNote, newNote = note)
            }
        } ?: add(note = note)
    }

    override fun findBy(id: String): Note? = _notes.value.find { it.id == id }

    override fun add(note: Note) {
        if (note.isEmpty) return
        updateNotesStateFlow { add(index = 0, element = note) }
    }

    private fun updateNotesStateFlow(block: MutableList<Note>.() -> Unit) {
        _notes.update { notes ->
            notes.toMutableList().apply(block = block)
        }
    }

    override fun delete(note: Note) {
        updateNotesStateFlow { remove(element = note) }
    }

    override fun replace(oldNote: Note, newNote: Note) {
        updateNotesStateFlow {
            val index = indexOf(element = oldNote)
            removeAt(index = index)
            add(index = index, element = newNote)
        }
    }
}
