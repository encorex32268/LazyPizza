package com.lihan.lazypizza.cart.presentation.order_checkout

import com.lihan.lazypizza.cart.presentation.order_checkout.util.PickUpTimeType
import java.time.LocalDate

sealed interface OrderCheckoutAction {
    data object OnBackClick: OrderCheckoutAction
    data object OnExpandClick: OrderCheckoutAction
    data object OnDismissDatePicker: OrderCheckoutAction
    data object OnDismissTimePicker: OrderCheckoutAction
    data object OnDatePickerCancelClick: OrderCheckoutAction
    data object OnTimePickerCancelClick: OrderCheckoutAction
    data class OnDateSelected(val dateLocalDate: LocalDate): OrderCheckoutAction
    data class OnTimeSelected(val time: String): OrderCheckoutAction
    data class OnPickupTimeTypeSelect(val type: PickUpTimeType): OrderCheckoutAction
}