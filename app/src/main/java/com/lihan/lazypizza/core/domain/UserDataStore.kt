package com.lihan.lazypizza.core.domain

import kotlinx.coroutines.flow.Flow

interface UserDataStore {

    suspend fun setIsOrdering(value: Boolean)
    fun getIsOrdering(): Flow<Boolean>

    suspend fun setOrderId(value: Int)
    fun getOrderId(): Flow<Int>

    suspend fun setVerificationId(id: String)
    fun getVerificationId(): Flow<String>

    suspend fun setUserId(value: String)
    fun getUserId(): Flow<String>

    suspend fun setUserPhoneNumber(phoneNumber: String)
    fun getUserPhoneNumber(): Flow<String>

    suspend fun clearUserData()

}