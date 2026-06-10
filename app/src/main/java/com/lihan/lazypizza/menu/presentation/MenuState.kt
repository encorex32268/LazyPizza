package com.lihan.lazypizza.menu.presentation

import androidx.compose.foundation.text.input.TextFieldState
import com.lihan.lazypizza.menu.presentation.model.ProductUi

data class MenuState(
    val searchTextFieldState: TextFieldState = TextFieldState(),
    val productUiList: List<ProductUi> = emptyList()
) {
    val items: Map<ProductType,List<ProductUi>> = productUiList.groupBy { it.type }

    companion object {
        val fakeProductUiList: List<ProductUi> = listOf(
            ProductUi(
                id = "1",
                name = "Pepperoni Pizza",
                description = "Freshly baked pizza with pepperoni, mozzarella, and tomato sauce.",
                imageUrl = "https://picsum.photos/200/300",
                price = 12.99,
                type = ProductType.Pizza
            ),
            ProductUi(
                id = "2",
                name = "Margherita",
                description = "Classic Margherita with basil, fresh mozzarella, and tomato sauce.",
                imageUrl = "https://picsum.photos/200/301",
                price = 10.50,
                type = ProductType.Pizza
            ),
            ProductUi(
                id = "3",
                name = "Hawaiian Pizza",
                description = "Ham and pineapple for a tropical flavor.",
                imageUrl = "https://picsum.photos/200/302",
                price = 13.99,
                type = ProductType.Pizza
            ),
            ProductUi(
                id = "4",
                name = "Coca Cola",
                description = "500ml classic soft drink.",
                imageUrl = "https://picsum.photos/200/303",
                price = 2.50,
                type = ProductType.Drinks
            ),
            ProductUi(
                id = "5",
                name = "Orange Juice",
                description = "Freshly squeezed orange juice.",
                imageUrl = "https://picsum.photos/200/304",
                price = 3.00,
                type = ProductType.Drinks
            ),
            ProductUi(
                id = "6",
                name = "Garlic Sauce",
                description = "Creamy garlic dipping sauce.",
                imageUrl = "https://picsum.photos/200/305",
                price = 0.50,
                type = ProductType.Sauces
            ),
            ProductUi(
                id = "7",
                name = "Spicy Mayo",
                description = "Mayo with a kick of chili.",
                imageUrl = "https://picsum.photos/200/306",
                price = 0.50,
                type = ProductType.Sauces
            ),
            ProductUi(
                id = "8",
                name = "Vanilla Cup",
                description = "Smooth vanilla ice cream cup.",
                imageUrl = "https://picsum.photos/200/307",
                price = 4.50,
                type = ProductType.IceCream
            ),
            ProductUi(
                id = "9",
                name = "Chocolate Tub",
                description = "Rich chocolate ice cream for the whole family.",
                imageUrl = "https://picsum.photos/200/308",
                price = 8.99,
                type = ProductType.IceCream
            )
        )
    }
}

