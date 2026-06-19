package com.moneymong.moneymong.report

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneymong.moneymong.domain.usecase.ledger.FetchLedgerReportUseCase
import com.moneymong.moneymong.report.navigation.ReportArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchLedgerReportUseCase: FetchLedgerReportUseCase
) : ViewModel() {

    private val agencyId = ReportArgs(savedStateHandle).agencyId
    private val reportCache = ReportHalfYearCache()

    private val _uiState = MutableStateFlow(ReportUiState())
    val uiState: StateFlow<ReportUiState> = _uiState.asStateFlow()

    init {
        fetchReport()
    }

    fun fetchReport() {
        fetchReport(yearMonth = _uiState.value.selectYearMonth)
    }

    private fun fetchReport(
        yearMonth: YearMonth
    ) {
        val cachedReport = reportCache.get(yearMonth)

        if (cachedReport != null) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = null,
                    reportData = cachedReport
                )
            }
            return
        }

        val halfYearRange = yearMonth.toReportHalfYearRange()

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            fetchLedgerReportUseCase(
                agencyId = agencyId,
                startYear = halfYearRange.startYear,
                startMonth = halfYearRange.startMonth,
                endYear = halfYearRange.endYear,
                endMonth = halfYearRange.endMonth
            ).fold(
                onSuccess = { reportResponse ->
                    reportCache.put(halfYearRange.half, reportResponse)

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            reportData = reportCache.get(yearMonth) ?: ReportUiData.Empty
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message
                        )
                    }
                }
            )
        }
    }

    fun updateReportToPreviousMonth() {
        val yearMonth = _uiState.value.selectYearMonth.minusMonths(1)
        _uiState.update { it.copy(selectYearMonth = yearMonth) }
        fetchReport(yearMonth = yearMonth)
    }

    fun updateReportToNextMonth() {
        if (_uiState.value.selectYearMonth.canMoveToNextReportMonth().not()) {
            _uiState.update { it.copy(isFutureMonthDialogVisible = true) }
            return
        }

        val yearMonth = _uiState.value.selectYearMonth.plusMonths(1)
        _uiState.update { it.copy(selectYearMonth = yearMonth) }
        fetchReport(yearMonth = yearMonth)
    }

    fun dismissFutureMonthDialog() {
        _uiState.update { it.copy(isFutureMonthDialogVisible = false) }
    }
}
