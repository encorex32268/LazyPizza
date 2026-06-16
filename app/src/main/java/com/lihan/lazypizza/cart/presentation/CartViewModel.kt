package com.lihan.lazypizza.cart.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.lazypizza.cart.presentation.mapper.toUi
import com.lihan.lazypizza.core.domain.CartRepository
import com.lihan.lazypizza.core.domain.StoreProductRepository
import com.lihan.lazypizza.core.domain.UserDataStore
import com.lihan.lazypizza.menu.presentation.mapper.toCartItem
import com.lihan.lazypizza.menu.presentation.mapper.toUi
import com.lihan.lazypizza.menu.presentation.model.ProductUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.sortedBy

class CartViewModel(
    private val cartRepository: CartRepository,
    private val storeProductRepository: StoreProductRepository,
    private val userDataStore: UserDataStore
) : ViewModel() {

    private var hasLoadedInitialData = false

    private var _recommendItems = MutableStateFlow(emptyList<ProductUi>())

    private val _state = MutableStateFlow(CartState())
    val state = _state.onStart {
        getRecommendItems()
        if (!hasLoadedInitialData) {
            observeCart()
            hasLoadedInitialData = true
        }
    }.onEach { state ->
        _state.update { it.copy(
            recommendItems = state.recommendItems.sortedBy { productUi ->
                productUi.id
            }
        ) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = CartState()
    )


    fun onAction(action: CartAction) {
        when (action) {
            is CartAction.OnDeleteClick -> deleteCartItem(action.cartItemId, action.productId)
            is CartAction.OnMinusClick -> updateCartItemQuantity(action.cartItemId, -1)
            is CartAction.OnPlusClick -> updateCartItemQuantity(action.cartItemId, 1)
            is CartAction.OnAddItemClick -> addToCartItem(action.productId)
            is CartAction.OnBackToMenu -> Unit
        }
    }

    private fun deleteCartItem(cartItemId: Long, productId: String) {
        viewModelScope.launch {
            //restore recommend items
            val product = _recommendItems.value.find { it.id == productId }
            if (product != null) {
                _state.update {
                    it.copy(
                        recommendItems = it.recommendItems + product
                    )
                }
            }

            cartRepository.deleteCartItem(cartItemId)
        }
    }


    private fun updateCartItemQuantity(cartItemId: Long, count: Int) {
        viewModelScope.launch {
            val currentCartItemWithToppings =
                state.value.items.find { it.cartItem.cartItemId == cartItemId }
            if (currentCartItemWithToppings == null) return@launch
            val newQuantity = currentCartItemWithToppings.cartItem.quantity + count
            cartRepository.updateCartItemQuantity(
                cartItemId = cartItemId,
                quantity = newQuantity
            )
        }
    }

    private fun addToCartItem(productId: String) {
        viewModelScope.launch {

            val orderId = userDataStore.getOrderId().first()
            val currentProduct = state.value.recommendItems.find { it.id == productId }
            if (currentProduct == null)
                return@launch

            cartRepository.insertCartItemWithToppings(
                cartItem = currentProduct.toCartItem(orderId),
                cartTopping = emptyList()
            )
            //add recommend item to cart , so remove item from recommend
            val product = _recommendItems.value.find { it.id == productId }
            if (product != null) {
                _state.update {
                    it.copy(
                        recommendItems = _recommendItems.value - product
                    )
                }
            }
        }
    }

    private fun observeCart() {
        cartRepository
            .getCartItems()
            .map { cartItemWithToppings ->
                cartItemWithToppings.mapNotNull { cartItemWithTopping ->
                    cartItemWithTopping.toUi()
                }
            }
            .onEach { cartItemWithToppingsUis ->

                _state.update {
                    it.copy(
                        items = cartItemWithToppingsUis
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getRecommendItems() {
        viewModelScope.launch {
            val items = storeProductRepository
                .getRecommendProducts()
                .first()
                .shuffled()
                .take(4)
                .map { it.toUi() }

            _recommendItems.update { items }

            _state.update {
                it.copy(
                    recommendItems = _recommendItems.value
                )
            }
        }
    }
}