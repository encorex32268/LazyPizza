package com.lihan.lazypizza.menu.presentation

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.lazypizza.auth.presentation.util.FirebaseAuthManager
import com.lihan.lazypizza.core.domain.CartRepository
import com.lihan.lazypizza.core.domain.StoreProductRepository
import com.lihan.lazypizza.core.domain.UserDataStore
import com.lihan.lazypizza.menu.presentation.MenuUiEvent.*
import com.lihan.lazypizza.menu.presentation.mapper.toCartItem
import com.lihan.lazypizza.menu.presentation.mapper.toUi
import com.lihan.lazypizza.menu.presentation.model.ProductUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuViewModel(
    private val storeProductRepository: StoreProductRepository,
    private val cartRepository: CartRepository,
    private val userDataStore: UserDataStore,
    private val firebaseAuthManager: FirebaseAuthManager
) : ViewModel() {

    private val _uiEvent = Channel<MenuUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _originData = MutableStateFlow<List<ProductUi>>(emptyList())

    private val _state = MutableStateFlow(MenuState())

    private val searchText = snapshotFlow {
        _state.value.searchTextFieldState.text.toString()
    }

    val state = combine(
        _originData,
        cartRepository.getCartItems(),
        _state,
        searchText
    ) { originData, cartItems, state, query ->

        val cartItemMap = cartItems.associateBy { it.cartItem.productId }

        val updatedProductList = originData.map { productUi ->
            val cartItem = cartItemMap[productUi.id]
            if (cartItem != null) {
                productUi.copy(
                    count = cartItem.cartItem.quantity,
                    isEditingMode = true
                )
            } else {
                productUi.copy(
                    count = 0,
                    isEditingMode = false
                )
            }
        }

        val filteredList = updatedProductList.filter { product ->
            val matchesType = state.productTypes.isEmpty() || product.type in state.productTypes
            val matchesSearch = product.name.contains(query, ignoreCase = true) || product.description.contains(query, ignoreCase = true)
            matchesType && matchesSearch
        }

        state.copy(
            productUiList = filteredList,
            productTypes = state.productTypes
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = MenuState()
    )

    init {
        initData()
        checkUserStatus()
    }

    fun onAction(action: MenuAction) {
        when (action) {
            is MenuAction.OnAddToCartClick -> onAddToCartClick(action.id)
            is MenuAction.OnDeleteClick -> onDeleteClick(action.id)
            is MenuAction.OnMinusClick -> onMinusClick(action.id)
            is MenuAction.OnPlusClick -> onPlusClick(action.id)
            is MenuAction.OnProductTypeClick -> productTypeClick(action.type)
            is MenuAction.OnPizzaClick -> {
                viewModelScope.launch {
                    _uiEvent.send(OnNavigateToDetail(action.id))
                }
            }
            MenuAction.OnShowLogOut -> {
                _state.update { it.copy(
                    isShowLogOutDialog = true
                ) }
            }
            MenuAction.OnDismissLogOutClick -> {
                _state.update { it.copy(
                    isShowLogOutDialog = false
                ) }
            }
            MenuAction.OnLogOutConfirmClick -> {
                viewModelScope.launch {
                    firebaseAuthManager.signOut()
                }
            }
        }
    }

    private fun checkUserStatus() {
        viewModelScope.launch {
            val isOrdering = userDataStore.getIsOrdering().first()
            if (!isOrdering){
                userDataStore.setIsOrdering(true)
            }

            userDataStore
                .getUserPhoneNumber()
                .onEach { phoneNumber ->
                    if (phoneNumber.isNotEmpty()){
                        _state.update { it.copy(
                            phoneNumber = phoneNumber
                        ) }
                    }
                }.launchIn(this)
        }
    }

    private fun initData() {
        viewModelScope.launch {
            val products = storeProductRepository
                .getProducts()
                .first()
                .map { it.toUi() }
                .sortedBy {
                    it.type
                }

            _originData.update { products }
        }
    }

    private fun productTypeClick(type: ProductType) {
        val currentProductTypes = state.value.productTypes

        val productTypes = if (type in currentProductTypes) {
            currentProductTypes - type
        } else {
            currentProductTypes + type
        }

        _state.update {
            it.copy(
                productTypes = productTypes
            )
        }
    }

    private fun onDeleteClick(id: String) {
        viewModelScope.launch {
            val cartItems = cartRepository.getCartItems().first()
            val existingCartItem = cartItems.find { it.cartItem.productId == id }
            if (existingCartItem != null) {
                existingCartItem.cartItem.cartItemId?.let { cartItemId ->
                    cartRepository.deleteCartItem(cartItemId)
                }
            }
        }
    }

    private fun onMinusClick(id: String) {
        viewModelScope.launch {
            val cartItems = cartRepository.getCartItems().first()
            val existingCartItem = cartItems.find { it.cartItem.productId == id }
            if (existingCartItem != null) {
                val newQty = existingCartItem.cartItem.quantity - 1
                existingCartItem.cartItem.cartItemId?.let { cartItemId ->
                    if (newQty <= 0) {
                        cartRepository.deleteCartItem(cartItemId)
                    } else {
                        cartRepository.updateCartItemQuantity(
                            cartItemId,
                            newQty
                        )
                    }
                }
            }
        }
    }

    private fun onPlusClick(id: String) {
        viewModelScope.launch {
            val cartItems = cartRepository.getCartItems().first()
            val existingCartItem = cartItems.find { it.cartItem.productId == id }
            if (existingCartItem != null) {
                existingCartItem.cartItem.cartItemId?.let { cartItemId ->
                    cartRepository.updateCartItemQuantity(
                        cartItemId,
                        existingCartItem.cartItem.quantity + 1
                    )
                }
            } else {
                val productUi = _originData.value.find { it.id == id } ?: return@launch
                val orderId = userDataStore.getOrderId().first().toInt()
                cartRepository.insertCartItemWithToppings(
                    cartItem = productUi.toCartItem(orderId).copy(quantity = 1),
                    cartTopping = emptyList()
                )
            }
        }
    }

    private fun onAddToCartClick(id: String) {
        viewModelScope.launch {
            val productUi = _originData.value.find { it.id == id } ?: return@launch
            val cartItems = cartRepository.getCartItems().first()
            val existingCartItem = cartItems.find { it.cartItem.productId == id }

            if (existingCartItem != null) {
                existingCartItem.cartItem.cartItemId?.let { cartItemId ->
                    cartRepository.deleteCartItem(cartItemId)
                }
            } else {
                val orderId = userDataStore.getOrderId().first().toInt()
                cartRepository.insertCartItemWithToppings(
                    cartItem = productUi.toCartItem(orderId),
                    cartTopping = emptyList()
                )
            }
        }
    }
}