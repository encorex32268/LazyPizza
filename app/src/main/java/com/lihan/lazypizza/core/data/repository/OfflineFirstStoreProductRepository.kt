package com.lihan.lazypizza.core.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.lihan.lazypizza.core.data.local.ProductDao
import com.lihan.lazypizza.core.data.local.ToppingDao
import com.lihan.lazypizza.core.data.mapper.toDomain
import com.lihan.lazypizza.core.data.mapper.toEntity
import com.lihan.lazypizza.core.data.model.ProductDto
import com.lihan.lazypizza.core.data.model.ToppingDto
import com.lihan.lazypizza.core.domain.StoreProductRepository
import com.lihan.lazypizza.core.domain.model.Product
import com.lihan.lazypizza.core.domain.model.Topping
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.coroutineContext

class OfflineFirstStoreProductRepository(
    private val firebaseFirestore: FirebaseFirestore,
    private val productDao: ProductDao,
    private val toppingDao: ToppingDao,
): StoreProductRepository {

    override suspend fun getProducts(): Flow<List<Product>> {

        val isDatabaseEmpty =productDao
            .getProducts().first().isEmpty()

        if (isDatabaseEmpty){
            val result = firebaseFirestore
                .collection("products")
                .get()
                .await()

            val products = result.map { document ->
                val data = document.data
                ProductDto(
                    id = document.id,
                    name = data["name"] as? String ?: "",
                    price = (data["price"] as? Number)?.toDouble() ?: 0.0,
                    category = data["category"] as? String ?: "",
                    imageUrl = data["imageUrl"] as? String ?: "",
                    ingredients = data["ingredients"] as? List<String> ?: emptyList()
                )
            }

            productDao.upsertProductEntities(
                products.map { it.toEntity() }
            )
        }

        return productDao
            .getProducts()
            .map { productEntities ->
                productEntities.map { it.toDomain() }
            }
    }

    override suspend fun getToppings(): Flow<List<Topping>> {

        val isDatabaseEmpty =toppingDao
            .getToppings().first().isEmpty()

        if (isDatabaseEmpty){

            val result = firebaseFirestore
                .collection("toppings")
                .get()
                .await()

            val toppings = result.map { document ->
                val data = document.data
                ToppingDto(
                    id = document.id,
                    name = data["name"] as? String ?: "",
                    price = (data["price"] as? Number)?.toDouble() ?: 0.0,
                    imageUrl = data["imageUrl"] as? String ?: "",
                )
            }

            toppingDao.upsertToppingEntities(
                toppings.map { it.toEntity() }
            )

        }

        return toppingDao
            .getToppings()
            .map { toppingEntities ->
                toppingEntities.map {
                    it.toDomain()
                }
            }
    }
}