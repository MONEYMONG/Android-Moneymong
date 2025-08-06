package com.moneymong.moneymong.ocr_detail

import com.moneymong.moneymong.android.SideEffect

sealed class OCRDetailSideEffect : SideEffect {
    data object OCRDetailOpenImagePicker : OCRDetailSideEffect()
    data object OCRDetailNavigateToLedger : OCRDetailSideEffect()
    data object OCRDetailHideErrorDialog : OCRDetailSideEffect()
}
