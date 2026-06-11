package com.lihan.lazypizza.menu.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.lihan.lazypizza.R
import com.lihan.lazypizza.core.presentation.Search
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.body1Regular
import com.lihan.lazypizza.core.presentation.ui.theme.surfaceHigher

@Composable
fun ProductSearchbar(
    textFieldState: TextFieldState,
    modifier: Modifier = Modifier
) {

    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    BasicTextField(
        state = textFieldState,
        decorator = { innerTextField ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .dropShadow(
                            shape = RoundedCornerShape(20.dp),
                            shadow = Shadow(
                                radius = 2.dp,
                                spread = 0.dp,
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.075f),
                                offset = DpOffset(x = 0.dp, 4.dp)
                            )
                        ),
                    imageVector = Search,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null
                )
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ){
                    if (textFieldState.text.isEmpty()){
                        Text(
                            text = stringResource(R.string.menu_search_bar_place_holder),
                            style = MaterialTheme.typography.body1Regular,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    innerTextField()
                }
            }



        },
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        onKeyboardAction = {
            keyboard?.hide()
            focusManager.clearFocus()
        },
        modifier = modifier
            .clip(RoundedCornerShape(28.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(28.dp)
            )
            .background(MaterialTheme.colorScheme.surfaceHigher)
            .dropShadow(
                shape = RoundedCornerShape(28.dp),
                shadow = Shadow(
                    radius = 16.dp,
                    spread = 0.dp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.04f),
                    offset = DpOffset(x = 0.dp, 4.dp)
                )
            )
            .padding(
                vertical = 14.dp,
                horizontal = 16.dp
            )

    )

}


@Preview(showBackground = true)
@Composable
private fun ProductSearchbarPreview() {
    LazyPizzaTheme {
        ProductSearchbar(
            textFieldState = TextFieldState()
        )
    }
}