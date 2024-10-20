package com.moneymong.moneymong.network.api

import retrofit2.http.Body
import retrofit2.http.POST

interface VersionApi {

    @POST("api/v1/version")
    suspend fun checkUpdate(
        @Body version: String
    ): Result<String>
}