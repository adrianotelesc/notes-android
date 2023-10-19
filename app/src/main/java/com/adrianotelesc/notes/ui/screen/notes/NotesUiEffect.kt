package com.adrianotelesc.notes.ui.screen.notes

sealed class NotesUiEffect {
    class NavigateToNoteEditing(
        val noteId: String?,
    ) : NotesUiEffect()
}
