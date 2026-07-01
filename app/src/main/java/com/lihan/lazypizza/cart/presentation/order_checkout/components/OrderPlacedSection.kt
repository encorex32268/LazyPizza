package com.lihan.lazypizza.cart.presentation.order_checkout.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.lazypizza.R
import com.lihan.lazypizza.core.presentation.design_system.AppButton
import com.lihan.lazypizza.core.presentation.design_system.ButtonType
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.body3Regular
import com.lihan.lazypizza.core.presentation.ui.theme.label2Medium
import com.lihan.lazypizza.core.presentation.ui.theme.label2SemiBold
import com.lihan.lazypizza.core.presentation.ui.theme.surfaceHigher
import com.lihan.lazypizza.core.presentation.ui.theme.title1Medium

@Composable
fun OrderPlacedSection(
    orderNumber: String,
    pickUpTime: String,
    onNavigateToMenu: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.surfaceHigher)
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.your_order_has_been_placed),
            style = MaterialTheme.typography.title1Medium.copy(
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = stringResource(R.string.thank_you_order),
            style = MaterialTheme.typography.body3Regular.copy(
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center
            )
        )
        Spacer(Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(12.dp)
                )
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.order_checkout_order_number),
                    style = MaterialTheme.typography.label2Medium.copy(
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
                Text(
                    text = orderNumber,
                    style = MaterialTheme.typography.label2SemiBold.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.order_checkout_pickup_time),
                    style = MaterialTheme.typography.label2Medium.copy(
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
                Text(
                    text = pickUpTime,
                    style = MaterialTheme.typography.label2SemiBold.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }

        }
        Spacer(Modifier.height(20.dp))
        AppButton(
            text = stringResource(R.string.back_to_menu),
            onClick = onNavigateToMenu,
            type = ButtonType.Text
        )

    }


}


@Preview(showBackground = true)
@Composable
private fun OrderPlacedSectionPreview() {
    LazyPizzaTheme {
        OrderPlacedSection(
            orderNumber = "#12345",
            pickUpTime = "September 25, 12:15",
            onNavigateToMenu = {}
        )
    }
}