package com.lihan.lazypizza.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.lihan.lazypizza.core.domain.UserDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class DefaultUserDataStore(
    private val context: Context
): UserDataStore {

    companion object {
        private val KEY_IS_ORDERING = booleanPreferencesKey("is_ordering")
        private val KEY_ORDER_ID = intPreferencesKey("order_id")
        private val KEY_VERIFICATION_ID = stringPreferencesKey("verification_id")
        private val KEY_USER_ID = stringPreferencesKey("user_id")
        private val KEY_PHONE_NUMBER = stringPreferencesKey("phone_number")
    }

    override suspend fun setIsOrdering(value: Boolean) {
        context.userDataStore.edit { preferences ->
            preferences[KEY_IS_ORDERING] = value
        }
    }

    override fun getIsOrdering(): Flow<Boolean> {
        return context.userDataStore.data.map { preferences ->
            preferences[KEY_IS_ORDERING]?:false
        }
    }

    override suspend fun setOrderId(value: Int) {
        context.userDataStore.edit { preferences ->
            preferences[KEY_ORDER_ID] = value
        }
    }

    override fun getOrderId(): Flow<Int> {
        return context.userDataStore.data.map { preferences ->
            preferences[KEY_ORDER_ID]?:0
        }
    }

    override suspend fun setVerificationId(id: String) {
        context.userDataStore.edit { preferences ->
            preferences[KEY_VERIFICATION_ID] = id
        }
    }

    override fun getVerificationId(): Flow<String> {
        return context.userDataStore.data.map { preferences ->
            preferences[KEY_VERIFICATION_ID]?:""
        }
    }

    override suspend fun setUserId(value: String) {
        context.userDataStore.edit { preferences ->
            preferences[KEY_USER_ID] = value
        }
    }

    override fun getUserId(): Flow<String> {
        return context.userDataStore.data.map { preferences ->
            preferences[KEY_USER_ID]?:""
        }
    }

    override suspend fun setUserPhoneNumber(phoneNumber: String) {
        context.userDataStore.edit { preferences ->
            preferences[KEY_PHONE_NUMBER] = phoneNumber
        }
    }

    override fun getUserPhoneNumber(): Flow<String> {
        return context.userDataStore.data.map { preferences ->
            preferences[KEY_PHONE_NUMBER]?:""
        }
    }

    override suspend fun clearUserData() {
        setUserId("")
        setUserPhoneNumber("")
    }


}