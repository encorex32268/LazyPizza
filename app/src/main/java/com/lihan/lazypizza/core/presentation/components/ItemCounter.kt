package com.lihan.lazypizza.menu.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.lazypizza.R
import com.lihan.lazypizza.core.presentation.Minus
import com.lihan.lazypizza.core.presentation.Plus
import com.lihan.lazypizza.core.presentation.Trash
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.title2

@Composable
fun ItemCounter(
    count: Int,
    onMinusClick: () -> Unit,
    onPlusClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .widthIn(max = 100.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BorderIconButton(
            onClick = onMinusClick,
            content = {
                Icon(
                    imageVector = Minus,
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = stringResource(R.string.product_counter_minus)
                )
            }
        )
        Text(
            modifier = Modifier.weight(1f),
            text = count.toString(),
            style = MaterialTheme.typography.title2.copy(
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            ),
        )

        BorderIconButton(
            onClick = onPlusClick,
            content = {
                Icon(
                    imageVector = Plus,
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = stringResource(R.string.product_counter_plus)
                )
            }
        )
    }


}


@Preview(showBackground = true)
@Composable
private fun ItemCounterPreview() {
    LazyPizzaTheme {
        ItemCounter(
            count = 101,
            onPlusClick = {},
            onMinusClick = {}
        )
    }
}