package com.adrianotelesc.postnote.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.adrianotelesc.postnote.data.model.Note

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
