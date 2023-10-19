package com.adrianotelesc.notes.ui.screen.noteediting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrianotelesc.notes.data.repository.NoteRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch

class NoteEditingViewModel(
    private val noteId: String?,
    private val noteRepo: NoteRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(value = NoteEditingUiState())

    val uiState: StateFlow<NoteEditingUiState> = _uiState

    private val _uiEffectChannel = Channel<NoteEditingUiEffect>()
    private val _uiEffect = _uiEffectChannel.receiveAsFlow()

    val uiEffect: Flow<NoteEditingUiEffect> = _uiEffect

    init {
        loadNoteByIdIfNotNull()
        emitFocusRequestUiEffectIfEmpty()
    }

    private fun loadNoteByIdIfNotNull() {
        if (noteId != null) loadNoteBy(id = noteId)
    }

    private fun loadNoteBy(id: String) {
        noteRepo.findBy(id = id)?.let { note ->
            _uiState.update { uiState ->
                uiState.copy(note = note)
            }
        }
    }

    private fun emitFocusRequestUiEffectIfEmpty() {
        if (_uiState.value.note.isEmpty) emitFocusRequestUiEffect()
    }

    private fun emitFocusRequestUiEffect() {
        viewModelScope.launch {
            _uiEffectChannel.send(element = NoteEditingUiEffect.RequestFocus)
        }
    }

    fun updateNote(text: String) {
        val updatedUiState = _uiState.updateAndGet { uiState ->
            uiState.copy(note = uiState.note.copy(text = text))
        }
        noteRepo.update(note = updatedUiState.note)
    }

    fun emitUpNavigationUiEffect() {
        viewModelScope.launch {
            _uiEffectChannel.send(element = NoteEditingUiEffect.NavigateUp)
        }
    }
}
