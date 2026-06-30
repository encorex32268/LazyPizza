package com.lihan.lazypizza.core.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {


    @Upsert
    fun upsert(orderHistoryEntity: OrderHistoryEntity)

    @Query("SELECT * FROM OrderHistoryEntity WHERE userId=:userId ")
    fun getOrderHistories(userId: String): Flow<List<OrderHistoryEntity>>


    @Transaction
    fun updateOrderHistories(orderHistoryEntities: List<OrderHistoryEntity>){
        orderHistoryEntities.forEach {
            upsert(it)
        }
    }
}