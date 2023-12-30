package com.adrianotelesc.postnote.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.adrianotelesc.postnote.data.model.Note

internal class NotesPreviewParameterProvider : PreviewParameterProvider<List<Note>> {
    override val values: Sequence<List<Note>> = sequenceOf(
        NotePreviewParameterProvider().values.toList(),
    )
}
