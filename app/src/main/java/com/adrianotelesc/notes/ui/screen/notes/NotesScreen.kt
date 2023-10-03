package com.adrianotelesc.notes.ui.screen.notes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.adrianotelesc.notes.R
import com.adrianotelesc.notes.data.model.Note
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotesScreen(viewModel: NotesViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    NotesContent(
        notes = uiState.notes,
        onAddClick = viewModel::addNote,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesContent(
    notes: List<Note> = emptyList(),
    onAddClick: () -> Unit = {},
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddClick()
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = null,
                )
            }
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(padding),
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
        NotesContent()
    }
}
