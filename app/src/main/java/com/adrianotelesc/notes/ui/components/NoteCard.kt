package com.adrianotelesc.notes.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NoteCard(
    id: String,
    text: String,
    onClick: (id: String?) -> Unit,
) {
    Card(onClick = { onClick(id) }) {
        Text(
            modifier = Modifier.padding(all = 16.dp),
            text = text,
            maxLines = 10,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
