package com.moneymong.moneymong.ledgermanual

import androidx.compose.ui.text.input.TextFieldValue
import com.moneymong.moneymong.android.BaseViewModel
import com.moneymong.moneymong.android.util.toMultipart
import com.moneymong.moneymong.ui.isValidPaymentDate
import com.moneymong.moneymong.ui.isValidPaymentTime
import com.moneymong.moneymong.ui.validateValue
import com.moneymong.moneymong.domain.usecase.agency.FetchAgencyIdUseCase
import com.moneymong.moneymong.domain.usecase.ledger.PostLedgerTransactionUseCase
import com.moneymong.moneymong.domain.usecase.ocr.PostFileUploadUseCase
import com.moneymong.moneymong.domain.usecase.user.FetchUserNicknameUseCase
import com.moneymong.moneymong.model.ledger.FundType
import com.moneymong.moneymong.model.ledger.LedgerTransactionRequest
import com.moneymong.moneymong.model.ocr.FileUploadRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import java.io.File
import javax.inject.Inject

@HiltViewModel
class LedgerManualViewModel @Inject constructor(
    private val postLedgerTransactionUseCase: PostLedgerTransactionUseCase,
    private val postFileUploadUseCase: PostFileUploadUseCase,
    private val fetchAgencyIdUseCase: FetchAgencyIdUseCase,
    private val fetchUserNicknameUseCase: FetchUserNicknameUseCase
) : BaseViewModel<LedgerManualState, LedgerManualSideEffect>(LedgerManualState()) {

    init {
        fetchUserInfo()
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
            val ledgerTransactionRequest = LedgerTransactionRequest(
                storeInfo = state.storeNameValue.text,
                fundType = state.fundType.name,
                amount = state.totalPriceValue.text.toInt(),
                description = state.memoValue.text.ifEmpty { "내용 없음" },
                paymentDate = state.postPaymentDate,
                documentImageUrls = state.documentList,
            )
            postLedgerTransactionUseCase(state.agencyId, ledgerTransactionRequest)
                .onSuccess {
                    postSideEffect(LedgerManualSideEffect.LedgerManualNavigateToLedger)
                }.onFailure {
                    reduce {
                        state.copy(
                            showErrorDialog = true,
                            errorMessage = it.message.orEmpty()
                        )
                    }
                }.also { reduce { state.copy(isLoading = false) } }
        }
    }

    fun postS3URLImage(imageFile: File?) = intent {
        imageFile?.let {
            if (!state.isLoading) {
                reduce { state.copy(isLoading = true) }
                val file = FileUploadRequest(it.toMultipart(), "ledgerManual")
                postFileUploadUseCase(file)
                    .onSuccess { response ->
                        reduce { state.copy(documentList = state.documentList + response.path) }
                    }.onFailure {
                        // FIXME : 정책 필요
                    }.also {
                        reduce {
                            state.copy(
                                isLoading = false,
                            )
                        }
                    }
            }
        }
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

    fun onOpenImagePicker() = intent {
        postSideEffect(LedgerManualSideEffect.LedgerManualOpenImagePicker)
    }

    fun onChangeFundType(fundType: FundType) = intent {
        reduce { state.copy(fundType = fundType) }
    }

    fun visibleErrorDialog(visible: Boolean) = intent {
        reduce { state.copy(showErrorDialog = visible) }
    }

    fun visiblePopBackStackModal(visible: Boolean) = intent {
        reduce { state.copy(showPopBackStackModal = visible) }
    }

    fun removeDocumentImage(image: String) = intent {
        reduce { state.copy(documentList = state.documentList - image) }
    }

    fun onClickPostTransaction() = eventEmit(LedgerManualSideEffect.LedgerManualPostTransaction)

    fun onClickErrorDialogConfirm() = eventEmit(LedgerManualSideEffect.LedgerManualHideErrorDialog)

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