package com.lihan.lazypizza.cart.presentation.order_checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.play.integrity.internal.ac
import com.lihan.lazypizza.cart.presentation.order_checkout.util.PickUpTimeType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class OrderCheckoutViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(OrderCheckoutState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = OrderCheckoutState()
        )

    fun onAction(action: OrderCheckoutAction) {
        when (action) {
            is OrderCheckoutAction.OnDateSelected -> {
                _state.update { it.copy(
                    dateMills = action.dateMillis?:0,
                    isShowDatePicker = false,
                    isShowTimePicker = true
                ) }
            }
            OrderCheckoutAction.OnDatePickerCancelClick,
            OrderCheckoutAction.OnDismissDatePicker -> {
                _state.update { it.copy(
                    isShowDatePicker = false,
                    pickUpTimeType = PickUpTimeType.EarliestAvailableTime,
                ) }
            }
            OrderCheckoutAction.OnDismissTimePicker ->{
                _state.update { it.copy(
                    isShowTimePicker = false,
                    pickUpTimeType = PickUpTimeType.EarliestAvailableTime,
                )}
            }
            OrderCheckoutAction.OnExpandClick -> {
                _state.update { it.copy(
                    isExpandDetail = !it.isExpandDetail
                ) }
            }
            is OrderCheckoutAction.OnPickupTimeTypeSelect -> {
                _state.update { it.copy(
                    pickUpTimeType = action.type,
                    isShowDatePicker = action.type == PickUpTimeType.ScheduleTime
                ) }
            }
            is OrderCheckoutAction.OnTimeSelected -> {

            }
            OrderCheckoutAction.OnTimePickerCancelClick ->{
                _state.update { it.copy(
                    isShowTimePicker = false,
                    isShowDatePicker = true
                ) }
            }
        }
    }

}