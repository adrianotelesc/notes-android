package com.adrianotelesc.postnote.ui.screen.noteeditor

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.adrianotelesc.postnote.R
import com.adrianotelesc.postnote.data.model.Note
import com.adrianotelesc.postnote.ui.component.TextEditor
import com.adrianotelesc.postnote.ui.preview.NotePreviewParameterProvider
import com.adrianotelesc.postnote.ui.theme.PostnoteTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun NoteEditorScreen(
    noteId: String?,
    viewModel: NoteEditorViewModel = koinViewModel(),
    navigateUp: () -> Unit = {}
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.loadNoteBy(id = noteId)
    }

    val uiState by viewModel.uiState.collectAsState()
    Content(
        uiState = uiState,
        updateNote = viewModel::updateNote,
        navigateUp = navigateUp,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    uiState: NoteEditorUiState = NoteEditorUiState(),
    updateNote: (text: String) -> Unit = {},
    navigateUp: () -> Unit = {},
) {
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_left),
                            contentDescription = null,
                        )
                    }
                },
            )
        },
    ) { padding ->
        if (uiState.isActive) {
            var textFieldValue by rememberSaveable(
                uiState.note.text,
                stateSaver = TextFieldValue.Saver,
            ) {
                mutableStateOf(
                    value = TextFieldValue(
                        text = uiState.note.text,
                        selection = TextRange(uiState.note.text.length),
                    ),
                )
            }
            TextEditor(
                modifier = Modifier.fillMaxSize(),
                padding = padding,
                scrollState = scrollState,
                autoFocus = uiState.note.isEmpty,
                value = textFieldValue,
                onValueChange = { value ->
                    textFieldValue = value.also { updateNote(value.text) }
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContentPreview(
    @PreviewParameter(NotePreviewParameterProvider::class) note: Note,
) {
    PostnoteTheme {
        Content(uiState = NoteEditorUiState(note = note))
    }
}
