package com.moneymong.moneymong.ocr_result

import com.moneymong.moneymong.android.State
import com.moneymong.moneymong.common.util.toZonedDateTime
import com.moneymong.moneymong.ui.isValidPaymentDate
import com.moneymong.moneymong.ui.isValidPaymentTime
import com.moneymong.moneymong.ui.toWonFormat
import com.moneymong.moneymong.ui.validateValue
import com.moneymong.moneymong.model.ocr.DocumentResponse
import com.moneymong.moneymong.model.ocr.DocumentResult
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class OCRResultState(
    val isLoading: Boolean = false,
    val receiptImage: String = "",
    val receiptFile: File? = null,
    val document: DocumentResponse? = null,
    val memo: String = "내용 없음",
    val agencyId: Int = 0
) : State {

    val receipt: DocumentResult?
        get() = document?.images?.first()?.receipt?.result

    val visibleSnackbar: Boolean
        get() {
            val hasSource = receipt?.storeInfo?.name?.text.orEmpty().isNotEmpty()
            val hasPrice = receipt?.totalPrice?.price?.text.orEmpty().isNotEmpty()
            val hasDate = receipt?.paymentInfo?.date?.text.orEmpty().isNotEmpty()
            val hasTime = receipt?.paymentInfo?.time?.text.orEmpty().isNotEmpty()

            return !hasSource || !hasPrice || !hasDate || !hasTime
        }

    val btnEnabled: Boolean
        get() {
            val hasSource = receipt?.storeInfo?.name?.text.orEmpty().isNotEmpty()
            val hasPrice = receipt?.totalPrice?.price?.text.orEmpty().isNotEmpty()

            return hasSource && hasPrice
        }

    val formattedPrice: String
        get() = receipt?.totalPrice?.price?.formatted?.value.orEmpty().toWonFormat(true)

    val formattedDate: String
        get() {
            val currentDate = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA).format(Date(System.currentTimeMillis()))
            return receipt?.paymentInfo?.date?.formatted?.let {
                ("${it.year}${it.month}${it.day}").run {
                    if (validateValue(length = 8, isDigit = true) && isValidPaymentDate()) {
                        "${it.year}년 ${it.month}월 ${it.day}일"
                    } else {
                        currentDate
                    }
                }
            } ?: currentDate
        }

    val formattedTime: String
        get() {
            val currentTime = SimpleDateFormat("HH:mm:ss", Locale.KOREA).format(Date(System.currentTimeMillis()))
            return receipt?.paymentInfo?.time?.formatted?.let {
                ("${it.hour}${it.minute}${it.second}").run {
                    if (validateValue(length = 6, isDigit = true) && isValidPaymentTime()) {
                        "${it.hour}:${it.minute}:${it.second}"
                    } else {
                        currentTime
                    }
                }
            } ?: currentTime
        }

    val postPaymentDate: String
        get() = "$formattedDate $formattedTime".toZonedDateTime()
}
