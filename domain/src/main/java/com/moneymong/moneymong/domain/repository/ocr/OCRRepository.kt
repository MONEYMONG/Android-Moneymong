package com.moneymong.moneymong.domain.repository.ocr

import com.moneymong.moneymong.model.ocr.DocumentRequest
import com.moneymong.moneymong.model.ocr.DocumentResponse
import com.moneymong.moneymong.model.ocr.FileUploadRequest
import com.moneymong.moneymong.model.ocr.FileUploadResponse

interface OCRRepository {
    suspend fun documentOCR(body: DocumentRequest): Result<DocumentResponse>

    suspend fun postFileUpload(body: FileUploadRequest): Result<FileUploadResponse>
}
