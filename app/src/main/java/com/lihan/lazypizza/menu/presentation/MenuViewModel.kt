package com.lihan.lazypizza.menu.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.lazypizza.menu.presentation.model.ProductUi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private var originData: List<ProductUi> = emptyList()

    private val _state = MutableStateFlow(MenuState())
    val state = _state
        .onStart {
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

    private fun initData() {
        viewModelScope.launch {
            delay(2000L)
            originData = MenuState.fakeProductUiList
            _state.update { it.copy(
                productUiList = originData
            )}
        }


    }

    fun onAction(action: MenuAction) {
        when (action) {
            is MenuAction.OnAddToCartClick -> onAddToCartClick(action.id)
            is MenuAction.OnDeleteClick -> onDeleteClick(action.id)
            is MenuAction.OnMinusClick -> onMinusClick(action.id)
            is MenuAction.OnPlusClick -> onPlusClick(action.id)
            is MenuAction.OnProductTypeClick -> productTypeClick(action.type)
        }
    }

    private fun productTypeClick(type: ProductType) {
        _state.update { it.copy(
            productUiList = originData.filter { productUi ->
                productUi.type == type
            }
        ) }
    }

    private fun onDeleteClick(id: String){
        val currentState = state.value
        val currentProduct = currentState
            .productUiList
            .map { productUi ->
                if (productUi.id == id){
                    productUi.copy(
                        count = 0,
                        isEditingMode = false
                    )
                }else {
                    productUi
                }
            }
        _state.update { it.copy(
            productUiList = currentProduct
        )}
    }

    private fun onMinusClick(id: String){
        val currentState = state.value
        val currentProduct = currentState
            .productUiList
            .map { productUi ->
                if (productUi.id == id){
                    val newCount = productUi.count - 1
                    if (newCount <= 0){
                        productUi.copy(
                            count = 0,
                            isEditingMode = false
                        )
                    }else{
                        productUi.copy(
                            count = productUi.count - 1
                        )
                    }
                }else {
                    productUi
                }
            }
        _state.update { it.copy(
            productUiList = currentProduct
        )}
    }

    private fun onPlusClick(id: String){
        val currentState = state.value
        val currentProduct = currentState
            .productUiList
            .map { productUi ->
               if (productUi.id == id){
                   productUi.copy(
                       count = productUi.count + 1
                   )
               }else {
                   productUi
               }
            }
        _state.update { it.copy(
            productUiList = currentProduct
        )}
    }

    private fun onAddToCartClick(id: String){
        val currentState = state.value
        val currentProduct = currentState
            .productUiList
            .map { productUi ->
                if (productUi.id == id){
                    productUi.copy(
                        isEditingMode = !productUi.isEditingMode
                    )
                }else {
                    productUi
                }
            }
        _state.update { it.copy(
            productUiList = currentProduct
        )}
    }

}