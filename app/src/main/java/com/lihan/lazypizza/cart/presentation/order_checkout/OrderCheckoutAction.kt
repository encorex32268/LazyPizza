package com.lihan.lazypizza.cart.presentation.order_checkout

import com.lihan.lazypizza.cart.presentation.order_checkout.util.PickUpTimeType

sealed interface OrderCheckoutAction {
    data object OnExpandClick: OrderCheckoutAction
    data object OnDismissDatePicker: OrderCheckoutAction
    data object OnDismissTimePicker: OrderCheckoutAction
    data object OnDatePickerCancelClick: OrderCheckoutAction
    data object OnTimePickerCancelClick: OrderCheckoutAction
    data class OnDateSelected(val dateMillis: Long?): OrderCheckoutAction
    data class OnTimeSelected(val time: String): OrderCheckoutAction
    data class OnPickupTimeTypeSelect(val type: PickUpTimeType): OrderCheckoutAction
}