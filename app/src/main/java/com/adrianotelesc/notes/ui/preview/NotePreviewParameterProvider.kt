package com.adrianotelesc.notes.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.adrianotelesc.notes.data.model.Note

internal class NotePreviewParameterProvider : PreviewParameterProvider<Note> {
    override val values: Sequence<Note> = sequenceOf(
        Note(
            text = """
                    My shopping list
                    - Eggs 🥚
                    - Rice 🍚
                    - Beans 🫘
                """.trimIndent()
        ),
        Note(
            text = """
                    My watchlist
                    - Konosuba 😳
                    - Jujutsu 👻
                    - One Piece 🏴‍☠️
                """.trimIndent()
        ),
    )
}
