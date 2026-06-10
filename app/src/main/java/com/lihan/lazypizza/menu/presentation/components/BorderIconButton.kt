package com.lihan.lazypizza.menu.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.lazypizza.R
import com.lihan.lazypizza.core.presentation.Trash
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.outline50

@Composable
fun BorderIconButton(
    onClick: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.outline50
            )
            .size(22.dp)
            .clickable(
                onClick = onClick
            )
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ){
        content()
    }

}


@Preview(showBackground = true)
@Composable
private fun BorderIconButtonPreview() {
    LazyPizzaTheme {

        BorderIconButton(
            onClick = {},
            content = {
                Icon(
                    imageVector = Trash,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = stringResource(R.string.product_delete_icon)
                )
            }
        )
    }
}