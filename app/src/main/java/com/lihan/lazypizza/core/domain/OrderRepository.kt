package com.lihan.lazypizza.core.domain

import com.lihan.lazypizza.core.domain.model.OrderHistory
import com.lihan.lazypizza.core.domain.util.RemoteError
import com.lihan.lazypizza.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    fun getOrderHistories(): Flow<List<OrderHistory>>

    suspend fun fetchOrderHistories()

    fun create(orderHistory: OrderHistory): Flow<Result<Unit, RemoteError>>

    suspend fun cancel(id: String)
}