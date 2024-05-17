package com.moneymong.moneymong.domain.usecase.ocr

import com.moneymong.moneymong.domain.base.BaseUseCase
import com.moneymong.moneymong.domain.repository.ocr.OCRRepository
import com.moneymong.moneymong.model.ocr.FileUploadRequest
import com.moneymong.moneymong.model.ocr.FileUploadResponse
import javax.inject.Inject

class PostFileUploadUseCase @Inject constructor(
    private val ocrRepository: OCRRepository
): BaseUseCase<FileUploadRequest, Result<FileUploadResponse>>() {
    override suspend fun invoke(data: FileUploadRequest): Result<FileUploadResponse> =
        ocrRepository.postFileUpload(data)
}