package com.lihan.lazypizza.core.domain

import com.lihan.lazypizza.core.data.model.OrderDto
import com.lihan.lazypizza.core.data.model.ProductDto
import com.lihan.lazypizza.core.data.model.ToppingDto
import com.lihan.lazypizza.core.domain.model.OrderHistory
import com.lihan.lazypizza.core.domain.util.RemoteError
import com.lihan.lazypizza.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    //Order
    suspend fun getOrderHistories(): List<OrderDto>

    fun create(orderHistory: OrderHistory): Flow<Result<Unit, RemoteError>>

    suspend fun cancel(id: String)

    //Product
    suspend fun getProducts(): List<ProductDto>

    //Toppings
    suspend fun getToppings(): List<ToppingDto>


}