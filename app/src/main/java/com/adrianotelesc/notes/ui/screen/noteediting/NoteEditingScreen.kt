package com.adrianotelesc.notes.ui.screen.noteediting

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.adrianotelesc.notes.R
import com.adrianotelesc.notes.data.model.Note
import com.adrianotelesc.notes.ui.preview.NotePreviewParameterProvider
import com.adrianotelesc.notes.ui.theme.AppTheme
import com.adrianotelesc.notes.util.cursorLine
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NoteEditingScreen(
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
private fun Content(
    uiState: NoteEditingUiState,
    updateNote: (text: String) -> Unit = {},
    navigateUp: () -> Unit = {},
) {
    val scrollState = rememberScrollState()
    val focusRequester = remember { FocusRequester() }
    var textFieldValue by remember { mutableStateOf(value = TextFieldValue(uiState.note.text)) }

    LaunchedEffect(key1 = textFieldValue) {
        updateNote(textFieldValue.text)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AppBar(onNavigationIconClick = navigateUp) },
    ) { padding ->
        TextEditor(
            modifier = Modifier.fillMaxSize(),
            scrollState = scrollState,
            focusRequester = focusRequester,
            padding = padding,
            autofocus = textFieldValue.text.isEmpty(),
            value = textFieldValue,
            onValueChange = { textFieldValue = it }
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AppBar(onNavigationIconClick: () -> Unit) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = null,
                )
            }
        },
    )
}

@Composable
private fun TextEditor(
    modifier: Modifier,
    scrollState: ScrollState = rememberScrollState(),
    focusRequester: FocusRequester = FocusRequester.Default,
    padding: PaddingValues = PaddingValues(),
    autofocus: Boolean = false,
    value: TextFieldValue = TextFieldValue(),
    onValueChange: (text: TextFieldValue) -> Unit,
) {
    val textStyle = MaterialTheme.typography.bodyLarge.copy(
        color = MaterialTheme.colorScheme.onBackground,
    )
    val lineHeight = with(LocalDensity.current) {
        textStyle.lineHeight.value.dp.roundToPx()
    }

    LaunchedEffect(key1 = Unit) {
        if (autofocus) focusRequester.requestFocus()
    }

    LaunchedEffect(value) {
        scrollState.animateScrollTo(value = lineHeight * (value.cursorLine - 1))
    }

    BasicTextField(
        modifier = modifier
            .verticalScroll(state = scrollState)
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 24.dp,
            )
            .focusRequester(focusRequester = focusRequester),
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        cursorBrush = SolidColor(value = MaterialTheme.colorScheme.primary),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier.padding(paddingValues = padding),
            ) {
                innerTextField()
            }
        }
    )
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
