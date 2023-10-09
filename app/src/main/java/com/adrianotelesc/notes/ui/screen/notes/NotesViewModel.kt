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
        val noteCount = uiState.value.notes.size + 1
        val text = if (noteCount % 2 == 0) {
            "This is note  $noteCount.\n\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Integer congue metus accumsan aliquet vestibulum. Sed pellentesque diam tincidunt ligula sollicitudin porttitor."
        } else {
            "This is note $noteCount."
        }
        noteRepo.addNote(text = text)
    }
}
