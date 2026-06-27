package com.lihan.lazypizza.core.data.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lihan.lazypizza.core.data.local.OrderDao
import com.lihan.lazypizza.core.data.mapper.toDomain
import com.lihan.lazypizza.core.data.mapper.toEntity
import com.lihan.lazypizza.core.domain.OrderRepository
import com.lihan.lazypizza.core.domain.RemoteDataSource
import com.lihan.lazypizza.core.domain.model.OrderHistory
import com.lihan.lazypizza.core.domain.util.RemoteError
import com.lihan.lazypizza.core.domain.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class OfflineFirstOrderRepository(
    private val orderDao: OrderDao,
    private val firebaseRemoteDataSource: RemoteDataSource
): OrderRepository {


    override fun getOrderHistories(): Flow<List<OrderHistory>> {
        return orderDao.getOrderHistories(
            userId = Firebase.auth.uid?:""
        ).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun fetchOrderHistories() {
        val remoteData = firebaseRemoteDataSource.getOrderHistories()
        println("fetch: ${remoteData}")
        withContext(Dispatchers.IO){
            orderDao.updateOrderHistories(
                orderHistoryEntities = remoteData.map { it.toEntity() }
            )
        }
    }

    override fun create(orderHistory: OrderHistory): Flow<Result<Unit, RemoteError>>{
        return firebaseRemoteDataSource.create(orderHistory)
    }

    override suspend fun cancel(id: String) {
        firebaseRemoteDataSource.cancel(id)
    }
}