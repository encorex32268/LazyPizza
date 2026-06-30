package com.lihan.lazypizza.core.presentation.util

import androidx.compose.foundation.text.input.InputTransformation

val hourTransformation = InputTransformation {
    val newText = asCharSequence().toString()
    if (!limitTimeInput(newText, maxVal = 23)) {
        revertAllChanges()
    }
}

val minuteTransformation = InputTransformation {
    val newText = asCharSequence().toString()
    if (!limitTimeInput(newText, maxVal = 59)) {
        revertAllChanges()
    }
}

private fun limitTimeInput(
    newText: String,
    maxVal: Int
): Boolean {

    if (newText.isEmpty()) return true

    if (!newText.all { it.isDigit() }) return false

    if (newText.length > 2) return false

    val value = newText.toIntOrNull() ?: 0
    return value <= maxVal
}
