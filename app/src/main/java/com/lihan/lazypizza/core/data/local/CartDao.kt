package com.lihan.lazypizza.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.lihan.lazypizza.core.domain.model.CartItemTopping
import com.lihan.lazypizza.core.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first


@Dao
interface CartDao {


    @Upsert
    suspend fun insertCartItem(cartItems: CartItemEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItemEntities(cartItems: List<CartItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItemToppingEntities(toppings: List<CartItemToppingEntity>)

    @Query("SELECT * From CartItemEntity WHERE productId =:productId")
    suspend fun getCartItemByProductId(productId: String): CartItemEntity?


    @Query("SELECT * From CartItemToppingEntity WHERE cartItemId =:cartItemId")
    fun getCartItemToppingByCartItemId(cartItemId: Long): Flow<List<CartItemToppingEntity>>


    @Transaction
    suspend fun insertCartItemWithToppings(
        cartItem: CartItemEntity,
        toppings: List<CartItemToppingEntity>
    ) {
        //Pizza以外的Product
        // Step 1: 先插入主商品，並拿到資料庫為它生成的唯一數字 ID (cartItemId)

        val isPizza = cartItem.productId.startsWith("pizza_")
        val existCartItem = getCartItemByProductId(cartItem.productId)
        if (!isPizza){

            if (existCartItem == null){
                insertCartItem(cartItem)
            }else{
                insertCartItem(cartItem.copy(cartItemId = existCartItem.cartItemId))
            }

        }else{
            if (existCartItem == null){
                val generatedCartItemId = insertCartItem(cartItem)

                val toppingsWithId = toppings.map { topping ->
                    topping.copy(cartItemId = generatedCartItemId)
                }

                insertCartItemToppingEntities(toppingsWithId)
            }else{

                val existCartItemTopping = getCartItemToppingByCartItemId(existCartItem.cartItemId!!).first()
                    .map { it.toppingId.trim() }
                    .sorted()

                val newToppings = toppings
                    .map { it.toppingId.trim() }
                    .sorted()

                if (existCartItemTopping != newToppings){
                    val generatedCartItemId = insertCartItem(cartItem)

                    val toppingsWithId = toppings.map { topping ->
                        topping.copy(cartItemId = generatedCartItemId)
                    }

                    insertCartItemToppingEntities(toppingsWithId)
                }else {
                    //Same pizza exist plus 1
                    insertCartItem(existCartItem.copy(
                        cartItemId = existCartItem.cartItemId,
                        quantity = existCartItem.quantity + 1)
                    )
                }
            }


        }



    }


    @Transaction
    @Query("SELECT * FROM cartitementity WHERE id = :cartId")
    fun getCartWithToppingsListById(cartId: String): Flow<List<CartItemWithToppingsDB>>

    @Transaction
    @Query("SELECT * FROM cartitementity")
    fun getCartWithToppingsList(): Flow<List<CartItemWithToppingsDB>>


    @Query("DELETE FROM cartitementity WHERE cartItemId = :cartItemId")
    suspend fun deleteCartItemById(cartItemId: Long)

    /**
     * 清空整個購物車
     */
    @Query("DELETE FROM cartitementity WHERE id = :cartId")
    suspend fun clearCart(cartId: String)




}