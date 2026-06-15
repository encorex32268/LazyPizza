@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.lazypizza.cart.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lihan.lazypizza.R
import com.lihan.lazypizza.core.presentation.components.ProductCard
import com.lihan.lazypizza.core.presentation.ui.theme.body1Medium
import com.lihan.lazypizza.core.presentation.ui.theme.label2SemiBold
import com.lihan.lazypizza.menu.presentation.ProductType
import org.koin.androidx.compose.koinViewModel

import com.lihan.lazypizza.cart.presentation.model.CartItemToppingUi
import com.lihan.lazypizza.cart.presentation.model.CartItemUi
import com.lihan.lazypizza.cart.presentation.model.CartItemWithToppingsUi
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme

@Composable
fun CartRoot(
    viewModel: CartViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CartScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun CartScreen(
    state: CartState,
    onAction: (CartAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.cart),
                        style = MaterialTheme.typography.body1Medium.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        ),
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) {  paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                columns = GridCells.Fixed(1),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = state.items,
                    key = { it.cartItem.cartItemId }
                ){ cartItemWithToppings ->
                    val cartItem = cartItemWithToppings.cartItem
                    val type = if (cartItem.productId.startsWith("pizza_")){
                        ProductType.Pizza
                    }else{
                        ProductType.Other
                    }
                    ProductCard(
                        type = type,
                        name = cartItem.name,
                        image = cartItem.imageUrl,
                        description = cartItemWithToppings.toppingsDescription,
                        quantity = cartItem.quantity,
                        price = "",
                        priceCalculate = cartItemWithToppings.priceDetail,
                        onItemClick = {},
                        onPlusClick = {},
                        onMinusClick = {},
                        onDeleteClick = {},
                        onAddToCartClick = {},
                        totalPrice = cartItemWithToppings.totalPrice,
                        isEditingMode = true
                    )

                }
                item{
                    Spacer(Modifier.height(20.dp))
                    Text(
                        text = stringResource(R.string.recommended_to_add_your_order),
                        style = MaterialTheme.typography.label2SemiBold.copy(
                            color = MaterialTheme.colorScheme.secondary
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                }
                item {
                    LazyHorizontalGrid(
                        modifier = Modifier.height(10.dp),
                        rows = GridCells.Adaptive(160.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {


                    }
                }
            }


        }


    }

}

@Preview(showBackground = true)
@Composable
private fun CartScreenPreview() {
    LazyPizzaTheme {
        CartScreen(
            state = CartState(
                items = listOf(
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
                )
            ),
            onAction = {}
        )
    }
}