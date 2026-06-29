package com.lihan.lazypizza.cart.presentation.order_checkout.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.lazypizza.R
import com.lihan.lazypizza.cart.presentation.order_checkout.util.PickUpTimeType
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme

@Composable
fun PickUpTime(
    pickUpTimeType: PickUpTimeType,
    onSelected: (PickUpTimeType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PickupRadioButton(
            isSelected = pickUpTimeType == PickUpTimeType.EarliestAvailableTime,
            onSelected = {
                onSelected(pickUpTimeType)
            },
            optionName = stringResource(R.string.order_checkout_pickup_earliest_available_time),
            modifier = Modifier.fillMaxWidth()
        )
        PickupRadioButton(
            isSelected = pickUpTimeType == PickUpTimeType.ScheduleTime,
            onSelected = {
                onSelected(pickUpTimeType)
            },
            optionName = stringResource(R.string.order_checkout_pickup_schedule_time),
            modifier = Modifier.fillMaxWidth()
        )
    }

}


@Preview(showBackground = true)
@Composable
private fun PickUpTimePreview() {
    LazyPizzaTheme {
        PickUpTime(
            pickUpTimeType = PickUpTimeType.ScheduleTime,
            onSelected = {

            }
        )
    }
}
