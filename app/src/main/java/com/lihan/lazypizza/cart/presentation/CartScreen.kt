@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.lazypizza.cart.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lihan.lazypizza.R
import com.lihan.lazypizza.cart.presentation.components.RecommendProductCard
import com.lihan.lazypizza.core.presentation.components.ProductCard
import com.lihan.lazypizza.core.presentation.ui.theme.body1Medium
import com.lihan.lazypizza.core.presentation.ui.theme.label2SemiBold
import com.lihan.lazypizza.menu.presentation.ProductType
import org.koin.androidx.compose.koinViewModel

import com.lihan.lazypizza.cart.presentation.model.CartItemToppingUi
import com.lihan.lazypizza.cart.presentation.model.CartItemUi
import com.lihan.lazypizza.cart.presentation.model.CartItemWithToppingsUi
import com.lihan.lazypizza.core.domain.formatToTwoDecimals
import com.lihan.lazypizza.core.presentation.components.PlaceholderView
import com.lihan.lazypizza.core.presentation.design_system.AppButton
import com.lihan.lazypizza.core.presentation.design_system.ButtonType
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.menu.presentation.MenuState
import kotlin.random.Random

@Composable
fun CartRoot(
    onBackToMenu: () -> Unit,
    viewModel: CartViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CartScreen(
        state = state,
        onAction = { action ->
            when(action){
                is CartAction.OnBackToMenu -> onBackToMenu()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun CartScreen(
    state: CartState,
    onAction: (CartAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
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
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ){
            when{
                state.items.isEmpty() -> {
                    PlaceholderView(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 120.dp),
                        title = stringResource(R.string.cart_is_empty_title),
                        content = stringResource(R.string.cart_is_empty_content),
                        buttonText = stringResource(R.string.back_to_menu),
                        onClick = {
                            onAction(CartAction.OnBackToMenu)
                        }
                    )
                }
                else ->{
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            LazyVerticalGrid(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .animateContentSize(),
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
                                        modifier = Modifier.animateItem(),
                                        type = type,
                                        name = cartItem.name,
                                        image = cartItem.imageUrl,
                                        description = cartItemWithToppings.toppingsDescription,
                                        quantity = cartItem.quantity,
                                        priceCalculate = cartItemWithToppings.priceDetail,
                                        onPlusClick = {
                                            onAction(CartAction.OnPlusClick(cartItemWithToppings.cartItem.cartItemId))
                                        },
                                        onMinusClick = {
                                            onAction(CartAction.OnMinusClick(cartItemWithToppings.cartItem.cartItemId))
                                        },
                                        onDeleteClick = {
                                            onAction(
                                                CartAction.OnDeleteClick(
                                                    cartItemId = cartItemWithToppings.cartItem.cartItemId,
                                                    productId = cartItem.productId
                                                ),
                                            )
                                        },
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
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(216.dp)
                                            .animateContentSize(),
                                        rows = GridCells.Fixed(1),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        items(
                                            items = state.recommendItems,
                                            key = {productUi -> productUi.id}
                                        ) { productUi ->
                                            RecommendProductCard(
                                                modifier = Modifier
                                                    .padding(bottom = 8.dp)
                                                    .width(width = 160.dp)
                                                    .animateItem(),
                                                image = productUi.imageUrl,
                                                name = productUi.name,
                                                price = "$${productUi.price.formatToTwoDecimals()}",
                                                onAddClick = {
                                                    onAction(CartAction.OnAddItemClick(productUi.id))
                                                }
                                            )

                                        }
                                    }
                                }
                                item {
                                    Spacer(Modifier.height(32.dp))
                                }
                            }


                        }

                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.64f))
                                .height(100.dp)
                                .fillMaxWidth()
                                .blur(8.dp)
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.BottomCenter
                        ){
                            AppButton(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(R.string.proceed_to_checkout,state.cartItemTotalPrice),
                                type = ButtonType.Filled,
                                onClick = {
                                    //proceed
                                }
                            )
                        }
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

        CartScreen(
            state = CartState(
                recommendItems = MenuState.fakeProductUiList.shuffled(random = Random(4)),
                items = emptyList()
            ),
            onAction = {}
        )
    }
}