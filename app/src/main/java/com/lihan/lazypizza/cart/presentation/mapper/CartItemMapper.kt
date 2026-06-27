@file:OptIn(ExperimentalTime::class)

package com.lihan.lazypizza.cart.presentation.mapper

import com.lihan.lazypizza.cart.presentation.model.CartItemToppingUi
import com.lihan.lazypizza.cart.presentation.model.CartItemUi
import com.lihan.lazypizza.cart.presentation.model.CartItemWithToppingsUi
import com.lihan.lazypizza.core.domain.model.CartItem
import com.lihan.lazypizza.core.domain.model.CartItemTopping
import com.lihan.lazypizza.core.domain.model.CartItemWithToppings
import com.lihan.lazypizza.core.domain.model.OrderHistory
import java.time.LocalDateTime
import java.time.ZonedDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


fun CartItem.toUi(): CartItemUi? {
    if (cartItemId == null) return null
    return CartItemUi(
        cartItemId = cartItemId,
        id = id,
        productId = productId,
        quantity = quantity,
        name = name,
        price = price,
        imageUrl = imageUrl
    )
}

fun CartItemTopping.toUi(): CartItemToppingUi {
    return CartItemToppingUi(
        cartItemId = cartItemId,
        toppingId = toppingId,
        quantity = quantity,
        name = name,
        price = price
    )
}

fun CartItemWithToppings.toUi(): CartItemWithToppingsUi?{
    val toUiResult = cartItem.toUi() ?: return null
    return CartItemWithToppingsUi(
        cartItem = toUiResult,
        toppings = toppings.map { it.toUi() }
    )
}

fun List<CartItemWithToppingsUi>.toDomainOrderHistory(): OrderHistory {
    val createAt = Clock.System.now().toEpochMilliseconds()
    val details = this.map { cartItemWithToppingsUi ->
        val name = cartItemWithToppingsUi.cartItem.name
        val totalCount = cartItemWithToppingsUi.cartItem.quantity
        val toppingsDescription = if (cartItemWithToppingsUi.toppingsDescription.isEmpty()){
            ""
        }else{
            "(${cartItemWithToppingsUi.toppingsDescription})"
        }

        "$totalCount x $name $toppingsDescription"
    }
    return OrderHistory(
        createAt = createAt,
        totalAmount = this.sumOf { it.totalPrice.replace("$","").toDouble() },
        orderNumber = createAt.toString().takeLast(5).toLong(),
        status = 0,
        details = details
    )
}