package com.lihan.lazypizza.cart.presentation.order_checkout

import com.lihan.lazypizza.cart.presentation.order_checkout.util.PickUpTimeType
import com.lihan.lazypizza.core.presentation.ui.util.UiText
import java.time.LocalDate

data class OrderCheckoutState(
    val isExpandDetail: Boolean = false,
    val isShowDatePicker: Boolean = false,
    val isShowTimePicker: Boolean = false,
    val pickUpTimeType: PickUpTimeType = PickUpTimeType.EarliestAvailableTime,
    val dateLocalDate: LocalDate = LocalDate.now(),
    val time: String = "",
    val pickUpTime: UiText?=null,
    val placedPickUpTime: UiText?=null,
    val isSentOrder: Boolean = false,
    val orderNumber: String = ""
)