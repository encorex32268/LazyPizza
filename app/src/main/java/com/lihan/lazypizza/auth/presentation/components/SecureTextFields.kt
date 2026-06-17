@file:OptIn(ExperimentalFoundationApi::class)

package com.lihan.lazypizza.auth.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.placeCursorAtEnd
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.lazypizza.R
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.body2Regular
import com.lihan.lazypizza.core.presentation.ui.theme.label2Medium
import com.lihan.lazypizza.core.presentation.ui.theme.surfaceHigher
import com.lihan.lazypizza.core.presentation.ui.theme.surfaceHighest

@Composable
fun SecureTextFields(
    textFieldStates: List<TextFieldState>,
    modifier: Modifier = Modifier,
    isOtpInvalid: Boolean = false
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            textFieldStates.forEachIndexed { index, textFieldState ->
                SecureField(
                    modifier = Modifier.weight(1f),
                    textFieldState = textFieldState,
                    onValueCommitted = {
                        if (index == textFieldStates.lastIndex){
                            focusManager.clearFocus()
                        }else{
                            focusManager.moveFocus(FocusDirection.Next)
                        }
                    },
                    inCorrectNumber = isOtpInvalid
                )
            }
        }
        if (isOtpInvalid){
            Text(
                text = stringResource(R.string.incorrect_number),
                style = MaterialTheme.typography.label2Medium.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
    }

}

@Composable
private fun SecureField(
    textFieldState: TextFieldState,
    onValueCommitted: () -> Unit,
    modifier: Modifier = Modifier,
    inCorrectNumber: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocus by interactionSource.collectIsFocusedAsState()

    BasicTextField(
        state = textFieldState,
        interactionSource = interactionSource,
        decorator = { innerField ->
            Box(
                contentAlignment = Alignment.Center
            ) {
                if (textFieldState.text.toString().isEmpty()) {
                    Text(
                        text = stringResource(R.string.secure_placeholder),
                        style = MaterialTheme.typography.body2Regular.copy(
                            color = MaterialTheme.colorScheme.secondary,
                            textAlign = TextAlign.Center
                        )
                    )
                }
                innerField()
            }
        },
        textStyle = MaterialTheme.typography.body2Regular.copy(
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        ),
        cursorBrush = SolidColor(Color.Transparent),
        lineLimits = TextFieldLineLimits.SingleLine,
        inputTransformation = InputTransformation {
            if (changes.changeCount > 0) {
                if (length > 1) {
                    val newChar = asCharSequence().last().toString()
                    replace(0, length, newChar)
                    placeCursorAtEnd()
                }
                if (length == 1) {
                    onValueCommitted()
                }
            }
        },
        modifier = modifier
            .widthIn(max = 64.dp)
            .clip(RoundedCornerShape(100))
            .border(
                width = 1.dp,
                color = if (isFocus || inCorrectNumber){
                    MaterialTheme.colorScheme.primary
                }else{
                    Color.Transparent
                },
                shape = RoundedCornerShape(100)
            )
            .background(
                color = if (isFocus || inCorrectNumber) {
                    MaterialTheme.colorScheme.surfaceHigher
                } else {
                    MaterialTheme.colorScheme.surfaceHighest
                }, shape = RoundedCornerShape(100)
            )
            .padding(
                vertical = 12.dp,
                horizontal = 20.dp
            )

    )

}

@Preview
@Composable
private fun SecureFieldPreview() {
    LazyPizzaTheme {
        SecureField(
            textFieldState = TextFieldState(initialText = "2"),
            onValueCommitted = {},
            inCorrectNumber = true
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun SecureTextFieldsPreview() {
    LazyPizzaTheme {
        SecureTextFields(
            textFieldStates = (0..5).map {
                TextFieldState()
            }
        )
    }
}