package com.lihan.lazypizza.cart.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.lazypizza.cart.presentation.CartPreviewData
import com.lihan.lazypizza.cart.presentation.model.CartItemWithToppingsUi
import com.lihan.lazypizza.core.presentation.components.ProductCard
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme

fun LazyGridScope.CartItemList(
    onPlusClick: (Long) -> Unit,
    onMinusClick: (Long) -> Unit,
    onDeleteClick: (Long,String) -> Unit,
    items: List<CartItemWithToppingsUi>
) {
    items(
        items = items,
        key = { it.cartItem.cartItemId }
    ){ cartItemWithToppings ->
        val cartItem = cartItemWithToppings.cartItem
        ProductCard(
            modifier = Modifier.animateItem(),
            type = cartItem.productType,
            name = cartItem.name,
            image = cartItem.imageUrl,
            description = cartItemWithToppings.toppingsDescription,
            quantity = cartItem.quantity,
            priceCalculate = cartItemWithToppings.priceDetail,
            onPlusClick = {
                onPlusClick(cartItemWithToppings.cartItem.cartItemId)
            },
            onMinusClick = {
                onMinusClick(cartItemWithToppings.cartItem.cartItemId)
            },
            onDeleteClick = {
                onDeleteClick(cartItemWithToppings.cartItem.cartItemId,cartItem.productId)
            },
            totalPrice = cartItemWithToppings.totalPrice,
            isEditingMode = true
        )

    }

}


@Preview(showBackground = true)
@Composable
private fun CartItemListPreview() {
    LazyPizzaTheme {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CartItemList(
                onDeleteClick = { _, _ ->

                },
                onPlusClick = {},
                onMinusClick = {},
                items = CartPreviewData.items
            )
        }

    }
}