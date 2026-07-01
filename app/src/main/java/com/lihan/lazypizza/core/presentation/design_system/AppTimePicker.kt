@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.lazypizza.core.presentation.design_system

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.lazypizza.R
import com.lihan.lazypizza.core.domain.util.TimeUtil
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.label2SemiBold
import com.lihan.lazypizza.core.presentation.ui.theme.medium
import com.lihan.lazypizza.core.presentation.ui.theme.outline50
import com.lihan.lazypizza.core.presentation.ui.theme.surfaceHigher
import com.lihan.lazypizza.core.presentation.ui.theme.surfaceHighest
import com.lihan.lazypizza.core.presentation.ui.theme.title4
import com.lihan.lazypizza.core.presentation.util.hourTransformation
import com.lihan.lazypizza.core.presentation.util.minuteTransformation
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import java.util.Locale

@Composable
fun AppTimePickerRoot(
    onDismissRequest: () -> Unit,
    onCancel: () -> Unit,
    onTimeConfirm: (String) -> Unit,
    modifier: Modifier = Modifier,
    initialHour: String = TimeUtil.getNowHour(),
    initialMinute: String = TimeUtil.getNowMinute()
) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        AppTimePicker(
            onTimeConfirm = onTimeConfirm,
            onCancel = onCancel,
            onDismissRequest = onDismissRequest,
            initialHour = initialHour,
            initialMinute = initialMinute
        )
    }
}


@Composable
fun AppTimePicker(
    onDismissRequest: () -> Unit,
    onCancel: () -> Unit,
    onTimeConfirm: (String) -> Unit,
    modifier: Modifier = Modifier,
    initialHour: String = "",
    initialMinute: String = ""
) {
    val hourTextFieldState = rememberSaveable(saver = TextFieldState.Saver) {
        TextFieldState(initialHour)
    }
    val minuteTextFieldState = rememberSaveable(saver = TextFieldState.Saver) {
        TextFieldState(initialMinute)
    }

    var isShowError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        combine(
            flow = snapshotFlow { hourTextFieldState.text.toString() },
            flow2 = snapshotFlow { minuteTextFieldState.text.toString() }
        ) { hourText, minute ->

            if (hourText.isNotEmpty() && minute.isNotEmpty()) {
                val modifiedMinute =
                    String.format(locale = Locale.getDefault(), "%02d", minute.toIntOrNull() ?: 0)

                val timeInt = (hourText + modifiedMinute).toInt()

                isShowError = timeInt !in 1015..2145
            }
        }.launchIn(this)
    }



    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceHigher
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(top = 24.dp),
                text = stringResource(R.string.select_time),
                style = MaterialTheme.typography.label2SemiBold.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(top = 16.dp, bottom = 8.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    TimeTextField(
                        modifier = Modifier.fillMaxWidth(),
                        textFieldState = hourTextFieldState,
                        limitTransformation = hourTransformation
                    )
                    Text(
                        text = stringResource(R.string.hour),
                        style = MaterialTheme.typography.title4.copy(
                            color = MaterialTheme.colorScheme.secondary
                        )
                    )
                }
                Text(
                    modifier = Modifier
                        .width(24.dp)
                        .height(72.dp),
                    text = ":",
                    style = MaterialTheme.typography.medium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                )
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    TimeTextField(
                        modifier = Modifier.fillMaxWidth(),
                        textFieldState = minuteTextFieldState,
                        limitTransformation = minuteTransformation
                    )
                    Text(
                        text = stringResource(R.string.minute),
                        style = MaterialTheme.typography.title4.copy(
                            color = MaterialTheme.colorScheme.secondary
                        )
                    )
                }
            }
            if (isShowError) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = stringResource(R.string.timepicker_error_message),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
            Spacer(Modifier.height(8.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline50)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 12.dp,
                        horizontal = 16.dp
                    )
                    .height(40.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppButton(
                    text = stringResource(R.string.cancel),
                    onClick = onCancel,
                    type = ButtonType.Text
                )
                AppButton(
                    text = stringResource(R.string.ok),
                    onClick = {
                        val hour = hourTextFieldState.text.toString()
                        val minutes = minuteTextFieldState.text.toString()
                        if (hour.isNotEmpty() && minutes.isNotEmpty()) {
                            onTimeConfirm("$hour:$minutes")
                        }
                    },
                    type = ButtonType.Filled,
                    enabled = hourTextFieldState.text.isNotEmpty() && minuteTextFieldState.text.isNotEmpty() && !isShowError
                )
            }
        }
    }
}

@Composable
private fun TimeTextField(
    textFieldState: TextFieldState,
    limitTransformation: InputTransformation,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocus by interactionSource.collectIsFocusedAsState()

    BasicTextField(
        state = textFieldState,
        interactionSource = interactionSource,
        decorator = { inner ->
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (textFieldState.text.isEmpty()) {
                    Text(
                        text = stringResource(R.string.timepicker_placeholder),
                        style = MaterialTheme.typography.medium.copy(
                            color = MaterialTheme.colorScheme.secondary,
                            textAlign = TextAlign.Center
                        )
                    )
                }
                inner()
            }
        },
        lineLimits = TextFieldLineLimits.SingleLine,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.secondary),
        textStyle = MaterialTheme.typography.medium.copy(
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        ),
        inputTransformation = limitTransformation,
        modifier = modifier
            .onFocusChanged { focusState ->
                if (!focusState.isFocused && textFieldState.text.length == 1) {
                    textFieldState.setTextAndPlaceCursorAtEnd(
                        "%02d".format(textFieldState.text.toString().toInt())
                    )
                }
            }
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = if (isFocus) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.Transparent
                },
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = if (isFocus) {
                    MaterialTheme.colorScheme.surfaceHigher
                } else {
                    MaterialTheme.colorScheme.surfaceHighest
                },
                shape = RoundedCornerShape(12.dp)
            )
            .padding(
                vertical = 12.dp,
                horizontal = 22.dp
            )
    )

}



@Preview(showBackground = true)
@Composable
private fun AppTimePickerPreview() {
    LazyPizzaTheme {
        AppTimePicker(
            onDismissRequest = {},
            onTimeConfirm = {},
            onCancel ={},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TimeTextFieldPreview() {
    LazyPizzaTheme {
        TimeTextField(
            textFieldState = TextFieldState(initialText = "22"),
            limitTransformation = hourTransformation
        )
    }

}