package com.lihan.lazypizza.menu.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.body3Medium
import com.lihan.lazypizza.core.presentation.ui.theme.surfaceHigher
import com.lihan.lazypizza.menu.presentation.ProductType
import com.lihan.lazypizza.menu.presentation.ProductType.Companion.toTypeName

@Composable
fun ProductTypeChip(
    isSelected: Boolean,
    type: ProductType,
    onClick: (ProductType) -> Unit,
    modifier: Modifier = Modifier
) {
    SuggestionChip(
        modifier = modifier,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = if (isSelected){
                MaterialTheme.colorScheme.surfaceHigher
            }else{
                Color.Transparent
            },
            labelColor = MaterialTheme.colorScheme.onBackground,
        ),
        onClick = {
            onClick(type)
        },
        label = {
            Text(
                text = type.toTypeName(),
                style = MaterialTheme.typography.body3Medium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
            )
        }
    )

}


@Preview(showBackground = true)
@Composable
private fun ProductTypeChipPreview() {
    LazyPizzaTheme {
        ProductTypeChip(
            isSelected = false,
            type = ProductType.entries.random(),
            onClick = {}
        )
    }
}