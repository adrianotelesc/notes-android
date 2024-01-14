package com.adrianotelesc.postnote.ui.screen.noteeditor

sealed class NoteEditorUiEffect {
    data object NavigateUp: NoteEditorUiEffect()
}
