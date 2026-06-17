package com.lihan.lazypizza.auth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.lazypizza.R
import com.lihan.lazypizza.auth.presentation.util.PhoneNumberInputTransformation
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.body2Regular
import com.lihan.lazypizza.core.presentation.ui.theme.surfaceHighest

@Composable
fun PhoneNumberTextField(
    textFieldState: TextFieldState,
    modifier: Modifier = Modifier
) {

    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    BasicTextField(
        state = textFieldState,
        decorator = { innerField ->
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ){
                if (textFieldState.text.isEmpty()){
                    Text(
                        text = stringResource(R.string.phone_number_place_holder),
                        style = MaterialTheme.typography.body2Regular.copy(
                            color = MaterialTheme.colorScheme.secondary
                        )
                    )
                }
                innerField()
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            showKeyboardOnFocus = true,
            imeAction = ImeAction.Done
        ),
        onKeyboardAction = {
            keyboard?.hide()
            focusManager.clearFocus(true)
        },
        inputTransformation = PhoneNumberInputTransformation,
        textStyle = MaterialTheme.typography.body2Regular.copy(color = MaterialTheme.colorScheme.onBackground),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(100))
            .background(
                color = MaterialTheme.colorScheme.surfaceHighest,
                shape = RoundedCornerShape(100)
            )
            .padding(vertical = 13.dp, horizontal = 20.dp)
    )

}


@Preview(showBackground = true)
@Composable
private fun PhoneNumberTextFieldPreview() {
    LazyPizzaTheme {
        PhoneNumberTextField(
            textFieldState = TextFieldState(
                initialText = "+1 234 567 8901"
            )
        )
    }
}