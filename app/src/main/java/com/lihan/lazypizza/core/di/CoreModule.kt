package com.lihan.lazypizza.core.di

import androidx.room.Room
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.lihan.lazypizza.core.data.DefaultUserDataStore
import com.lihan.lazypizza.core.data.local.LazyPizzaDatabase
import com.lihan.lazypizza.core.data.repository.DefaultCartRepository
import com.lihan.lazypizza.core.data.repository.OfflineFirstStoreProductRepository
import com.lihan.lazypizza.core.domain.CartRepository
import com.lihan.lazypizza.core.domain.StoreProductRepository
import com.lihan.lazypizza.core.domain.UserDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreModule = module {

    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = LazyPizzaDatabase::class.java,
            name = "lazy_pizza.db"
        ).build()
    }

    singleOf(::DefaultUserDataStore).bind<UserDataStore>()

    single { get<LazyPizzaDatabase>().productDao() }
    single { get<LazyPizzaDatabase>().toppingDao() }
    single { get<LazyPizzaDatabase>().cartDao() }

    single { Firebase.firestore }

    singleOf(::OfflineFirstStoreProductRepository).bind<StoreProductRepository>()
    singleOf(::DefaultCartRepository).bind<CartRepository>()

}