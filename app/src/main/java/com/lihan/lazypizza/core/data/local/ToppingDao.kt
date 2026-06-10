package com.lihan.lazypizza.core.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ToppingDao {

    @Upsert
    fun upsert(toppingEntity: ToppingEntity): Long

    @Query("SELECT * FROM ToppingEntity")
    fun getToppings(): Flow<List<ToppingEntity>>


    @Transaction
    suspend fun upsertToppingEntities(toppingEntities: List<ToppingEntity>){
        toppingEntities.forEach { toppingEntity ->
            upsert(toppingEntity)
        }
    }

}