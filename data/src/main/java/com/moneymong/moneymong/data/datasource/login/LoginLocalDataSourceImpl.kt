package com.moneymong.moneymong.data.datasource.login

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.moneymong.moneymong.model.sign.UserDataStoreInfoResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = "jwt")

class LoginLocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : LoginLocalDataSource {
    private val accessTokenKey = stringPreferencesKey("ACCESS_TOKEN")
    private val refreshTokenKey = stringPreferencesKey("REFRESH_TOKEN")
    private val loginSuccessKey = booleanPreferencesKey("LOGIN_SUCCESS")
    private val schoolInfoProvidedKey = booleanPreferencesKey("SCHOOL_INFO_PROVIDED")

    override suspend fun getRefreshToken(): Result<String> {
        val preferences = context.dataStore.data.first()
        return preferences[refreshTokenKey]?.let { Result.success(it) }
            ?: Result.failure(Exception("refreshToken is null"))
    }

    override suspend fun getAccessToken(): Result<String> {
        val preferences = context.dataStore.data.first()
        return preferences[accessTokenKey]?.let { Result.success(it) }
            ?: Result.failure(Exception("accessToken is null"))
    }

    override suspend fun getDataStoreInfo(): Result<UserDataStoreInfoResponse> {
        val preferences = context.dataStore.data.first()
        return Result.success(UserDataStoreInfoResponse(preferences[accessTokenKey] ?: "",preferences[schoolInfoProvidedKey] ?: false))
    }

    override suspend fun updateTokens(accessToken: String, refreshToken: String) {
        context.dataStore.edit { preferences ->
            preferences[accessTokenKey] = accessToken
            preferences[refreshTokenKey] = refreshToken
        }
    }

    override suspend fun updateAccessToken(accessToken: String) {
        context.dataStore.edit { preferences ->
            preferences[accessTokenKey] = accessToken
        }
    }

    override suspend fun getSchoolInfo(): Result<Boolean> {
        val preferences = context.dataStore.data.first()
        return preferences[schoolInfoProvidedKey]?.let { Result.success(it) }
            ?: Result.failure(Exception("schoolInfoProvided is null"))
    }

    override suspend fun deleteToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(accessTokenKey)
            preferences.remove(refreshTokenKey)
            preferences.remove(loginSuccessKey)
            preferences.remove(schoolInfoProvidedKey)
        }
    }

    override suspend fun setDataStore(
        accessToken: String,
        refreshToken: String,
        success: Boolean,
        infoExist: Boolean
    ) {
        context.dataStore.edit { preferences ->
            preferences[accessTokenKey] = accessToken
            preferences[refreshTokenKey] = refreshToken
            preferences[loginSuccessKey] = success
            preferences[schoolInfoProvidedKey] = infoExist
        }
    }

    override suspend fun setSchoolInfoProvided(infoExist: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[schoolInfoProvidedKey] = infoExist
        }

    }
}

