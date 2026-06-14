package com.lihan.lazypizza.menu.presentation.product_detail.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.lazypizza.core.domain.formatToTwoDecimals2
import com.lihan.lazypizza.core.presentation.components.AppAsyncImage
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.body3Regular
import com.lihan.lazypizza.core.presentation.ui.theme.primary8
import com.lihan.lazypizza.core.presentation.ui.theme.surfaceHigher
import com.lihan.lazypizza.core.presentation.ui.theme.title2
import com.lihan.lazypizza.core.presentation.components.ItemCounter
import com.lihan.lazypizza.menu.presentation.model.ToppingUi

@Composable
fun ToppingCard(
    toppingUi: ToppingUi,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceHigher
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (toppingUi.isEditingMode){
                MaterialTheme.colorScheme.primary
            }else{
                MaterialTheme.colorScheme.outline
            }
        ),
        onClick = onItemClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary8),
                contentAlignment = Alignment.Center
            ){
                AppAsyncImage(
                    image = toppingUi.imageUrl,
                    modifier = Modifier.size(56.dp)
                )
            }

            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = toppingUi.name,
                style = MaterialTheme.typography.body3Regular.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )
            if (toppingUi.isEditingMode){
                ItemCounter(
                    count = toppingUi.count,
                    onPlusClick = onPlusClick,
                    onMinusClick = onMinusClick
                )
            }else{
                Text(
                    text = "$${toppingUi.price.formatToTwoDecimals2()}",
                    style = MaterialTheme.typography.title2.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )

            }


        }

    }

}


@Preview(showBackground = true)
@Composable
private fun ToppingCardPreview() {
    LazyPizzaTheme {
        Box(
            modifier = Modifier.padding(8.dp),
            contentAlignment = Alignment.Center
        ){
            ToppingCard(
                toppingUi = ToppingUi(
                    id = "Topping",
                    name = "BaconBaconBaconBacon",
                    price = 1.0,
                    imageUrl = "",
                    isEditingMode = true
                ),
                onPlusClick = {},
                onMinusClick = {},
                onItemClick = {}
            )
        }
    }
}