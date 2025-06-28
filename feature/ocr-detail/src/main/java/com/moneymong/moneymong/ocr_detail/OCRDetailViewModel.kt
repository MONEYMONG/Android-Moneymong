package com.moneymong.moneymong.ocr_detail

import android.content.SharedPreferences
import androidx.compose.ui.text.input.TextFieldValue
import com.moneymong.moneymong.common.base.BaseViewModel
import com.moneymong.moneymong.common.event.Event
import com.moneymong.moneymong.common.event.EventTracker
import com.moneymong.moneymong.common.ext.toMultipart
import com.moneymong.moneymong.common.ui.isValidPaymentDate
import com.moneymong.moneymong.common.ui.isValidPaymentTime
import com.moneymong.moneymong.common.ui.validateValue
import com.moneymong.moneymong.domain.usecase.agency.FetchAgencyIdUseCase
import com.moneymong.moneymong.domain.usecase.ledger.PostLedgerTransactionUseCase
import com.moneymong.moneymong.domain.usecase.ocr.PostFileUploadUseCase
import com.moneymong.moneymong.domain.usecase.user.FetchUserNicknameUseCase
import com.moneymong.moneymong.model.ledger.FundType
import com.moneymong.moneymong.model.ledger.LedgerTransactionRequest
import com.moneymong.moneymong.model.ocr.DocumentResponse
import com.moneymong.moneymong.model.ocr.FileUploadRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class OCRDetailViewModel @Inject constructor(
    private val eventTracker: EventTracker,
    private val prefs: SharedPreferences,
    private val postLedgerTransactionUseCase: PostLedgerTransactionUseCase,
    private val postFileUploadUseCase: PostFileUploadUseCase,
    private val fetchAgencyIdUseCase: FetchAgencyIdUseCase,
    private val fetchUserNicknameUseCase: FetchUserNicknameUseCase
) : BaseViewModel<OCRDetailState, OCRDetailSideEffect>(OCRDetailState()) {

    init {
        fetchUserInfo()
        fetchReceiptImage()
    }

    fun init(document: DocumentResponse?) = intent {
        val receipt = document?.images?.first()?.receipt?.result
        val currentDate =
            SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(Date(System.currentTimeMillis()))
        val currentTime =
            SimpleDateFormat("HHmmss", Locale.KOREA).format(Date(System.currentTimeMillis()))
        val paymentDateString = receipt?.paymentInfo?.date?.formatted?.let {
            ("${it.year}${it.month}${it.day}").run {
                if (validateValue(length = 8, isDigit = true) && isValidPaymentDate()) {
                    this
                } else {
                    currentDate
                }
            }
        } ?: currentDate
        val paymentTimeString = receipt?.paymentInfo?.time?.formatted?.let {
            ("${it.hour}${it.minute}${it.second}").run {
                if (validateValue(length = 6, isDigit = true) && isValidPaymentTime()) {
                    this
                } else {
                    currentTime
                }
            }
        } ?: currentTime
        reduce {
            state.copy(
                document = document,
                storeNameValue = state.storeNameValue.copy(text = receipt?.storeInfo?.name?.text.orEmpty()),
                totalPriceValue = state.totalPriceValue.copy(text = receipt?.totalPrice?.price?.formatted?.value.orEmpty()),
                paymentDateValue = state.paymentDateValue.copy(text = paymentDateString),
                paymentTimeValue = state.paymentTimeValue.copy(text = paymentTimeString)
            )
        }
    }

    @OptIn(OrbitExperimental::class)
    fun fetchUserInfo() = blockingIntent {
        val agencyId = fetchAgencyIdUseCase()
        val userNickname = fetchUserNicknameUseCase()
        reduce {
            state.copy(
                agencyId = agencyId,
                authorName = userNickname
            )
        }
    }

    fun postLedgerTransaction() = intent {
        if (!state.isLoading) {
            reduce { state.copy(isLoading = true) }
            // empty string을 제거하고 요청을 보내기 위함
            val documentImageUrls = state.documentImageUrls - ""
            val ledgerTransactionRequest = LedgerTransactionRequest(
                storeInfo = state.storeNameValue.text,
                fundType = state.fundType.name,
                amount = state.totalPriceValue.text.replace(".", "").toInt(),
                description = state.memoValue.text,
                paymentDate = state.postPaymentDate,
                documentImageUrls = documentImageUrls.ifEmpty {
                    emptyList()
                }
            )
            postLedgerTransactionUseCase(state.agencyId, ledgerTransactionRequest)
                .onSuccess {
                    postSideEffect(OCRDetailSideEffect.OCRDetailNavigateToLedger)
                }.onFailure {
                    showErrorDialog(it.message.orEmpty())
                }.also { reduce { state.copy(isLoading = false) } }
        }
    }

    fun postDocumentImage(imageFile: File?, isReceipt: Boolean = false) = intent {
        imageFile?.let {
            if (!state.isLoading) {
                reduce { state.copy(isLoading = true) }
                val file = FileUploadRequest(it.toMultipart(), "ocr")
                postFileUploadUseCase(file)
                    .onSuccess {
                        if (isReceipt) {
                            reduce { state.copy(receiptImageUrls = listOf(it.path)) }
                            postLedgerTransaction()
                        } else {
                            reduce { state.copy(documentImageUrls = state.documentImageUrls + it.path) }
                        }
                    }.onFailure {
                        showErrorDialog(it.message.orEmpty())
                    }.also { reduce { state.copy(isLoading = false) } }
            }
        }
    }

    fun onClickPostLedger() = intent {
        eventTracker.logEvent(Event.OCR_MODIFY_TO_REGISTER_CLICK)
        postDocumentImage(imageFile = state.receiptFile, isReceipt = true)
    }

    fun onClickErrorDialogConfirm() = eventEmit(OCRDetailSideEffect.OCRDetailHideErrorDialog)

    fun addDocumentImage(file: File?) = intent {
        val newDocumentUris = state.documentImageUrls.toMutableList()
        if (newDocumentUris.size == 12) {
            newDocumentUris.removeFirst()
            reduce { state.copy(documentImageUrls = newDocumentUris) }
        }
        postDocumentImage(file)
    }

    fun removeDocumentImage(uriString: String) = intent {
        val newDocumentUris = state.documentImageUrls.toMutableList()
        if (state.documentImageUrls.size == 12 && state.documentImageUrls.first().isNotEmpty()) {
            newDocumentUris.add(0, "")
        }
        reduce { state.copy(documentImageUrls = newDocumentUris - uriString) }
    }

    fun reduceReceiptFile(file: File?) = intent {
        reduce { state.copy(receiptFile = file) }
    }

    private fun fetchReceiptImage() = blockingIntent {
        val receiptImage = prefs.getString("receiptImage", "").orEmpty()
        reduce { state.copy(receiptImage = receiptImage) }
    }

    fun onChangeStoreNameValue(value: TextFieldValue) = blockingIntent {
        val validate = value.text.validateValue(length = 20)
        if (!validate) {
            reduce { state.copy(isStoreNameError = true) }
        } else {
            reduce { state.copy(isStoreNameError = false) }
        }
        reduce { state.copy(storeNameValue = value) }
    }

    fun onChangeTotalPriceValue(value: TextFieldValue) = blockingIntent {
        val trimValue = trimStartWithZero(value)
        val validate = trimValue.text.validateValue(length = 12, isDigit = true)
        if (validate) {
            val elvisValue = value.text.ifEmpty { "0" }
            if (elvisValue.toLong() > MAX_TOTAL_PRICE) {
                reduce { state.copy(isTotalPriceError = true) }
            } else {
                reduce { state.copy(isTotalPriceError = false) }
            }

            reduce { state.copy(totalPriceValue = trimValue) }
        }
    }

    fun onChangePaymentDateValue(value: TextFieldValue) = blockingIntent {
        val validate = value.text.validateValue(length = 8, isDigit = true)
        if (validate) {
            val isPaymentDateError = !value.text.isValidPaymentDate()
            reduce {
                state.copy(
                    paymentDateValue = value,
                    isPaymentDateError = isPaymentDateError
                )
            }
        }
    }

    fun onChangePaymentTimeValue(value: TextFieldValue) = blockingIntent {
        val validate = value.text.validateValue(length = 6, isDigit = true)
        if (validate) {
            val isPaymentTimeError = !value.text.isValidPaymentTime()
            reduce {
                state.copy(
                    paymentTimeValue = value,
                    isPaymentTimeError = isPaymentTimeError
                )
            }
        }
    }

    fun onChangeMemoValue(value: TextFieldValue) = blockingIntent {
        val validate = value.text.validateValue(length = 300)
        reduce {
            state.copy(
                memoValue = value,
                isMemoError = !validate
            )
        }
    }

    fun onChangeFundType(fundType: FundType) = intent {
        reduce { state.copy(fundType = fundType) }
    }

    fun visibleErrorDialog(visible: Boolean) = intent {
        reduce {
            state.copy(showErrorDialog = visible)
        }
    }

    private fun showErrorDialog(message: String) = intent {
        reduce {
            state.copy(
                showErrorDialog = true,
                errorMessage = message
            )
        }
    }

    private fun trimStartWithZero(value: TextFieldValue) =
        if (value.text.isNotEmpty() && value.text.all { it == '0' }) {
            value.copy(text = "0")
        } else {
            value.copy(text = value.text.trimStart('0'))
        }

    companion object {
        const val MAX_TOTAL_PRICE = 999999999
    }
}