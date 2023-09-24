package com.adrianotelesc.notes.ui.screen.notes

import com.adrianotelesc.notes.data.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class NotesViewModel(
    private val noteRepo: NoteRepository,
) {
    private val _uiState = MutableStateFlow(value = NotesUiState())
    val uiState: StateFlow<NotesUiState> get() = _uiState

    init {
        loadNotes()
    }

    private fun loadNotes() {
        _uiState.update { uiState ->
            uiState.copy(
                notes = noteRepo.getNotes(),
            )
        }
    }
}
