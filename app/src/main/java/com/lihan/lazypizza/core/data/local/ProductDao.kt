package com.lihan.lazypizza.core.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Upsert
    suspend fun upsert(productEntity: ProductEntity)

    @Query("SELECT * FROM ProductEntity")
    fun getProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM ProductEntity WHERE id=:id")
    fun getProductById(id: String): Flow<ProductEntity?>

    @Transaction
    suspend fun upsertProductEntities(productEntities: List<ProductEntity>){
        productEntities.forEach { productEntity ->
            upsert(productEntity)
        }
    }

}