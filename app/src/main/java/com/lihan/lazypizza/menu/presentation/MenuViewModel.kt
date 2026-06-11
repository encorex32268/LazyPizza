package com.lihan.lazypizza.menu.presentation

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.lazypizza.core.domain.StoreProductRepository
import com.lihan.lazypizza.menu.presentation.mapper.toUi
import com.lihan.lazypizza.menu.presentation.model.ProductUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuViewModel(
    private val storeProductRepository: StoreProductRepository
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _originData = MutableStateFlow<List<ProductUi>>(emptyList())

    private val _state = MutableStateFlow(MenuState())

    private val searchText = snapshotFlow {
        _state.value.searchTextFieldState.text.toString()
    }

    val state = combine(
        _originData,
        _state.map { it.productTypes }.distinctUntilChanged(),
        searchText
    ) { originData, productTypes, query ->
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