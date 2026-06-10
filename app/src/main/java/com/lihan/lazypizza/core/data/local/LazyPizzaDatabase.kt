package com.lihan.lazypizza.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [ProductEntity::class, ToppingEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LazyPizzaDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun toppingDao(): ToppingDao
}