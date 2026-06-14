package com.lihan.lazypizza.core.data.repository

import com.lihan.lazypizza.core.data.local.CartDao
import com.lihan.lazypizza.core.data.mapper.toDomain
import com.lihan.lazypizza.core.data.mapper.toEntity
import com.lihan.lazypizza.core.domain.CartRepository
import com.lihan.lazypizza.core.domain.model.CartItem
import com.lihan.lazypizza.core.domain.model.CartItemTopping
import com.lihan.lazypizza.core.domain.model.CartItemWithToppings
import com.lihan.lazypizza.core.domain.model.Product
import com.lihan.lazypizza.core.domain.model.Topping
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultCartRepository(
    private val cartDao: CartDao
): CartRepository{

    override suspend fun insertCartItemWithToppings(
        cartItem: CartItem,
        cartTopping: List<CartItemTopping>
    ) {
        cartDao.insertCartItemWithToppings(
            cartItem = cartItem.toEntity(),
            toppings = cartTopping.map { it.toEntity() }
        )

    }

    override fun getCartItems(): Flow<List<CartItemWithToppings>> {
       return cartDao.getCartWithToppingsList().map { cartItemWithToppingEntities ->
           cartItemWithToppingEntities.map {
               it.toDomain()
           }
       }
    }
}