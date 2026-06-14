package com.lihan.lazypizza.core.domain

import kotlinx.coroutines.flow.Flow

interface UserDataStore {

    suspend fun setIsOrdering(value: Boolean)
    fun getIsOrdering(): Flow<Boolean>

    suspend fun setOrderId(value: Int)
    fun getOrderId(): Flow<Int>
}