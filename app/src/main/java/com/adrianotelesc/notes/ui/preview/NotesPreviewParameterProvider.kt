package com.adrianotelesc.notes.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.adrianotelesc.notes.data.model.Note

internal class NotesPreviewParameterProvider : PreviewParameterProvider<List<Note>> {
    override val values: Sequence<List<Note>> = sequenceOf(
        NotePreviewParameterProvider().values.toList(),
    )
}
