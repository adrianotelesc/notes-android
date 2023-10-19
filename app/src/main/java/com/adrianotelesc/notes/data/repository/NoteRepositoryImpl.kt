package com.adrianotelesc.notes.data.repository

import com.adrianotelesc.notes.data.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class NoteRepositoryImpl : NoteRepository {
    private val _notes = MutableStateFlow<List<Note>>(value = emptyList())

    override val notes: Flow<List<Note>> = _notes

    override fun update(note: Note): Boolean {
        val oldNote = findBy(id = note.id)
        val isSuccess = when {
            oldNote == null && note.isNotEmpty -> add(note = note)
            oldNote != null && oldNote.isNotEmpty && note.isEmpty -> delete(note = oldNote)
            oldNote != null && oldNote != note -> replace(oldNote = oldNote, newNote = note)
            else -> false
        }
        return isSuccess
    }

    override fun findBy(id: String): Note? = _notes.value.find { it.id == id }

    override fun add(note: Note): Boolean =
        updateNotesState { add(index = 0, element = note) }

    private fun updateNotesState(block: MutableList<Note>.() -> Unit): Boolean {
        val result = runCatching {
            _notes.update { notes ->
                notes.toMutableList().apply(block = block)
            }
        }
        return result.isSuccess
    }

    override fun delete(note: Note): Boolean =
        updateNotesState { remove(element = note) }

    override fun replace(oldNote: Note, newNote: Note): Boolean {
        val isSuccess = updateNotesState {
            val index = indexOf(element = oldNote)
            remove(element = oldNote)
            add(index = index, element = newNote)
        }
        return isSuccess
    }
}
