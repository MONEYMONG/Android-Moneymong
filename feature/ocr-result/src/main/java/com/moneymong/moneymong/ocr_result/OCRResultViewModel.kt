package com.moneymong.moneymong.ocr_result

import android.content.SharedPreferences
import com.moneymong.moneymong.common.base.BaseViewModel
import com.moneymong.moneymong.common.ext.toMultipart
import com.moneymong.moneymong.domain.usecase.agency.FetchAgencyIdUseCase
import com.moneymong.moneymong.domain.usecase.ledger.PostLedgerTransactionUseCase
import com.moneymong.moneymong.domain.usecase.ocr.PostFileUploadUseCase
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
import javax.inject.Inject

@HiltViewModel
class OCRResultViewModel @Inject constructor(
    private val prefs: SharedPreferences,
    private val postLedgerTransactionUseCase: PostLedgerTransactionUseCase,
    private val postFileUploadUseCase: PostFileUploadUseCase,
    private val fetchAgencyIdUseCase: FetchAgencyIdUseCase
) : BaseViewModel<OCRResultState, OCRResultSideEffect>(OCRResultState()) {

    init {
        fetchAgencyId()
        fetchReceiptImage()
    }

    @OptIn(OrbitExperimental::class)
    fun fetchAgencyId() = blockingIntent {
        val agencyId = fetchAgencyIdUseCase()
        reduce { state.copy(agencyId = agencyId) }
    }

    fun postLedgerTransaction(s3Url: String) = intent {
        if (!state.isLoading) {
            reduce { state.copy(isLoading = true) }
            val ledgerTransactionRequest = LedgerTransactionRequest(
                storeInfo = state.receipt?.storeInfo?.name?.text.orEmpty(),
                fundType = FundType.EXPENSE.name,
                amount = state.receipt?.totalPrice?.price?.formatted?.value.orEmpty().toInt(),
                description = state.memo,
                paymentDate = state.postPaymentDate,
                documentImageUrls = listOf(s3Url)
            )
            postLedgerTransactionUseCase(state.agencyId, ledgerTransactionRequest)
                .onSuccess {
                    postSideEffect(OCRResultSideEffect.OCRResultNavigateToLedger)
                }.onFailure {
                    // TODO
                }.also { reduce { state.copy(isLoading = false) } }
        }
    }

    fun postReceiptImage() = intent {
        state.receiptFile?.let {
            if (!state.isLoading) {
                reduce { state.copy(isLoading = true) }
                val file = FileUploadRequest(it.toMultipart(), "ocr")
                postFileUploadUseCase(file)
                    .onSuccess {
                        postLedgerTransaction(it.path)
                    }.onFailure {
                        // TODO
                    }.also { reduce { state.copy(isLoading = false) } }
            }
        }
    }

    fun updateDocument(document: DocumentResponse?) = intent {
        reduce { state.copy(document = document) }

        if (state.visibleSnackbar) {
            postSideEffect(OCRResultSideEffect.OCRResultShowSnackbar)
        }
    }

    fun reduceReceiptFile(file: File?) = intent {
        reduce { state.copy(receiptFile = file) }
    }

    @OptIn(OrbitExperimental::class)
    private fun fetchReceiptImage() = blockingIntent {
        val receiptImage = prefs.getString("receiptImage", "").orEmpty()
        reduce { state.copy(receiptImage = receiptImage) }
    }

    // onClick
    fun onClickOCREdit() = intent {
        postSideEffect(OCRResultSideEffect.OCRResultNavigateToOCRDetail(state.document))
    }
}