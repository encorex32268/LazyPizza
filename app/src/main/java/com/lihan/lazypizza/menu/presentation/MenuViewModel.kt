package com.lihan.lazypizza.menu.presentation

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.lazypizza.core.domain.CartRepository
import com.lihan.lazypizza.core.domain.StoreProductRepository
import com.lihan.lazypizza.core.domain.UserDataStore
import com.lihan.lazypizza.menu.presentation.mapper.toCartItem
import com.lihan.lazypizza.menu.presentation.mapper.toDomain
import com.lihan.lazypizza.menu.presentation.mapper.toUi
import com.lihan.lazypizza.menu.presentation.model.ProductUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuViewModel(
    private val storeProductRepository: StoreProductRepository,
    private val cartRepository: CartRepository,
    private val userDataStore: UserDataStore
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _uiEvent = Channel<MenuUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _originData = MutableStateFlow<List<ProductUi>>(emptyList())

    private val searchText = snapshotFlow {
        _state.value.searchTextFieldState.text.toString()
    }

    private val _state = MutableStateFlow(MenuState())
    val state = combine(
        _originData,
        _state.map { it.productTypes }.distinctUntilChanged(),
        searchText
    ) { originData, productTypes, query ->

        val orderId = userDataStore.getOrderId().first()
        val orderProducts = originData
            .filter { productUi -> productUi.isEditingMode }
            .map { productUi ->
                val newProductUi =  productUi.toCartItem(orderId)
                newProductUi
            }

        orderProducts.forEach { cartItem ->
            cartRepository.insertCartItemWithToppings(
                cartItem = cartItem,
                cartTopping = emptyList()
            )
        }


        _state.value.copy(
            productUiList = originData.filter { product ->
                val matchesType = productTypes.isEmpty() || product.type in productTypes
                val matchesSearch = product.name.contains(query, ignoreCase = true) || product.description.contains(query,ignoreCase = true)
                matchesType && matchesSearch
            },
            productTypes = productTypes
        )


    }.onStart {
            if (!hasLoadedInitialData) {
                initData()
                checkUserStatus()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MenuState()
        )


    fun onAction(action: MenuAction) {
        when (action) {
            is MenuAction.OnAddToCartClick -> onAddToCartClick(action.id)
            is MenuAction.OnDeleteClick -> onDeleteClick(action.id)
            is MenuAction.OnMinusClick -> onMinusClick(action.id)
            is MenuAction.OnPlusClick -> onPlusClick(action.id)
            is MenuAction.OnProductTypeClick -> productTypeClick(action.type)
            is MenuAction.OnPizzaClick -> {
                viewModelScope.launch {
                    _uiEvent.send(MenuUiEvent.OnNavigateToDetail(action.id))
                }
            }
        }
    }

    //Check user isOrdering or not
    private fun checkUserStatus() {
        viewModelScope.launch {
            val isOrdering = userDataStore.getIsOrdering().first()
            if (!isOrdering){
                userDataStore.setIsOrdering(true)
            }
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
        _originData.update { currentOriginData ->
            currentOriginData.map { productUi ->
                if (productUi.id == id) {
                    productUi.copy(
                        count = 0,
                        isEditingMode = false
                    )
                } else {
                    productUi
                }
            }
        }
    }

    private fun onMinusClick(id: String) {
        _originData.update { currentOriginData ->
            currentOriginData.map { productUi ->
                if (productUi.id == id) {
                    val newCount = productUi.count - 1
                    if (newCount <= 0) {
                        productUi.copy(
                            count = 0,
                            isEditingMode = false
                        )
                    } else {
                        productUi.copy(
                            count = productUi.count - 1
                        )
                    }
                } else {
                    productUi
                }
            }
        }
    }

    private fun onPlusClick(id: String) {
        _originData.update { currentOriginData ->
            currentOriginData.map { productUi ->
                if (productUi.id == id) {
                    productUi.copy(
                        count = productUi.count + 1
                    )
                } else {
                    productUi
                }
            }
        }
    }

    private fun onAddToCartClick(id: String) {
        _originData.update { currentOriginData ->
            currentOriginData.map { productUi ->
                if (productUi.id == id) {
                    productUi.copy(
                        isEditingMode = !productUi.isEditingMode,
                        count = if (productUi.count == 0) 1 else productUi.count
                    )
                } else {
                    productUi
                }
            }
        }
    }
}