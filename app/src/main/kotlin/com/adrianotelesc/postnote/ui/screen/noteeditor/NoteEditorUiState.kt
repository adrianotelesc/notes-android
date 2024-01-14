package com.adrianotelesc.postnote.ui.screen.noteeditor

import com.adrianotelesc.postnote.data.model.Note

data class NoteEditorUiState(
    val isActive: Boolean = false,
    val note: Note = Note(),
)
