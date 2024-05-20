package com.moneymong.moneymong.data.repository.ocr

import com.moneymong.moneymong.data.datasource.ocr.OCRRemoteDataSource
import com.moneymong.moneymong.domain.repository.ocr.OCRRepository
import com.moneymong.moneymong.model.ocr.DocumentRequest
import com.moneymong.moneymong.model.ocr.DocumentResponse
import com.moneymong.moneymong.model.ocr.FileUploadRequest
import com.moneymong.moneymong.model.ocr.FileUploadResponse
import javax.inject.Inject

class OCRRepositoryImpl @Inject constructor(
    private val ocrRemoteDataSource: OCRRemoteDataSource
): OCRRepository {
    override suspend fun documentOCR(body: DocumentRequest): Result<DocumentResponse> =
        ocrRemoteDataSource.documentOCR(body)

    override suspend fun postFileUpload(body: FileUploadRequest): Result<FileUploadResponse> =
        ocrRemoteDataSource.postFileUpload(body)
}