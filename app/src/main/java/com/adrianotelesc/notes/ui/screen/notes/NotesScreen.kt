package com.adrianotelesc.notes.ui.screen.notes

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.adrianotelesc.notes.data.model.Note
import com.adrianotelesc.notes.data.repository.NoteRepositoryImpl

@Composable
fun NotesScreen(viewModel: NotesViewModel = NotesViewModel(noteRepo = NoteRepositoryImpl())) {
    val uiState by viewModel.uiState.collectAsState()
    NotesContent(notes = uiState.notes)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesContent(notes: List<Note>) {
    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding)
        ) {
            items(notes) { note ->
                Text(text = note.text)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotesContentPreview() {
    MaterialTheme {
        NotesContent(notes = emptyList())
    }
}
