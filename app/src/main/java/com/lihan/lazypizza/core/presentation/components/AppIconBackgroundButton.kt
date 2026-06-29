package com.lihan.lazypizza.core.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lihan.lazypizza.R
import com.lihan.lazypizza.core.presentation.ArrowLeft
import com.lihan.lazypizza.core.presentation.LogOut
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.primary8
import com.lihan.lazypizza.core.presentation.ui.theme.textSecondary8
import com.lihan.lazypizza.menu.presentation.product_detail.ProductDetailAction

@Composable
fun AppIconBackgroundButton(
    onClick: () -> Unit,
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    iconTintColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.primary8,
    shape: Shape = CircleShape,
    borderColor: Color = Color.Transparent,
    size: Dp = 32.dp,
) {
    IconButton(
        shape = shape,
        modifier = modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shape
            )
            .clip(shape)
            .size(size),
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = backgroundColor
        )
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = imageVector,
            tint = iconTintColor,
            contentDescription = null
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun AppIconBackgroundButtonPreview() {
    LazyPizzaTheme {
        AppIconBackgroundButton(
            onClick = {},
            imageVector = ArrowLeft,
            backgroundColor = MaterialTheme.colorScheme.textSecondary8,
            iconTintColor = MaterialTheme.colorScheme.secondary
        )
    }
}