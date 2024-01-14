package com.adrianotelesc.postnote.ui.screen.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrianotelesc.postnote.data.model.Note
import com.adrianotelesc.postnote.data.repository.NoteRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotesViewModel(
    private val noteRepo: NoteRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(value = NotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState

    private val _uiEffect = MutableSharedFlow<NotesUiEffect>()
    val uiEffect: SharedFlow<NotesUiEffect> = _uiEffect

    fun loadNotes() {
        viewModelScope.launch {
            noteRepo.notes.collect { notes ->
                _uiState.update { uiState ->
                    uiState.copy(notes = notes)
                }
            }
        }
    }

    fun createOrOpenNote(note: Note? = null) {
        viewModelScope.launch {
            _uiEffect.emit(value = NotesUiEffect.NavigateToNoteEditor(noteId = note?.id))
        }
    }
}
