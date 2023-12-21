package com.adrianotelesc.notes.data.repository

import com.adrianotelesc.notes.data.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class NoteRepositoryImpl : NoteRepository {
    private val _notes = MutableStateFlow<List<Note>>(value = emptyList())
    override val notes: Flow<List<Note>> = _notes

    override fun update(note: Note) {
        val oldNote = findBy(id = note.id)
        when {
            oldNote == null && note.isNotEmpty -> add(note = note)
            oldNote != null && oldNote.isNotEmpty && note.isEmpty -> delete(note = oldNote)
            oldNote != null && oldNote != note -> replace(oldNote = oldNote, newNote = note)
        }
    }

    override fun findBy(id: String): Note? = _notes.value.find { it.id == id }

    override fun add(note: Note) {
        updateNotesState { add(index = 0, element = note) }
    }

    private fun updateNotesState(block: MutableList<Note>.() -> Unit) {
        _notes.update { notes ->
            notes.toMutableList().apply(block = block)
        }
    }

    override fun delete(note: Note) {
        updateNotesState { remove(element = note) }
    }

    override fun replace(oldNote: Note, newNote: Note) {
        updateNotesState {
            val index = indexOf(element = oldNote)
            remove(element = oldNote)
            add(index = index, element = newNote)
        }
    }
}
