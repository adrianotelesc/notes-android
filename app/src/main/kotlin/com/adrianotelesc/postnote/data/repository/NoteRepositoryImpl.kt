package com.adrianotelesc.postnote.data.repository

import com.adrianotelesc.postnote.data.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class NoteRepositoryImpl : NoteRepository {
    private val _notes = MutableStateFlow<List<Note>>(value = emptyList())

    override val notes: Flow<List<Note>> = _notes

    override fun findBy(id: String): Note? = _notes.value.find { it.id == id }

    override fun update(note: Note) {
        findBy(id = note.id)?.let { existingNote ->
            if (existingNote.isNotEmpty && note.isEmpty) {
                remove(note = existingNote)
            } else if (existingNote != note) {
                replace(oldNote = existingNote, newNote = note)
            }
        } ?: add(note = note)
    }

    override fun add(note: Note) {
        if (note.isEmpty) return
        updateNotes { add(index = 0, element = note) }
    }

    private fun updateNotes(block: MutableList<Note>.() -> Unit) {
        _notes.update { notes -> notes.toMutableList().apply(block = block) }
    }

    override fun remove(note: Note) {
        updateNotes { remove(element = note) }
    }

    override fun replace(oldNote: Note, newNote: Note) {
        updateNotes {
            val index = indexOf(element = oldNote)
            removeAt(index = index)
            add(index = index, element = newNote)
        }
    }
}
