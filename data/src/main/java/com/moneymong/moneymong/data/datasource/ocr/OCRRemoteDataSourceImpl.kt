package com.moneymong.moneymong.data.datasource.ocr

import com.moneymong.moneymong.model.ocr.DocumentRequest
import com.moneymong.moneymong.model.ocr.DocumentResponse
import com.moneymong.moneymong.model.ocr.FileUploadRequest
import com.moneymong.moneymong.model.ocr.FileUploadResponse
import com.moneymong.moneymong.network.api.ClovaApi
import com.moneymong.moneymong.network.api.MoneyMongApi
import javax.inject.Inject

class OCRRemoteDataSourceImpl
    @Inject
    constructor(
        private val clovaApi: ClovaApi,
        private val moneyMongApi: MoneyMongApi,
    ) : OCRRemoteDataSource {
        override suspend fun documentOCR(body: DocumentRequest): Result<DocumentResponse> =
            clovaApi.documentOCR(body = body)

        override suspend fun postFileUpload(body: FileUploadRequest): Result<FileUploadResponse> =
            moneyMongApi.postFileUpload(file = body.file)
    }
