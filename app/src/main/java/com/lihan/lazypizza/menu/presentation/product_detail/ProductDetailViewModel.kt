package com.lihan.lazypizza.menu.presentation.product_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.lihan.lazypizza.core.domain.CartRepository
import com.lihan.lazypizza.core.presentation.Route
import com.lihan.lazypizza.core.domain.StoreProductRepository
import com.lihan.lazypizza.core.domain.UserDataStore
import com.lihan.lazypizza.core.domain.formatToTwoDecimals
import com.lihan.lazypizza.menu.presentation.mapper.toCartItem
import com.lihan.lazypizza.menu.presentation.mapper.toCartItemTopping
import com.lihan.lazypizza.menu.presentation.mapper.toUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val storeProductRepository: StoreProductRepository,
    private val cartRepository: CartRepository,
    private val savedStateHandle: SavedStateHandle,
    private val userDataStore: UserDataStore,
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val route = savedStateHandle.toRoute<Route.ProductDetail>()

    private val _uiEvent = Channel<ProductDetailUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow(ProductDetailState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                initData()
                observeTotalPrice()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ProductDetailState()
        )

    private fun initData() {
        viewModelScope.launch {
            val pizza = storeProductRepository.getPizzaById(route.id)
            if (pizza == null) {
                _uiEvent.send(ProductDetailUiEvent.OnBack)
                return@launch
            }

            val toppings = storeProductRepository
                .getToppings()
                .first()
                .map { it.toUi() }

            _state.update {
                it.copy(
                    product = pizza.toUi(),
                    toppings = toppings
                )
            }
        }
    }

    fun onAction(action: ProductDetailAction) {
        when (action) {
            ProductDetailAction.OnBack -> {
                viewModelScope.launch {
                    _uiEvent.send(ProductDetailUiEvent.OnBack)
                }
            }

            is ProductDetailAction.OnMinusClick -> onMinusClick(action.id)
            is ProductDetailAction.OnPlusClick -> onPlusClick(action.id)
            is ProductDetailAction.OnItemClick -> onItemClick(action.id)
            ProductDetailAction.OnAddToCartClick -> addToCart()
        }
    }

    private fun addToCart(){
        viewModelScope.launch {
            val currentState = state.value
            val product = currentState.product
            if (product == null){
                _uiEvent.send(ProductDetailUiEvent.OnBack)
                return@launch
            }
            val toppings = currentState.toppings.filter { toppingUi -> toppingUi.isEditingMode }
            val orderId = userDataStore.getOrderId().first()

            cartRepository.insertCartItemWithToppings(
                cartItem = product.toCartItem(orderId),
                cartTopping = toppings.map { it.toCartItemTopping() }
            )
            _uiEvent.send(ProductDetailUiEvent.OnBack)

        }
    }

    private fun onMinusClick(id: String) {
        val currentToppings = state.value.toppings

        val newToppings = currentToppings.map { toppingUi ->
            if (toppingUi.id == id) {
                val newCount = toppingUi.count - 1
                if (newCount <= 0) {
                    toppingUi.copy(
                        count = 0,
                        isEditingMode = false
                    )
                } else {
                    toppingUi.copy(
                        count = toppingUi.count - 1
                    )
                }
            } else {
                toppingUi
            }
        }
        _state.update {
            it.copy(
                toppings = newToppings
            )
        }
    }

    private fun onPlusClick(id: String) {
        val currentToppings = state.value.toppings

        val newToppings = currentToppings.map { toppingUi ->
            if (toppingUi.id == id) {
                toppingUi.copy(
                    count = toppingUi.count + 1
                )
            } else {
                toppingUi
            }
        }
        _state.update {
            it.copy(
                toppings = newToppings
            )
        }
    }

    private fun onItemClick(id: String) {
        val currentToppings = state.value.toppings

        val newToppings = currentToppings.map { toppingUi ->
            if (toppingUi.id == id) {
                toppingUi.copy(
                    isEditingMode = true,
                    count = 1
                )
            } else {
                toppingUi
            }
        }
        _state.update {
            it.copy(
                toppings = newToppings
            )
        }
    }

    private fun observeTotalPrice() {
        combine(
            _state.map { it.product },
            _state.map { it.toppings },
        ) { product, toppings ->

            val productPrice = product?.price ?: 0.0
            val toppingsPrice = toppings
                .filter { toppingUi -> toppingUi.isEditingMode }
                .sumOf { toppingUi ->
                    toppingUi.price * toppingUi.count
                }
            (productPrice + toppingsPrice).formatToTwoDecimals()

        }.onEach { totalString ->

            _state.update {
                it.copy(
                    totalString = totalString
                )
            }
        }.launchIn(viewModelScope)
    }

}