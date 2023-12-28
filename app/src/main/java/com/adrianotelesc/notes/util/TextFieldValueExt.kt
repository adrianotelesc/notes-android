package com.adrianotelesc.notes.util

import androidx.compose.ui.text.input.TextFieldValue

val TextFieldValue.cursorLine: Int
    get() {
        val lineList = mutableListOf<Int>()
        text.forEachIndexed { index: Int, c: Char ->
            if (c == '\n') {
                lineList.add(index)
            }
        }

        if (lineList.isEmpty()) return 1

        lineList.forEachIndexed { index, lineEndIndex ->
            if (selection.start <= lineEndIndex) {
                return index + 1
            }
        }

        return lineList.size + 1
    }