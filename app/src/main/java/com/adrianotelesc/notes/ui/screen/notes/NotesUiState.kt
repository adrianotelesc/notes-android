package com.adrianotelesc.notes.ui.screen.notes

import com.adrianotelesc.notes.data.model.Note

data class NotesUiState(
    val notes: List<Note> = emptyList(),
)
