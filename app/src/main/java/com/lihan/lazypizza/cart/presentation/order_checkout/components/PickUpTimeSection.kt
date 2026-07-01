package com.lihan.lazypizza.cart.presentation.order_checkout.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.lazypizza.R
import com.lihan.lazypizza.cart.presentation.order_checkout.util.PickUpTimeType
import com.lihan.lazypizza.core.presentation.ui.theme.label2SemiBold

@Composable
fun PickUpTimeSection(
    pickUpTimeType: PickUpTimeType,
    onSelected: (PickUpTimeType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.pickup_time),
            style = MaterialTheme.typography.label2SemiBold,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(Modifier.height(12.dp))
        PickUpTime(
            pickUpTimeType = pickUpTimeType,
            onSelected = onSelected
        )
    }

}


@Preview(showBackground = true)
@Composable
private fun PickUpTimeSectionPreview() {

}