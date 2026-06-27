package com.lihan.lazypizza.history.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import com.lihan.lazypizza.core.presentation.ui.theme.ExtendsText
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.body4Regular
import com.lihan.lazypizza.core.presentation.ui.theme.label3Medium
import com.lihan.lazypizza.core.presentation.ui.theme.success
import com.lihan.lazypizza.core.presentation.ui.theme.surfaceHigher
import com.lihan.lazypizza.core.presentation.ui.theme.title3
import com.lihan.lazypizza.core.presentation.ui.theme.warning
import com.lihan.lazypizza.history.presentation.model.OrderStatus
import com.lihan.lazypizza.history.presentation.model.OrderStatus.Companion.toStringResourceId

@Composable
fun HistoryItemCard(
    orderNumber: String,
    createTime: String,
    status: OrderStatus,
    details: String,
    totalAmount: String,
    modifier: Modifier = Modifier
) {

    val statusBackground =  when(status){
        OrderStatus.InProgress -> MaterialTheme.colorScheme.warning
        OrderStatus.Completed -> MaterialTheme.colorScheme.success
    }

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceHigher
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.order_number,orderNumber),
                    style = MaterialTheme.typography.title3.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = createTime,
                    style = MaterialTheme.typography.body4Regular.copy(
                        color = MaterialTheme.colorScheme.secondary
                    ),
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    modifier = Modifier.fillMaxWidth().padding(end = 4.dp),
                    text = details,
                    style = MaterialTheme.typography.body4Regular.copy(
                        color = ExtendsText
                    ),
                )

            }
            Column{
                Box(
                    modifier = Modifier
                        .align(Alignment.End)
                        .clip(RoundedCornerShape(100))
                        .background(color = statusBackground , shape = RoundedCornerShape(100))
                        .padding(vertical = 3.dp, horizontal = 8.dp),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = stringResource(status.toStringResourceId()),
                        style = MaterialTheme.typography.label3Medium.copy(
                            color = MaterialTheme.colorScheme.background
                        )
                    )
                }
                Spacer(Modifier.weight(1f))

                Column {
                    Text(
                        text = stringResource(R.string.total_amount),
                        style = MaterialTheme.typography.body4Regular.copy(
                            color = MaterialTheme.colorScheme.secondary
                        )
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        modifier = Modifier.align(Alignment.End),
                        text = "$${totalAmount}",
                        style = MaterialTheme.typography.title3.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.End
                        )
                    )
                }

            }



        }
    }



}

@Preview
@Composable
private fun HistoryItemCardPreview() {
    LazyPizzaTheme {
        HistoryItemCard(
            orderNumber = "12347",
            createTime = "September 25, 12:15",
            status = OrderStatus.Completed,
            details = "1 x Margherita\n" +
                    "2 x Pepsi\n" +
                    "2 x Cookies Ice Cream",
            totalAmount = "$8.99"
        )
    }
}