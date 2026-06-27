package com.lihan.lazypizza.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.lihan.lazypizza.R
import com.lihan.lazypizza.core.presentation.Trash
import com.lihan.lazypizza.core.presentation.design_system.AppButton
import com.lihan.lazypizza.core.presentation.design_system.ButtonType
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.body1Medium
import com.lihan.lazypizza.core.presentation.ui.theme.body3Regular
import com.lihan.lazypizza.core.presentation.ui.theme.body4Regular
import com.lihan.lazypizza.core.presentation.ui.theme.surfaceHigher
import com.lihan.lazypizza.core.presentation.ui.theme.surfaceHighest
import com.lihan.lazypizza.core.presentation.ui.theme.title1SemiBold
import com.lihan.lazypizza.menu.presentation.MenuState
import com.lihan.lazypizza.menu.presentation.ProductType
import com.lihan.lazypizza.menu.presentation.model.ProductUi

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    image: Any? = null,
    description: String = "",
    price: String = "",
    isEditingMode: Boolean = false,
    onItemClick: (() -> Unit)?=null,
    onAddToCartClick: (() -> Unit)?=null,
    type: ProductType,
    name: String,
    quantity: Int,
    totalPrice: String,
    priceCalculate: String,
    onDeleteClick: () -> Unit,
    onMinusClick: () -> Unit,
    onPlusClick: () -> Unit,

) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .pointerInput(Unit){
                detectTapGestures(onTap = {
                    onItemClick?.invoke()
                })
            },
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.surfaceHigher),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceHigher
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .widthIn(min = 100.dp , max = 120.dp)
                    .padding(1.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 12.dp,
                            bottomStart = 12.dp
                        )
                    )
                    .background(MaterialTheme.colorScheme.surfaceHighest)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                AppAsyncImage(
                    image =image
                )
            }

            if (isEditingMode){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = name,
                            style = MaterialTheme.typography.body1Medium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        BorderIconButton(
                            onClick = onDeleteClick,
                            content = {
                                Icon(
                                    imageVector = Trash,
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = stringResource(R.string.product_delete_icon)
                                )
                            }
                        )

                    }
                    Box(
                        modifier = Modifier.weight(1f)
                    ){
                        if (description.isNotEmpty()){
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = description,
                                style = MaterialTheme.typography.body3Regular,
                                color = MaterialTheme.colorScheme.secondary,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ItemCounter(
                            count = quantity,
                            onMinusClick = onMinusClick,
                            onPlusClick = onPlusClick
                        )
                        Spacer(Modifier.weight(1f))
                        Column(
                            horizontalAlignment = Alignment.End
                        ){
                            Text(
                                text =  totalPrice,
                                style = MaterialTheme.typography.title1SemiBold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = priceCalculate,
                                style = MaterialTheme.typography.body4Regular,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }
            }else{
                if (type != ProductType.Pizza){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 16.dp)
                    ) {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.body1Medium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(Modifier.weight(1f))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text =  price,
                                style = MaterialTheme.typography.title1SemiBold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            AppButton(
                                text = stringResource(R.string.add_to_cart),
                                type = ButtonType.Outline,
                                onClick = {
                                    onAddToCartClick?.invoke()
                                }
                            )
                        }
                    }
                }else{
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 16.dp)
                    ) {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.body1Medium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        if (description.isNotEmpty()){
                            Spacer(Modifier.height(4.dp))
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = description,
                                style = MaterialTheme.typography.body3Regular,
                                color = MaterialTheme.colorScheme.secondary,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Spacer(Modifier.weight(1f))
                        Text(
                            text =  price,
                            style = MaterialTheme.typography.title1SemiBold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ProductCardPreview() {
    LazyPizzaTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val pizza = MenuState.fakeProductUiList.first()
            ProductCard(
                image = pizza.imageUrl,
                description = pizza.description,
                type = pizza.type,
                name = pizza.name,
                price = pizza.priceString,
                quantity = pizza.count,
                totalPrice = pizza.priceTotal,
                priceCalculate = pizza.priceTotalDetail,
                onItemClick = {},
                onDeleteClick = {},
                onMinusClick = {},
                onPlusClick = {},
                onAddToCartClick = {}
            )

            ProductCard(
                image = pizza.imageUrl,
                description = "" +
                        "1 x Extra Cheese\n" +
                        "1 x Pepperoni\n" +
                        "1 x Pepperoni\n" +
                        "1 x Pepperoni\n",
                type = pizza.type,
                name = pizza.name,
                price = pizza.priceString,
                quantity = pizza.count,
                totalPrice = pizza.priceTotal,
                priceCalculate = pizza.priceTotalDetail,
                isEditingMode = true,
                onItemClick = {},
                onDeleteClick = {},
                onMinusClick = {},
                onPlusClick = {},
                onAddToCartClick = {}
            )

            val drinkEditing = MenuState.fakeProductUiList
                .first { it.type == ProductType.Drinks }
                .copy(isEditingMode = true, count = 2)
            ProductCard(
                image = drinkEditing.imageUrl,
                description = drinkEditing.description,
                isEditingMode = drinkEditing.isEditingMode,
                type = drinkEditing.type,
                name = drinkEditing.name,
                price = drinkEditing.priceString,
                quantity = drinkEditing.count,
                totalPrice = drinkEditing.priceTotal,
                priceCalculate = drinkEditing.priceTotalDetail,
                onItemClick = {},
                onDeleteClick = {},
                onMinusClick = {},
                onPlusClick = {},
                onAddToCartClick = {}
            )

            val drinkNormal = MenuState.fakeProductUiList
                .first { it.type == ProductType.Drinks }
            ProductCard(
                image = drinkNormal.imageUrl,
                description = drinkNormal.description,
                isEditingMode = false,
                type = drinkNormal.type,
                name = drinkNormal.name,
                price = drinkNormal.priceString,
                quantity = drinkNormal.count,
                totalPrice = drinkNormal.priceTotal,
                priceCalculate = drinkNormal.priceTotalDetail,
                onItemClick = {},
                onDeleteClick = {},
                onMinusClick = {},
                onPlusClick = {},
                onAddToCartClick = {}
            )
        }
    }
}