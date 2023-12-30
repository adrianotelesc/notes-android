package com.adrianotelesc.postnote.data.model

import java.util.UUID

data class Note(
    val id: String = UUID.randomUUID().toString(),
    val text: String = "",
) {
    val isEmpty: Boolean get() = text.isEmpty() || text.isBlank()

    val isNotEmpty: Boolean get() = !isEmpty
}
