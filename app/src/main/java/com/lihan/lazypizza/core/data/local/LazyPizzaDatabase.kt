package com.lihan.lazypizza.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [
        ProductEntity::class,
        ToppingEntity::class,
        CartItemEntity::class,
        CartItemToppingEntity::class,
        OrderHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class LazyPizzaDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun toppingDao(): ToppingDao
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
}