package com.lihan.lazypizza.core.di

import androidx.room.Room
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.lihan.lazypizza.MainViewModel
import com.lihan.lazypizza.core.data.DefaultUserDataStore
import com.lihan.lazypizza.core.data.local.LazyPizzaDatabase
import com.lihan.lazypizza.core.data.repository.DefaultCartRepository
import com.lihan.lazypizza.core.data.repository.FirebaseRemoteDataSource
import com.lihan.lazypizza.core.data.repository.OfflineFirstOrderRepository
import com.lihan.lazypizza.core.data.repository.OfflineFirstStoreProductRepository
import com.lihan.lazypizza.core.domain.CartRepository
import com.lihan.lazypizza.core.domain.OrderRepository
import com.lihan.lazypizza.core.domain.RemoteDataSource
import com.lihan.lazypizza.core.domain.StoreProductRepository
import com.lihan.lazypizza.core.domain.UserDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
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
    single { get<LazyPizzaDatabase>().orderDao() }

    single { Firebase.firestore }

    singleOf(::FirebaseRemoteDataSource).bind<RemoteDataSource>()

    singleOf(::OfflineFirstStoreProductRepository).bind<StoreProductRepository>()
    singleOf(::DefaultCartRepository).bind<CartRepository>()
    singleOf(::OfflineFirstOrderRepository).bind<OrderRepository>()

    viewModelOf(::MainViewModel)

}