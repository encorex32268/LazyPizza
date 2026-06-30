package com.lihan.lazypizza.cart.presentation.order_checkout

import com.lihan.lazypizza.cart.presentation.order_checkout.util.PickUpTimeType

data class OrderCheckoutState(
    val isExpandDetail: Boolean = false,
    val isShowDatePicker: Boolean = false,
    val isShowTimePicker: Boolean = false,
    val pickUpTimeType: PickUpTimeType = PickUpTimeType.EarliestAvailableTime,
    val dateMills: Long = 0,
    val time: String = "",
)