package com.lihan.lazypizza.core.data.repository

import com.lihan.lazypizza.core.data.local.ProductDao
import com.lihan.lazypizza.core.data.local.ToppingDao
import com.lihan.lazypizza.core.data.mapper.toDomain
import com.lihan.lazypizza.core.data.mapper.toEntity
import com.lihan.lazypizza.core.domain.RemoteDataSource
import com.lihan.lazypizza.core.domain.StoreProductRepository
import com.lihan.lazypizza.core.domain.model.Product
import com.lihan.lazypizza.core.domain.model.Topping
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class OfflineFirstStoreProductRepository(
    private val remoteDataSource: RemoteDataSource,
    private val productDao: ProductDao,
    private val toppingDao: ToppingDao,
): StoreProductRepository {

    override suspend fun getProducts(): Flow<List<Product>> {

        val isDatabaseEmpty =productDao
            .getProducts().first().isEmpty()

        if (isDatabaseEmpty){

            val products = remoteDataSource.getProducts()

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

    override suspend fun getRecommendProducts(): Flow<List<Product>> {
        return productDao
            .getRecommendProducts()
            .map { productEntities ->
                productEntities.map { it.toDomain() }
            }
    }

    override suspend fun getPizzaById(id: String): Product?{
        return productDao.getProductById(id).first()?.toDomain()

    }

    override suspend fun getToppings(): Flow<List<Topping>> {

        val isDatabaseEmpty =toppingDao
            .getToppings().first().isEmpty()

        if (isDatabaseEmpty){

            val toppings = remoteDataSource.getToppings()

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