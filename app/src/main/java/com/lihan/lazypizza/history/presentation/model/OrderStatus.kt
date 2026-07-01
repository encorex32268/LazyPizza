package com.lihan.lazypizza.history.presentation.model

import com.lihan.lazypizza.R

enum class OrderStatus {
    InProgress,Completed;
    companion object{
        fun Int.toOrderStatus(): OrderStatus {
            return when(this){
                0  -> InProgress
                1  -> Completed
                else -> InProgress
            }
        }
        fun OrderStatus.toStringResourceId(): Int{
            return when(this){
                InProgress -> R.string.order_in_progress
                Completed -> R.string.order_completed
            }
        }
    }
}