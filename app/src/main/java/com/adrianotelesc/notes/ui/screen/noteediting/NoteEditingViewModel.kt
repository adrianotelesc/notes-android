package com.adrianotelesc.notes.ui.screen.noteediting

import androidx.lifecycle.ViewModel
import com.adrianotelesc.notes.data.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class NoteEditingViewModel(
    noteId: String?,
    private val noteRepo: NoteRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(value = NoteEditingUiState())
    val uiState: StateFlow<NoteEditingUiState> = _uiState

    init {
        noteId?.let {
            noteRepo.findBy(id = noteId)?.let { note ->
                _uiState.update { uiState ->
                    uiState.copy(note = note)
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
