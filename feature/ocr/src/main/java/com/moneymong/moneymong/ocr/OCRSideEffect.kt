package com.moneymong.moneymong.ocr

import com.moneymong.moneymong.android.SideEffect
import com.moneymong.moneymong.model.ocr.DocumentResponse

sealed class OCRSideEffect : SideEffect {
    data object OCRTakePicture : OCRSideEffect()
    data object OCRMoveToPermissionSetting : OCRSideEffect()
    data class OCRNavigateToOCRResult(val document: DocumentResponse?) : OCRSideEffect()
    data class OCRPostDocumentApi(val base64: String) : OCRSideEffect()
}
