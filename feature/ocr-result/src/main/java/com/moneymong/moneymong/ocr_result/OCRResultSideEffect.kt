package com.moneymong.moneymong.ocr_result

import com.moneymong.moneymong.common.base.SideEffect
import com.moneymong.moneymong.model.ocr.DocumentResponse

sealed class OCRResultSideEffect : SideEffect {
    data object OCRResultShowSnackbar : OCRResultSideEffect()
    data object OCRResultNavigateToLedger : OCRResultSideEffect()
    data class OCRResultNavigateToOCRDetail(val document: DocumentResponse?) : OCRResultSideEffect()
}
