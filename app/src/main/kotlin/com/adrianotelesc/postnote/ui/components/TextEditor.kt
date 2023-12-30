package com.adrianotelesc.postnote.ui.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.adrianotelesc.postnote.data.model.Note
import com.adrianotelesc.postnote.ui.preview.NotePreviewParameterProvider
import com.adrianotelesc.postnote.ui.theme.AppTheme
import com.adrianotelesc.postnote.util.selectionLine

@Composable
fun TextEditor(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(),
    scrollState: ScrollState = rememberScrollState(),
    autoFocus: Boolean = false,
    value: TextFieldValue = TextFieldValue(),
    onValueChange: (text: TextFieldValue) -> Unit = {},
) {
    val focusRequester = FocusRequester()

    LaunchedEffect(key1 = Unit) {
        if (autoFocus) focusRequester.requestFocus()
    }

    val textStyle = MaterialTheme.typography.bodyLarge.copy(
        color = MaterialTheme.colorScheme.onBackground,
    )
    val lineHeight = with(LocalDensity.current) {
        textStyle.lineHeight.value.dp.roundToPx()
    }

    LaunchedEffect(key1 = value) {
        scrollState.animateScrollTo(value = (value.selectionLine - 1) * lineHeight)
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
fun TextEditorPreview(
    @PreviewParameter(NotePreviewParameterProvider::class) note: Note,
) {
    AppTheme {
        TextEditor(
            value = TextFieldValue(text = note.text),
        )
    }
}