@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.lazypizza.cart.presentation.order_checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lihan.lazypizza.R
import com.lihan.lazypizza.cart.presentation.CartPreviewData
import com.lihan.lazypizza.cart.presentation.CartSharedAction
import com.lihan.lazypizza.cart.presentation.CartSharedState
import com.lihan.lazypizza.cart.presentation.CartSharedViewModel
import com.lihan.lazypizza.cart.presentation.components.CartItemList
import com.lihan.lazypizza.cart.presentation.components.RecommendListRow
import com.lihan.lazypizza.cart.presentation.order_checkout.components.CommentTextField
import com.lihan.lazypizza.cart.presentation.order_checkout.components.PickUpTimeSection
import com.lihan.lazypizza.cart.presentation.order_checkout.util.PickUpTimeType.Companion.toStringResource
import com.lihan.lazypizza.core.presentation.ArrowLeft
import com.lihan.lazypizza.core.presentation.ChevronDown
import com.lihan.lazypizza.core.presentation.ChevronUp
import com.lihan.lazypizza.core.presentation.components.AppIconBackgroundButton
import com.lihan.lazypizza.core.presentation.design_system.AppButton
import com.lihan.lazypizza.core.presentation.design_system.AppDatePickerRoot
import com.lihan.lazypizza.core.presentation.design_system.AppTimePickerRoot
import com.lihan.lazypizza.core.presentation.design_system.ButtonType
import com.lihan.lazypizza.core.presentation.design_system.rememberAppDatePickerState
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.body1Medium
import com.lihan.lazypizza.core.presentation.ui.theme.label2Medium
import com.lihan.lazypizza.core.presentation.ui.theme.label2SemiBold
import com.lihan.lazypizza.core.presentation.ui.theme.outline50
import com.lihan.lazypizza.core.presentation.ui.theme.surfaceHigher
import com.lihan.lazypizza.core.presentation.ui.theme.textSecondary8
import com.lihan.lazypizza.menu.presentation.MenuState
import java.time.LocalDate
import java.time.ZoneId
import kotlin.random.Random

@Composable
fun OrderCheckoutRoot(
    onBack: () -> Unit,
    sharedViewModel: CartSharedViewModel,
    viewModel: OrderCheckoutViewModel = viewModel()
) {
    val sharedState by sharedViewModel.state.collectAsStateWithLifecycle()
    val state by viewModel.state.collectAsStateWithLifecycle()

    //if cart items is empty , back to cart.
    LaunchedEffect(sharedState.items){
        if (sharedState.items.isEmpty()){
            onBack()
        }
    }

    OrderCheckoutScreen(
        sharedState = sharedState,
        state = state,
        onShareAction = sharedViewModel::onAction,
        onAction = { action ->
            when(action){
                OrderCheckoutAction.OnBackClick -> onBack()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun OrderCheckoutScreen(
    sharedState: CartSharedState,
    state: OrderCheckoutState,
    onShareAction: (CartSharedAction) -> Unit,
    onAction: (OrderCheckoutAction) -> Unit,
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit){
                    detectTapGestures(
                        onTap = {
                            keyboard?.hide()
                            focusManager.clearFocus()
                        }
                    )
                }
                .padding(paddingValues)
        ){
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CenterAlignedTopAppBar(
                        windowInsets = WindowInsets(0.dp),
                        title = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(R.string.order_checkout),
                                style = MaterialTheme.typography.body1Medium.copy(
                                    color = MaterialTheme.colorScheme.onBackground,
                                    textAlign = TextAlign.Center
                                ),
                            )
                        },
                        navigationIcon = {
                            AppIconBackgroundButton(
                                modifier = Modifier.padding(start = 16.dp),
                                onClick = {
                                    onAction(OrderCheckoutAction.OnBackClick)
                                },
                                imageVector = ArrowLeft,
                                backgroundColor = MaterialTheme.colorScheme.textSecondary8,
                                iconTintColor = MaterialTheme.colorScheme.secondary
                            )
                        }
                    )
                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        columns = GridCells.Fixed(1),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            PickUpTimeSection(
                                pickUpTimeType = state.pickUpTimeType,
                                onSelected = { type ->
                                    onAction(OrderCheckoutAction.OnPickupTimeTypeSelect(type))
                                }
                            )
                        }
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = state.pickUpTimeType.toStringResource(),
                                    style = MaterialTheme.typography.label2Medium,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                                Text(
                                    text = state.pickUpTime?.asString()?:"",
                                    style = MaterialTheme.typography.label2SemiBold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                        item {
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = stringResource(R.string.order_details),
                                    style = MaterialTheme.typography.label2SemiBold,
                                    color = MaterialTheme.colorScheme.secondary
                                )

                                AppIconBackgroundButton(
                                    onClick = {
                                        onAction(OrderCheckoutAction.OnExpandClick)
                                    },
                                    imageVector = if (state.isExpandDetail) ChevronDown else ChevronUp,
                                    iconTintColor = MaterialTheme.colorScheme.secondary,
                                    backgroundColor = Color.Transparent,
                                    shape = RoundedCornerShape(8.dp),
                                    borderColor = MaterialTheme.colorScheme.outline50,
                                    size = 22.dp
                                )
                            }
                        }
                        if (state.isExpandDetail){
                            CartItemList(
                                onDeleteClick = { cartItemId, productId ->
                                    onShareAction(CartSharedAction.OnDeleteClick(cartItemId, productId))
                                },
                                onPlusClick = { cartItemId ->
                                    onShareAction(CartSharedAction.OnPlusClick(cartItemId))
                                },
                                onMinusClick = { cartItemId ->
                                    onShareAction(CartSharedAction.OnMinusClick(cartItemId))
                                },
                                items = sharedState.items
                            )
                        }
                        if (sharedState.recommendItems.isNotEmpty()) {
                            item {
                                Spacer(Modifier.height(20.dp))
                                Text(
                                    text = stringResource(R.string.recommended_to_add_your_order),
                                    style = MaterialTheme.typography.label2SemiBold.copy(
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                )
                                Spacer(Modifier.height(8.dp))
                            }
                            RecommendListRow(
                                recommendItems = sharedState.recommendItems,
                                onAddClick = { productId ->
                                    onShareAction(CartSharedAction.OnAddItemClick(productId))
                                }
                            )
                        }
                        item {
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                        item {
                            Text(
                                text = stringResource(R.string.comments),
                                style = MaterialTheme.typography.label2Medium,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                        item {
                            CommentTextField(
                                modifier = Modifier.heightIn(min = 92.dp),
                                textFieldState = TextFieldState(),
                                onDone = {
                                    keyboard?.hide()
                                    focusManager.clearFocus()
                                }
                            )
                        }
                        item {
                            Spacer(Modifier.height(256.dp))
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceHigher
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ){
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.order_total),
                        style = MaterialTheme.typography.label2Medium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "$${sharedState.cartItemTotalPrice}",
                        style = MaterialTheme.typography.label2SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                AppButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.place_order),
                    type = ButtonType.Filled,
                    onClick = {
                        onShareAction(CartSharedAction.OnPlaceOrderClick)
                    }
                )
            }

            if (state.isShowDatePicker){
                AppDatePickerRoot(
                    state = rememberAppDatePickerState(
                        initialSelectedDate = state.dateLocalDate
                    ),
                    onDismissRequest = {
                        onAction(OrderCheckoutAction.OnDismissDatePicker)
                    },
                    onDateConfirm = {
                        onAction(OrderCheckoutAction.OnDateSelected(it))
                    },
                    onCancel = {
                        onAction(OrderCheckoutAction.OnDatePickerCancelClick)
                    }
                )
            }
            if (state.isShowTimePicker){
                AppTimePickerRoot(
                    onDismissRequest = {
                        onAction(OrderCheckoutAction.OnDismissTimePicker)
                    },
                    onTimeConfirm = {
                        onAction(OrderCheckoutAction.OnTimeSelected(it))
                    },
                    onCancel = {
                        onAction(OrderCheckoutAction.OnTimePickerCancelClick)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    LazyPizzaTheme {
        OrderCheckoutScreen(
            state = OrderCheckoutState(
                isExpandDetail = false,
                isShowDatePicker = false,

            ),
            onAction = {},
            sharedState = CartSharedState(
                recommendItems = MenuState.fakeProductUiList.shuffled(random = Random(4)),
                items = CartPreviewData.items,
            ),
            onShareAction = {}
        )
    }
}