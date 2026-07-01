package com.lihan.lazypizza.cart.presentation.order_checkout.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lihan.lazypizza.R

enum class PickUpTimeType {
    EarliestAvailableTime,ScheduleTime;
    companion object{
        @Composable
        fun PickUpTimeType.toStringResource(): String {
            return when(this){
                EarliestAvailableTime -> stringResource(R.string.earliest_pickup_time)
                ScheduleTime -> stringResource(R.string.schedule_time)
            }
        }
    }
}