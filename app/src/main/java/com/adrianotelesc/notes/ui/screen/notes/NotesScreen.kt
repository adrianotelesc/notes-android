package com.adrianotelesc.notes.ui.screen.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.adrianotelesc.notes.R
import com.adrianotelesc.notes.data.model.Note
import com.adrianotelesc.notes.ui.preview.NotesPreviewParameterProvider
import com.adrianotelesc.notes.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotesScreen(
    viewModel: NotesViewModel = koinViewModel(),
    newNote: () -> Unit = {},
    openNote: (noteId: String?) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()

    Content(
        uiState = uiState,
        newNote = newNote,
        openNote = openNote,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(
    uiState: NotesUiState = NotesUiState(),
    newNote: () -> Unit = {},
    openNote: (noteId: String?) -> Unit = {},
) {
    val listState = rememberLazyStaggeredGridState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AppBar() },
        floatingActionButton = { Fab(onClick = newNote) },
    ) { padding ->
        Grid(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            listState = listState
        ) {
            items(items = uiState.notes) { note ->
                NoteCard(
                    onClick = openNote,
                    note = note
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AppBar() {
    TopAppBar(
        title = { Text(text = "Notes") },
    )
}

@Composable
private fun Fab(onClick: () -> Unit) {
    FloatingActionButton(onClick = { onClick() }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = null,
        )
    }
}

@Composable
private fun Grid(
    modifier: Modifier,
    listState: LazyStaggeredGridState,
    content: LazyStaggeredGridScope.() -> Unit
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Fixed(count = 2),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalItemSpacing = 8.dp,
        contentPadding = PaddingValues(all = 16.dp),
        state = listState,
        content = content,
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun NoteCard(
    onClick: (noteId: String?) -> Unit,
    note: Note
) {
    Card(onClick = { onClick(note.id) }) {
        Text(
            modifier = Modifier.padding(all = 16.dp),
            text = note.text,
            maxLines = 10,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContentPreview(
    @PreviewParameter(NotesPreviewParameterProvider::class) notes: List<Note>,
) {
    AppTheme {
        Content(uiState = NotesUiState(notes = notes))
    }
}
