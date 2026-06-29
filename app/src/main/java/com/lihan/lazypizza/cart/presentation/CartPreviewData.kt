package com.lihan.lazypizza.cart.presentation

import com.lihan.lazypizza.cart.presentation.model.CartItemToppingUi
import com.lihan.lazypizza.cart.presentation.model.CartItemUi
import com.lihan.lazypizza.cart.presentation.model.CartItemWithToppingsUi


object CartPreviewData {
    val items = listOf(
        CartItemWithToppingsUi(
            cartItem = CartItemUi(
                cartItemId = 1,
                id = 1,
                productId = "pizza_1",
                quantity = 2,
                name = "Margherita Pizza",
                price = 12.99,
                imageUrl = ""
            ),
            toppings = listOf(
                CartItemToppingUi(
                    cartItemId = 1,
                    toppingId = "topping_1",
                    quantity = 1,
                    name = "Extra Cheese",
                    price = 1.50
                ),
                CartItemToppingUi(
                    cartItemId = 1,
                    toppingId = "topping_2",
                    quantity = 1,
                    name = "Mushrooms",
                    price = 0.75
                )
            )
        ),
        CartItemWithToppingsUi(
            cartItem = CartItemUi(
                cartItemId = 2,
                id = 2,
                productId = "drink_1",
                quantity = 1,
                name = "Coca Cola",
                price = 2.50,
                imageUrl = ""
            ),
            toppings = emptyList()
        )
        ,
        CartItemWithToppingsUi(
            cartItem = CartItemUi(
                cartItemId = 3,
                id = 3,
                productId = "drink_3",
                quantity = 1,
                name = "Coca Cola3",
                price = 2.50,
                imageUrl = ""
            ),
            toppings = emptyList()
        )
        ,
        CartItemWithToppingsUi(
            cartItem = CartItemUi(
                cartItemId = 4,
                id = 4,
                productId = "drink_4",
                quantity = 1,
                name = "Coca Cola4",
                price = 2.50,
                imageUrl = ""
            ),
            toppings = emptyList()
        )
    )
}
