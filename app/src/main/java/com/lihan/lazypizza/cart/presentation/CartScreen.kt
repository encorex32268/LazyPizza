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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lihan.lazypizza.R
import com.lihan.lazypizza.cart.presentation.components.CartItemList
import com.lihan.lazypizza.cart.presentation.components.RecommendList
import com.lihan.lazypizza.core.presentation.components.AppDialog
import com.lihan.lazypizza.core.presentation.components.PlaceholderView
import com.lihan.lazypizza.core.presentation.design_system.AppButton
import com.lihan.lazypizza.core.presentation.design_system.ButtonType
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.body1Medium
import com.lihan.lazypizza.core.presentation.ui.theme.label2SemiBold
import com.lihan.lazypizza.menu.presentation.MenuState
import kotlin.random.Random

@Composable
fun CartRoot(
    onBackToMenu: () -> Unit,
    onNavigateToOrderCheckout: () -> Unit,
    sharedViewModel: CartSharedViewModel
) {
    val sharedState by sharedViewModel.state.collectAsStateWithLifecycle()

    CartScreen(
        sharedState = sharedState,
        onSharedAction = sharedViewModel::onAction,
        onBackToMenu = onBackToMenu,
        onProceedToCheckoutClick = onNavigateToOrderCheckout

    )
}

@Composable
private fun CartScreen(
    sharedState: CartSharedState,
    onSharedAction: (CartSharedAction) -> Unit,
    onBackToMenu: () -> Unit,
    onProceedToCheckoutClick: () -> Unit,
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
                sharedState.items.isEmpty() -> {
                    PlaceholderView(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 120.dp),
                        title = stringResource(R.string.cart_is_empty_title),
                        content = stringResource(R.string.cart_is_empty_content),
                        buttonText = stringResource(R.string.back_to_menu),
                        onClick = onBackToMenu
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
                                CartItemList(
                                    items = sharedState.items,
                                    onPlusClick = { cartItemId ->
                                        onSharedAction(CartSharedAction.OnPlusClick(cartItemId))
                                    },
                                    onMinusClick = { cartItemId ->
                                        onSharedAction(CartSharedAction.OnMinusClick(cartItemId))
                                    },
                                    onDeleteClick = { cartItemId , productId ->
                                        onSharedAction(
                                            CartSharedAction.OnDeleteClick(
                                                cartItemId = cartItemId,
                                                productId = productId
                                            ),
                                        )
                                    }
                                )

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
                                    RecommendList(
                                        recommendItems = sharedState.recommendItems,
                                        onAddClick = { id ->
                                            onSharedAction(CartSharedAction.OnAddItemClick(id))
                                        }
                                    )
                                }
                                item {
                                    Spacer(Modifier.height(32.dp))
                                }
                            }


                        }

                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f))
                                .height(100.dp)
                                .fillMaxWidth()
                                .blur(4.dp)
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
                                text = stringResource(R.string.proceed_to_checkout,sharedState.cartItemTotalPrice),
                                type = ButtonType.Filled,
                                onClick = onProceedToCheckoutClick
                            )
                        }
                    }

                }
            }
        }
        if (sharedState.error!=null){
            AppDialog(
                title = sharedState.error.asString(),
                confirmButtonText = stringResource(R.string.confirm),
                onConfirmClick = {
                    onSharedAction(CartSharedAction.OnDismissErrorDialog)
                },
                onDismiss = {}
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun CartScreenPreview() {
    LazyPizzaTheme {
        CartScreen(
            sharedState = CartSharedState(
                recommendItems = MenuState.fakeProductUiList.shuffled(random = Random(4)),
                items = CartPreviewData.items,
            ),
            onSharedAction = {},
            onBackToMenu = {},
            onProceedToCheckoutClick = {}
        )
    }
}