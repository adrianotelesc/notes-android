package com.adrianotelesc.notes.ui.screen.noteediting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.adrianotelesc.notes.R
import com.adrianotelesc.notes.data.model.Note
import com.adrianotelesc.notes.ui.preview.NotePreviewParameterProvider
import com.adrianotelesc.notes.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NoteDetailScreen(
    noteId: String?,
    viewModel: NoteEditingViewModel = koinViewModel(parameters = { parametersOf(noteId) }),
    navigateUp: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    Content(
        uiState = uiState,
        updateNote = viewModel::updateNote,
        navigateUp = navigateUp,
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Content(
    uiState: NoteEditingUiState,
    updateNote: (text: String) -> Unit = {},
    navigateUp: () -> Unit = {},
) {
    val appBarState = rememberTopAppBarState()
    val scrollState = rememberScrollState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(state = appBarState)
    val focusRequester = remember { FocusRequester() }
    var text by remember { mutableStateOf(value = uiState.note.text) }

    LaunchedEffect(key1 = text) {
        scrollState.animateScrollTo(value = scrollState.maxValue)
        updateNote(text)
        if (uiState.note.isEmpty) focusRequester.requestFocus()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {},
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = { navigateUp() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_left),
                            contentDescription = null,
                        )
                    }
                },
            )
        },
    ) { padding ->
        BasicTextField(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 24.dp,
                )
                .focusRequester(focusRequester = focusRequester),
            value = text,
            onValueChange = { text = it },
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onBackground,
            ),
            cursorBrush = SolidColor(value = MaterialTheme.colorScheme.primary),
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.padding(paddingValues = padding)) {
                    innerTextField()
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContentPreview(
    @PreviewParameter(NotePreviewParameterProvider::class) note: Note,
) {
    AppTheme {
        Content(uiState = NoteEditingUiState(note = note))
    }
}
