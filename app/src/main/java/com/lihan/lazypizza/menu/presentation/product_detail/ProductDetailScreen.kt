@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.lazypizza.menu.presentation.product_detail

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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lihan.lazypizza.R
import com.lihan.lazypizza.core.presentation.ArrowLeft
import com.lihan.lazypizza.core.presentation.components.AppAsyncImage
import com.lihan.lazypizza.core.presentation.components.AppIconBackgroundButton
import com.lihan.lazypizza.core.presentation.design_system.AppButton
import com.lihan.lazypizza.core.presentation.design_system.ButtonType
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.body3Regular
import com.lihan.lazypizza.core.presentation.ui.theme.label2SemiBold
import com.lihan.lazypizza.core.presentation.ui.theme.surfaceHigher
import com.lihan.lazypizza.core.presentation.ui.theme.textSecondary8
import com.lihan.lazypizza.core.presentation.ui.theme.title1SemiBold
import com.lihan.lazypizza.core.presentation.util.ObserveAsEvents
import com.lihan.lazypizza.menu.presentation.ProductType
import com.lihan.lazypizza.menu.presentation.model.ProductUi
import com.lihan.lazypizza.menu.presentation.model.ToppingUi
import com.lihan.lazypizza.menu.presentation.product_detail.components.ToppingCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductDetailRoot(
    onBack: () -> Unit,
    viewModel: ProductDetailViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.uiEvent) { uiEvent ->
        when(uiEvent){
            ProductDetailUiEvent.OnBack -> onBack()
        }
    }

    ProductDetailScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun ProductDetailScreen(
    state: ProductDetailState,
    onAction: (ProductDetailAction) -> Unit,
    modifier: Modifier = Modifier,
) {

    Scaffold(
        modifier =  modifier,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.outline
                ),
                title = {},
                navigationIcon = {
                    AppIconBackgroundButton(
                        onClick = {
                            onAction(ProductDetailAction.OnBack)
                        },
                        imageVector = ArrowLeft,
                        backgroundColor = MaterialTheme.colorScheme.textSecondary8,
                        iconTintColor = MaterialTheme.colorScheme.secondary
                    )
                }
            )
        }
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ){
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(bottomEnd = 16.dp))
                        .background(color = MaterialTheme.colorScheme.outline)
                        .fillMaxWidth()
                        .height(240.dp),
                    contentAlignment = Alignment.Center
                ){
                    AppAsyncImage(
                        image = state.product?.imageUrl,
                    )
                }
                if (state.product != null){
                    val product = state.product
                    Column(
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.outline)
                            .clip(RoundedCornerShape(topStart = 16.dp))
                            .background(MaterialTheme.colorScheme.surfaceHigher)
                            .fillMaxWidth()
                            .padding(vertical = 20.dp, horizontal = 16.dp)
                    ) {
                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.title1SemiBold.copy(
                                color = MaterialTheme.colorScheme.onBackground
                            ),
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = product.description,
                            style = MaterialTheme.typography.body3Regular.copy(
                                color = MaterialTheme.colorScheme.secondary
                            ),
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = stringResource(R.string.add_extra_toppings),
                            style = MaterialTheme.typography.label2SemiBold.copy(
                                color = MaterialTheme.colorScheme.secondary
                            )
                        )
                        Spacer(Modifier.height(8.dp))
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(
                                items = state.toppings,
                                key = { toppingUi -> toppingUi.id }
                            ){ toppingUi ->
                                ToppingCard(
                                    toppingUi = toppingUi,
                                    onPlusClick = {
                                        onAction(ProductDetailAction.OnPlusClick(toppingUi.id))
                                    },
                                    onMinusClick = {
                                        onAction(ProductDetailAction.OnMinusClick(toppingUi.id))
                                    },
                                    onItemClick = {
                                        onAction(ProductDetailAction.OnItemClick(toppingUi.id))
                                    }
                                )
                            }
                            item {
                                Spacer(Modifier.height(56.dp))
                            }
                        }
                    }

                }
            }

            AppButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(16.dp),
                text = stringResource(R.string.add_to_cart_for,state.totalString),
                onClick = {
                    onAction(ProductDetailAction.OnAddToCartClick)
                },
                type = ButtonType.Filled
            )

        }
    }

}

@Preview
@Composable
private fun Preview() {
    LazyPizzaTheme {
        ProductDetailScreen(
            state = ProductDetailState(
                product = ProductUi(
                    id = "PizzaId",
                    name = "Margherita",
                    description = "Tomato sauce, Mozzarella, Fresh basil, Olive oil",
                    imageUrl = "",
                    price = 12.1,
                    category = ProductType.Pizza.name,
                    isEditingMode = false,
                    count = 1
                ),
                toppings = (0..10).map {
                    ToppingUi(
                        id = "Id-${it}",
                        name = "Name-${it}",
                        price = 1.2,
                        imageUrl = "",
                        count = 0,
                        isEditingMode = false
                    )
                },
                totalString = "12.99"
            ),
            onAction = {}
        )
    }
}