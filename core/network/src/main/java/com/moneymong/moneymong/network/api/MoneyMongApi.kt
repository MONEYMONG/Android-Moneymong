package com.moneymong.moneymong.network.api

import com.moneymong.moneymong.model.ocr.FileUploadResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MoneyMongApi {

    @Multipart
    @POST("api/v1/images")
    suspend fun postFileUpload(
        @Part file: MultipartBody.Part
    ): Result<FileUploadResponse>
}