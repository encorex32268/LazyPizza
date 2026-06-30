package com.lihan.lazypizza.core.data.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.lihan.lazypizza.core.data.model.OrderDto
import com.lihan.lazypizza.core.data.model.ProductDto
import com.lihan.lazypizza.core.data.model.ToppingDto
import com.lihan.lazypizza.core.domain.RemoteDataSource
import com.lihan.lazypizza.core.domain.model.OrderHistory
import com.lihan.lazypizza.core.domain.util.RemoteError
import com.lihan.lazypizza.core.domain.util.Result
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseRemoteDataSource(
    private val firebaseFirestore: FirebaseFirestore
): RemoteDataSource {

    override suspend fun getOrderHistories(): List<OrderDto> {
        val userId = Firebase.auth.uid
        if (userId.isNullOrEmpty()){
            return emptyList()
        }
        val result = firebaseFirestore
            .collection("orders")
            .document(userId)
            .collection("orders")
            .get()
            .await()

        return result
            .filter { it.exists() }
            .map { document ->
                document.toObject<OrderDto>()
            }

    }

    override fun create(orderHistory: OrderHistory): Flow<Result<Unit, RemoteError>> =
        callbackFlow {
            val userId = Firebase.auth.uid
            if (userId.isNullOrEmpty()){
                trySend(Result.Error(RemoteError.UnknownUser))
                close()
                return@callbackFlow
            }
            val ordersCollection = firebaseFirestore
                .collection("orders")
                .document(userId)
                .collection("orders")

            val newOrderRef = ordersCollection.document()


            val modifiedOrderHistory = orderHistory.copy(
                id = newOrderRef.id,
                userId = userId
            )

            ordersCollection
                .document(newOrderRef.id)
                .set(modifiedOrderHistory)
                .addOnSuccessListener {
                    trySend(Result.Success(Unit))
                    close()
                }
                .addOnCanceledListener {
                    trySend(Result.Error(RemoteError.CreateOrderCanceled))
                    close()
                }
                .addOnFailureListener {
                    trySend(Result.Error(RemoteError.CreateOrderFailed))
                    close()
                }

            awaitClose()

        }

    override suspend fun cancel(id: String) {
        // feature...
    }

    override suspend fun getProducts(): List<ProductDto> {

        val result = firebaseFirestore
            .collection("products")
            .get()
            .await()

        return result
            .filter { it.exists() }
            .map { document ->
                document.toObject<ProductDto>()
            }
    }


    override suspend fun getToppings(): List<ToppingDto> {

         val result  = firebaseFirestore
            .collection("toppings")
            .get()
            .await()

        return result
            .filter { it.exists() }
            .map { document ->
                document.toObject<ToppingDto>()
            }
    }
}
