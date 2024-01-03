package com.adrianotelesc.postnote.ui.screen.noteeditor

import androidx.lifecycle.ViewModel
import com.adrianotelesc.postnote.data.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class NoteEditorViewModel(
    noteId: String?,
    private val noteRepo: NoteRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(value = NoteEditorUiState())
    val uiState: StateFlow<NoteEditorUiState> = _uiState

    init {
        noteId?.let {
            noteRepo.findBy(id = noteId)?.let { existingNote ->
                _uiState.update { uiState ->
                    uiState.copy(note = existingNote)
                }
            }
        }
    }

    fun updateNote(text: String) {
        val changedNote = _uiState.value.note.copy(text = text)
        _uiState.update { uiState ->
            uiState.copy(note = changedNote)
        }
        noteRepo.update(note = changedNote)
    }
}
