package com.moneymong.moneymong.data.datasource.ocr

import com.moneymong.moneymong.model.ocr.DocumentRequest
import com.moneymong.moneymong.model.ocr.FileUploadRequest
import com.moneymong.moneymong.model.ocr.DocumentResponse
import com.moneymong.moneymong.model.ocr.FileUploadResponse

interface OCRRemoteDataSource {
    suspend fun documentOCR(body: DocumentRequest): Result<DocumentResponse>
    suspend fun postFileUpload(body: FileUploadRequest): Result<FileUploadResponse>
}