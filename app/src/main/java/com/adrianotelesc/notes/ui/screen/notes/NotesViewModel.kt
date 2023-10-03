package com.adrianotelesc.notes.ui.screen.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrianotelesc.notes.data.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotesViewModel(
    private val noteRepo: NoteRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(value = NotesUiState())
    val uiState: StateFlow<NotesUiState> get() = _uiState

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            noteRepo.notes.collect { notes ->
                _uiState.update { uiState ->
                    uiState.copy(notes = notes)
                }
            }
        }
    }

    fun addNote() {
        noteRepo.addNote(text = "This is note ${uiState.value.notes.size + 1}")
    }
}
