package com.lihan.lazypizza.cart.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.lazypizza.cart.presentation.mapper.toDomainOrderHistory
import com.lihan.lazypizza.cart.presentation.mapper.toUi
import com.lihan.lazypizza.core.domain.CartRepository
import com.lihan.lazypizza.core.domain.OrderRepository
import com.lihan.lazypizza.core.domain.StoreProductRepository
import com.lihan.lazypizza.core.domain.UserDataStore
import com.lihan.lazypizza.core.domain.util.onFailure
import com.lihan.lazypizza.core.domain.util.onSuccess
import com.lihan.lazypizza.core.presentation.util.toUiText
import com.lihan.lazypizza.menu.presentation.mapper.toCartItem
import com.lihan.lazypizza.menu.presentation.mapper.toUi
import com.lihan.lazypizza.menu.presentation.model.ProductUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartSharedViewModel(
    private val cartRepository: CartRepository,
    private val userDataStore: UserDataStore,
    private val storeProductRepository: StoreProductRepository,
    private val orderRepository: OrderRepository
): ViewModel() {

    private var hasLoadedInitialData = false

    private var _recommendItems = MutableStateFlow(emptyList<ProductUi>())

    private var _state = MutableStateFlow(CartSharedState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData){
                observeCartItems()
                hasLoadedInitialData = true
            }
        }.onEach { state ->
            _state.update { it.copy(
                recommendItems = state.recommendItems.sortedBy { productUi ->
                    productUi.id
                }) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CartSharedState()
        )

    init {
        getRecommendItems()
    }

    fun onAction(action: CartSharedAction){
        when(action){
            is CartSharedAction.OnDeleteClick -> deleteCartItem(action.cartItemId, action.productId)
            is CartSharedAction.OnMinusClick -> updateCartItemQuantity(action.cartItemId, -1)
            is CartSharedAction.OnPlusClick -> updateCartItemQuantity(action.cartItemId, 1)
            is CartSharedAction.OnAddItemClick -> addToCartItem(action.productId)
            CartSharedAction.OnPlaceOrderClick -> placeOrder()
            CartSharedAction.OnDismissErrorDialog -> dismissErrorDialog()
        }
    }

    private fun placeOrder() {
        viewModelScope.launch {
            val currentState = state.value
            val cartItemWithToppings = currentState.items
            orderRepository
                .create(
                    orderHistory = cartItemWithToppings.toDomainOrderHistory()
                )
                .first()
                .onSuccess {
                    //1.let orderId + 1
                    //2.clean cart
                    val currentOrderId = userDataStore.getOrderId().first()
                    userDataStore.setOrderId(currentOrderId + 1)

                    val cartId = cartItemWithToppings.first().cartItem.id
                    cartRepository.cleanCart(cartId = cartId.toString())

                }.onFailure { error ->
                    _state.update { it.copy(
                        error = error.toUiText()
                    ) }
                }

        }
    }


    private fun observeCartItems(){
        cartRepository
            .getCartItems()
            .map { domainList ->
                domainList.mapNotNull { it.toUi() }
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

    private fun dismissErrorDialog(){
        _state.update { it.copy(
            error = null
        ) }
    }
}