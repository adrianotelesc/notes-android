package com.adrianotelesc.notes.ui.screen.noteeditor

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.adrianotelesc.notes.R
import com.adrianotelesc.notes.data.model.Note
import com.adrianotelesc.notes.ui.components.TextEditor
import com.adrianotelesc.notes.ui.preview.NotePreviewParameterProvider
import com.adrianotelesc.notes.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NoteEditorScreen(
    noteId: String?,
    viewModel: NoteEditorViewModel = koinViewModel(parameters = { parametersOf(noteId) }),
    navigateUp: () -> Unit = {}
) {
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
    uiState: NoteEditorUiState,
    updateNote: (text: String) -> Unit = {},
    navigateUp: () -> Unit = {},
) {
    val scrollState = rememberScrollState()
    var textFieldValue by remember {
        mutableStateOf(value = TextFieldValue(text = uiState.note.text))
    }

    LaunchedEffect(key1 = textFieldValue) {
        updateNote(textFieldValue.text)
    }

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
        TextEditor(
            modifier = Modifier.fillMaxSize(),
            padding = padding,
            scrollState = scrollState,
            autoFocus = textFieldValue.text.isEmpty(),
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContentPreview(
    @PreviewParameter(NotePreviewParameterProvider::class) note: Note,
) {
    AppTheme {
        Content(uiState = NoteEditorUiState(note = note))
    }
}
