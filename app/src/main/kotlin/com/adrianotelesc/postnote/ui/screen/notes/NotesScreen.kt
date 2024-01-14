package com.adrianotelesc.postnote.ui.screen.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.adrianotelesc.postnote.R
import com.adrianotelesc.postnote.data.model.Note
import com.adrianotelesc.postnote.ui.component.StickyNote
import com.adrianotelesc.postnote.ui.preview.NotesPreviewParameterProvider
import com.adrianotelesc.postnote.ui.theme.PostnoteTheme
import com.adrianotelesc.postnote.util.collectInLaunchedEffectWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotesScreen(
    viewModel: NotesViewModel = koinViewModel(),
    navigateToNoteEditor: (noteId: String?) -> Unit = {},
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.loadNotes()
    }

    viewModel.uiEffect.collectInLaunchedEffectWithLifecycle { uiEffect ->
        when (uiEffect) {
            is NotesUiEffect.NavigateToNoteEditor -> navigateToNoteEditor(uiEffect.noteId)
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Content(
        uiState = uiState,
        newNote = viewModel::createOrOpenNote,
        openNote = viewModel::createOrOpenNote,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(
    uiState: NotesUiState = NotesUiState(),
    newNote: () -> Unit = {},
    openNote: (note: Note) -> Unit = {},
) {
    val listState = rememberLazyStaggeredGridState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { newNote() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = null,
                )
            }
        },
    ) { padding ->
        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = padding),
            columns = StaggeredGridCells.Fixed(count = 2),
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
            verticalItemSpacing = 8.dp,
            contentPadding = PaddingValues(all = 16.dp),
            state = listState,
        ) {
            items(items = uiState.notes) { note ->
                StickyNote(
                    text = note.text,
                    onClick = { openNote(note) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContentPreview(
    @PreviewParameter(NotesPreviewParameterProvider::class) notes: List<Note>,
) {
    PostnoteTheme {
        Content(uiState = NotesUiState(notes = notes))
    }
}
