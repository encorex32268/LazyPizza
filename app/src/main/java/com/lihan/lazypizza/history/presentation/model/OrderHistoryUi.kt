package com.lihan.lazypizza.history.presentation.model

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale


const val DatePattern = "MMMM d, HH:mm"

data class OrderHistoryUi(
    val createAt: Long,
    val status: OrderStatus,
    val totalAmount: String,
    val details: String
){

    val formatedTime: String
        get(){
            val localDateTime = Instant
                .ofEpochMilli(createAt)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
            val formatter = DateTimeFormatter
                .ofPattern(DatePattern, Locale.getDefault())

            return localDateTime.format(formatter)
        }

    val orderNumber: String
        get() = createAt.toString().takeLast(5)

}
