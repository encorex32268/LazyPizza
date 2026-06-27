package com.lihan.lazypizza.core.domain

import com.lihan.lazypizza.core.domain.model.CartItem
import com.lihan.lazypizza.core.domain.model.CartItemTopping
import com.lihan.lazypizza.core.domain.model.CartItemWithToppings
import com.lihan.lazypizza.core.domain.model.Product
import com.lihan.lazypizza.core.domain.model.Topping
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    suspend fun insertCartItemWithToppings(
        cartItem: CartItem,
        cartTopping: List<CartItemTopping>
    )

    fun getCartItems(): Flow<List<CartItemWithToppings>>

    suspend fun updateCartItemQuantity(cartItemId: Long, quantity: Int)

    suspend fun deleteCartItem(cartItemId: Long)

    suspend fun cleanCart(cartId: String)


}