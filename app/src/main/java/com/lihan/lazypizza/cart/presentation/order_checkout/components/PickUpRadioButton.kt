package com.lihan.lazypizza.cart.presentation.order_checkout.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.lazypizza.core.presentation.design_system.AppRadioButton
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.body3Medium

@Composable
fun PickupRadioButton(
    isSelected: Boolean,
    onSelected: () -> Unit,
    optionName: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(100))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(100)
            )
            .clickable(onClick = onSelected)
            .padding(vertical = 16.dp)
            .padding(start = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AppRadioButton(
            selected = isSelected,
            onClick = null
        )
        Text(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            text = optionName,
            style = MaterialTheme.typography.body3Medium.copy(
                color = if (isSelected){
                    MaterialTheme.colorScheme.onBackground
                }else{
                    MaterialTheme.colorScheme.secondary
                }
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun PickupRadioButtonPreview() {
    LazyPizzaTheme {
        PickupRadioButton(
            isSelected = false,
            onSelected = {},
            optionName = "Earliest available time"
        )
    }
}