package com.adrianotelesc.postnote.ui.screen.notes

sealed class NotesUiEffect {
    data class NavigateToNoteEditor(val noteId: String? = null): NotesUiEffect()
}
