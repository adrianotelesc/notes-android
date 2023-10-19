package com.adrianotelesc.notes.ui.screen.noteediting

sealed class NoteEditingUiEffect {
    object NavigateUp: NoteEditingUiEffect()
    object RequestFocus: NoteEditingUiEffect()
}
