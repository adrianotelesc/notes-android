package com.adrianotelesc.notes.ui.screen.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrianotelesc.notes.data.model.Note
import com.adrianotelesc.notes.data.repository.NoteRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotesViewModel(
    private val noteRepo: NoteRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(value = NotesUiState())

    val uiState: StateFlow<NotesUiState> = _uiState

    private val _uiEffectChannel = Channel<NotesUiEffect>()
    private val _uiEffect = _uiEffectChannel.receiveAsFlow()

    val uiEffect: Flow<NotesUiEffect> = _uiEffect

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

    fun emitNoteEditingNavigationUiEffect(note: Note? = null) {
        viewModelScope.launch {
            _uiEffectChannel.send(
                element = NotesUiEffect.NavigateToNoteEditing(
                    noteId = note?.id,
                ),
            )
        }
    }
}
