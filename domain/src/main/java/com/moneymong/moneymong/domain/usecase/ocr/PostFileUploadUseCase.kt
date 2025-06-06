package com.moneymong.moneymong.domain.usecase.ocr

import com.moneymong.moneymong.domain.repository.ocr.OCRRepository
import com.moneymong.moneymong.model.ocr.FileUploadRequest
import com.moneymong.moneymong.model.ocr.FileUploadResponse
import javax.inject.Inject

class PostFileUploadUseCase
    @Inject
    constructor(
        private val ocrRepository: OCRRepository,
    ) {
        suspend operator fun invoke(request: FileUploadRequest): Result<FileUploadResponse> =
            ocrRepository.postFileUpload(request)
    }
