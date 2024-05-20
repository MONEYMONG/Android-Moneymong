package com.moneymong.moneymong.domain.usecase.ocr

import com.moneymong.moneymong.domain.base.BaseUseCase
import com.moneymong.moneymong.domain.repository.ocr.OCRRepository
import com.moneymong.moneymong.model.ocr.DocumentRequest
import com.moneymong.moneymong.model.ocr.DocumentResponse
import javax.inject.Inject

class DocumentOCRUseCase @Inject constructor(
    private val ocrRepository: OCRRepository
): BaseUseCase<DocumentRequest, Result<DocumentResponse>>() {
    override suspend fun invoke(data: DocumentRequest): Result<DocumentResponse> =
        ocrRepository.documentOCR(data)
}