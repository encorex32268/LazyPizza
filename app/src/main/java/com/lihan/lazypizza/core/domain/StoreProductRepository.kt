package com.lihan.lazypizza.core.domain

import com.lihan.lazypizza.core.domain.model.Product
import com.lihan.lazypizza.core.domain.model.Topping
import kotlinx.coroutines.flow.Flow

interface StoreProductRepository {

    suspend fun getProducts(): Flow<List<Product>>

    suspend fun getPizzaById(id: String): Product?

    suspend fun getToppings(): Flow<List<Topping>>
}