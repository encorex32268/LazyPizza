package com.lihan.lazypizza.cart.presentation.order_checkout.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.lazypizza.R
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.body2Regular
import com.lihan.lazypizza.core.presentation.ui.theme.surfaceHighest

@Composable
fun CommentTextField(
    textFieldState: TextFieldState,
    onDone: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(R.string.add_comment)
) {


    BasicTextField(
        state = textFieldState,
        decorator = { innerTextField ->
            if (textFieldState.text.isEmpty()){
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.body2Regular.copy(
                        color = MaterialTheme.colorScheme.secondary
                    ),
                )
            }
            innerTextField()
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        onKeyboardAction = {
            onDone()
        },
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(
                color = MaterialTheme.colorScheme.surfaceHighest,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(
                vertical = 13.dp,
                horizontal = 20.dp
            )
        ,
        textStyle = MaterialTheme.typography.body2Regular.copy(
            color = MaterialTheme.colorScheme.onBackground
        ),
    )

}


@Preview(showBackground = true)
@Composable
private fun CommentTextFieldPreview() {
    LazyPizzaTheme {
        CommentTextField(
            textFieldState = TextFieldState(
                "1234"
            ),
            placeholder = "Add Comment",
            modifier = Modifier
                .size(300.dp),
            onDone = {}
        )
    }
}