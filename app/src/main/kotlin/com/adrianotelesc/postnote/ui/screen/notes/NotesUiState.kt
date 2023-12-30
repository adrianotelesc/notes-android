package com.adrianotelesc.postnote.ui.screen.notes

import com.adrianotelesc.postnote.data.model.Note

data class NotesUiState(
    val notes: List<Note> = emptyList(),
)
