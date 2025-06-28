package com.moneymong.moneymong.ledgerdetail

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import com.moneymong.moneymong.common.base.BaseViewModel
import com.moneymong.moneymong.common.error.MoneyMongError
import com.moneymong.moneymong.common.ext.toDateFormat
import com.moneymong.moneymong.common.ext.toMultipart
import com.moneymong.moneymong.common.ui.isValidPaymentDate
import com.moneymong.moneymong.common.ui.isValidPaymentTime
import com.moneymong.moneymong.common.ui.validateValue
import com.moneymong.moneymong.domain.usecase.ledgerdetail.FetchLedgerTransactionDetailUseCase
import com.moneymong.moneymong.domain.usecase.ledgerdetail.DeleteLedgerDetailUseCase
import com.moneymong.moneymong.domain.usecase.ledgerdetail.DeleteLedgerDocumentTransactionUseCase
import com.moneymong.moneymong.domain.usecase.ledgerdetail.PostLedgerDocumentTransactionUseCase
import com.moneymong.moneymong.domain.usecase.ledgerdetail.UpdateLedgerTransactionDetailUseCase
import com.moneymong.moneymong.domain.usecase.ocr.PostFileUploadUseCase
import com.moneymong.moneymong.ledgerdetail.navigation.LedgerDetailArgs
import com.moneymong.moneymong.model.ledgerdetail.LedgerDocumentRequest
import com.moneymong.moneymong.model.ledgerdetail.LedgerTransactionDetailRequest
import com.moneymong.moneymong.model.ledgerdetail.LedgerTransactionDetailResponse
import com.moneymong.moneymong.model.ocr.FileUploadRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import java.io.File
import javax.inject.Inject

@OptIn(OrbitExperimental::class)
@HiltViewModel
class LedgerDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchLedgerTransactionDetailUseCase: FetchLedgerTransactionDetailUseCase,
    private val updateLedgerTransactionDetailUseCase: UpdateLedgerTransactionDetailUseCase,
    private val postLedgerDocumentTransactionUseCase: PostLedgerDocumentTransactionUseCase,
    private val deleteLedgerDocumentTransactionUseCase: DeleteLedgerDocumentTransactionUseCase,
    private val postFileUploadUseCase: PostFileUploadUseCase,
    private val deleteLedgerDetailUseCase: DeleteLedgerDetailUseCase
) : BaseViewModel<LedgerDetailState, LedgerDetailSideEffect>(LedgerDetailState()) {

    init {
        onChangeStaffStatus(isStaff = LedgerDetailArgs(savedStateHandle).isStaff)
    }

    fun ledgerTransactionEdit(detailId: Int) = intent {
        reduce { state.copy(isLoading = true) }

        coroutineScope {
            launch { postLedgerDocumentTransaction(detailId) }
            launch { deleteLedgerDocumentTransaction(detailId) }
        }.join()

        updateLedgerTransactionDetail(detailId)

        reduce { state.copy(isLoading = false) }

        fetchLedgerTransactionDetail(detailId)
    }

    fun fetchLedgerTransactionDetail(detailId: Int) = intent {
        reduce { state.copy(isLoading = true) }
        fetchLedgerTransactionDetailUseCase(detailId)
            .onSuccess {
                reduce { state.copy(ledgerTransactionDetail = it) }
                initTextValue(it)
            }.onFailure {
                showErrorDialog(it.message)
            }.also { reduce { state.copy(isLoading = false) } }
    }

    fun updateLedgerTransactionDetail(detailId: Int) = intent {
        val request = LedgerTransactionDetailRequest(
            storeInfo = state.storeNameValue.text,
            amount = state.totalPriceValue.text.toInt(),
            description = state.memoValue.text,
            paymentDate = state.formattedPaymentDate
        )
        updateLedgerTransactionDetailUseCase(detailId, request)
            .onSuccess {
                reduce { state.copy(ledgerTransactionDetail = it) }
                initTextValue(it)
            }.onFailure {
                showErrorDialog(it.message)
            }
    }

    fun postLedgerDocumentTransaction(detailId: Int) = intent {
        if (state.documentList.isNotEmpty()) {
            val mapToOriginalUrl =
                state.ledgerTransactionDetail?.documentImageUrls?.map { it.documentImageUrl }
                    .orEmpty()
            val request = LedgerDocumentRequest(
                documentImageUrls = state.documentList - mapToOriginalUrl.toSet()
            )
            postLedgerDocumentTransactionUseCase(detailId, request)
                .onFailure {
                    showErrorDialog(it.message)
                }
        }
    }

    fun deleteLedgerDocumentTransaction(detailId: Int) = intent {
        if (state.documentIdList.isNotEmpty()) {
            state.documentIdList.forEach { documentId ->
                deleteLedgerDocumentTransactionUseCase(detailId, documentId)
                    .onFailure {
                        showErrorDialog(it.message)
                    }
            }
        }
    }

    fun deleteLedgerDetail(detailId: Int) = intent {
        deleteLedgerDetailUseCase(detailId)
            .onSuccess {
                postSideEffect(LedgerDetailSideEffect.LedgerDetailNavigateToLedger)
            }.onFailure {
                showErrorDialog(it.message)
            }
    }

    fun postS3URLImage(imageFile: File?) = intent {
        imageFile?.let {
            if (!state.isLoading) {
                reduce { state.copy(isLoading = true) }
                val file = FileUploadRequest(it.toMultipart(), "ledgerDetail")
                postFileUploadUseCase(file)
                    .onSuccess { response ->
                        reduce { state.copy(documentList = state.documentList + response.path) }
                    }.onFailure {
                        showErrorDialog(it.message)
                    }.also {
                        reduce { state.copy(isLoading = false) }
                    }
            }
        }
    }

    fun onClickRemoveDocument(documentImage: String) = intent {
        state.ledgerTransactionDetail?.let {
            val documentId =
                it.documentImageUrls.find { it.documentImageUrl == documentImage }?.id ?: 0
            val isOriginalDocument =
                it.documentImageUrls.map { it.documentImageUrl }.contains(documentImage)
            if (isOriginalDocument) {
                reduce {
                    state.copy(
                        documentList = state.documentList - documentImage,
                        documentIdList = state.documentIdList + documentId
                    )
                }
            } else {
                reduce {
                    state.copy(
                        documentList = state.documentList - documentImage
                    )
                }
            }
        }
    }

    fun onClickEditButton() = intent {
        if (state.useEditMode) {
            eventEmit(LedgerDetailSideEffect.LedgerDetailEditDone)
        } else {
            eventEmit(LedgerDetailSideEffect.LedgerDetailEdit)
        }
    }

    fun onClickOpenImagePicker() = eventEmit(LedgerDetailSideEffect.LedgerDetailOpenImagePicker)

    private fun initTextValue(ledgerTransactionDetail: LedgerTransactionDetailResponse) = blockingIntent {
        reduce {
            state.copy(
                storeNameValue = state.storeNameValue.copy(text = ledgerTransactionDetail.storeInfo),
                totalPriceValue = state.totalPriceValue.copy(text = ledgerTransactionDetail.amount.toString()),
                paymentDateValue = state.paymentDateValue.copy(
                    text = ledgerTransactionDetail.paymentDate.toDateFormat(
                        "yyyyMMdd"
                    )
                ),
                paymentTimeValue = state.paymentDateValue.copy(
                    text = ledgerTransactionDetail.paymentDate.toDateFormat(
                        "HHmmss"
                    )
                ),
                memoValue = state.memoValue.copy(text = ledgerTransactionDetail.description),
                documentList = ledgerTransactionDetail.documentImageUrls.map { it.documentImageUrl },
                documentIdList = emptyList()
            )
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
        val validate = value.text.validateValue(length = 12, isDigit = true)
        if (validate) {
            val elvisValue = value.text.ifEmpty { "0" }
            if (elvisValue.toLong() > MAX_TOTAL_PRICE) {
                reduce { state.copy(isTotalPriceError = true) }
            } else {
                reduce { state.copy(isTotalPriceError = false) }
            }

            reduce { state.copy(totalPriceValue = value) }
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

    fun onChangeEditMode(useEditMode: Boolean) = intent {
        reduce { state.copy(useEditMode = useEditMode) }
    }

    fun onChangeVisibleConfirmModal(visible: Boolean) = intent {
        reduce { state.copy(showConfirmModal = visible) }
    }

    fun onChangeErrorDialogVisible(visible: Boolean) = intent {
        reduce { state.copy(showErrorDialog = visible) }
    }

    private fun onChangeStaffStatus(isStaff: Boolean) = intent {
        reduce { state.copy(isStaff = isStaff) }
    }

    private fun showErrorDialog(message: String?) = intent {
        reduce {
            state.copy(
                showErrorDialog = true,
                errorMessage = message ?: MoneyMongError.UnExpectedError.message
            )
        }
    }

    companion object {
        const val MAX_TOTAL_PRICE = 999999999
    }
}