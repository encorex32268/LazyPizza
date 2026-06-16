package com.lihan.lazypizza.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.lazypizza.core.presentation.design_system.AppButton
import com.lihan.lazypizza.core.presentation.design_system.ButtonType
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.body3Regular
import com.lihan.lazypizza.core.presentation.ui.theme.title1Medium

@Composable
fun PlaceholderView(
    title: String,
    content: String,
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.title1Medium.copy(
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.body3Regular.copy(
                color = MaterialTheme.colorScheme.secondary
            )
        )
        Spacer(Modifier.height(20.dp))
        AppButton(
            text = buttonText,
            onClick = onClick,
            type = ButtonType.Filled
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun PlaceholderViewPreview() {
    LazyPizzaTheme {
        PlaceholderView(
            title = "Your Cart Is Empty",
            content = "Head back to the menu and grab a pizza you love.",
            buttonText = "Back to Menu",
            onClick = {}
        )
    }
}