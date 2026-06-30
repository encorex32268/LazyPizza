package com.lihan.lazypizza.menu.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lihan.lazypizza.R
import com.lihan.lazypizza.core.domain.formatToTwoDecimals
import com.lihan.lazypizza.core.presentation.components.AppDialog
import com.lihan.lazypizza.core.presentation.components.ProductCard
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.label2SemiBold
import com.lihan.lazypizza.core.presentation.util.ObserveAsEvents
import com.lihan.lazypizza.menu.presentation.ProductType.Companion.toTypeName
import com.lihan.lazypizza.menu.presentation.components.MenuTopbar
import com.lihan.lazypizza.menu.presentation.components.ProductSearchbar
import com.lihan.lazypizza.menu.presentation.components.ProductTypeChip
import org.koin.androidx.compose.koinViewModel

@Composable
fun MenuRoot(
    onNavigateToDetail: (String) -> Unit,
    viewModel: MenuViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.uiEvent) { uiEvent ->
        when(uiEvent){
            is MenuUiEvent.OnNavigateToDetail -> onNavigateToDetail(uiEvent.id)
        }
    }

    MenuScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun MenuScreen(
    state: MenuState,
    onAction: (MenuAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current

    val filterTypes = remember { ProductType.getFilterTypes() }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    keyboard?.hide()
                    focusManager.clearFocus()
                })
            },
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MenuTopbar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 10.dp),
                phoneNumber = state.phoneNumber,
                onLogOut = {
                    onAction(MenuAction.OnShowLogOut)
                }
            )
        }
    ) { it -> it
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = it.calculateTopPadding())
        ) {
            Image(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .height(150.dp),
                painter = painterResource(R.drawable.banner),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Spacer(Modifier.height(16.dp))
            ProductSearchbar(
                textFieldState = state.searchTextFieldState,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filterTypes.forEach { productType ->
                    ProductTypeChip(
                        isSelected = productType in state.productTypes,
                        type = productType,
                        onClick = { productType ->
                            onAction(MenuAction.OnProductTypeClick(productType))
                        }
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),

            ) {
                state.items.forEach { (productType, productUis) ->
                    item {
                        Text(
                            text = productType.toTypeName(),
                            style = MaterialTheme.typography.label2SemiBold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    items(
                        items = productUis,
                        key = { productUi -> productUi.id }
                    ){ productUi ->
                        ProductCard(
                            image = productUi.imageUrl,
                            name = productUi.name,
                            description = productUi.description,
                            isEditingMode = productUi.isEditingMode,
                            type = productUi.type,
                            price = productUi.price.formatToTwoDecimals(),
                            priceCalculate = productUi.priceTotalDetail,
                            totalPrice = productUi.priceTotal,
                            quantity = productUi.count,
                            onPlusClick = {
                                onAction(MenuAction.OnPlusClick(productUi.id))
                            },
                            onMinusClick = {
                                onAction(MenuAction.OnMinusClick(productUi.id))
                            },
                            onItemClick = {
                                if (productUi.type != ProductType.Pizza){
                                    return@ProductCard
                                }
                                onAction(MenuAction.OnPizzaClick(productUi.id))
                            },
                            onDeleteClick = {
                                onAction(MenuAction.OnDeleteClick(productUi.id))
                            },
                            onAddToCartClick = {
                                onAction(MenuAction.OnAddToCartClick(productUi.id))
                            },
                        )
                    }
                }
                item{
                    Spacer(Modifier.height(60.dp))
                }
            }

        }
        if (state.isShowLogOutDialog){
            AppDialog(
                title = stringResource(R.string.log_out_dialog_title),
                onDismiss = {
                    onAction(MenuAction.OnDismissLogOutClick)
                },
                onConfirmClick = {
                    onAction(MenuAction.OnLogOutConfirmClick)
                },
                dismissButtonText = stringResource(R.string.cancel),
                confirmButtonText = stringResource(R.string.logout)
            )
        }

    }


}

@Preview
@Composable
private fun Preview() {
    LazyPizzaTheme {
        MenuScreen(
            state = MenuState(
                isShowLogOutDialog = false
            ),
            onAction = {}
        )
    }
}