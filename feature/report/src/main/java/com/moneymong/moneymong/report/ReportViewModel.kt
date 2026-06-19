package com.moneymong.moneymong.report

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneymong.moneymong.common.error.MoneyMongError
import com.moneymong.moneymong.domain.usecase.ledger.FetchLedgerReportUseCase
import com.moneymong.moneymong.report.navigation.ReportArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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

    private var fetchReportJob: Job? = null

    init {
        fetchReport()
    }

    fun fetchReport() {
        fetchReport(yearMonth = _uiState.value.selectYearMonth)
    }

    fun refreshReport() {
        val yearMonth = YearMonth.now()
        reportCache.clear()
        _uiState.update { it.copy(selectYearMonth = yearMonth) }
        fetchReport(
            yearMonth = yearMonth,
            isRefresh = true
        )
    }

    private fun fetchReport(
        yearMonth: YearMonth,
        isRefresh: Boolean = false
    ) {
        fetchReportJob?.cancel()
        fetchReportJob = null

        val cachedReport = reportCache.get(yearMonth)

        if (cachedReport != null) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    isRefreshing = false,
                    errorMessage = null,
                    reportData = cachedReport
                )
            }
            return
        }

        val halfYearRange = yearMonth.toReportHalfYearRange()

        fetchReportJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = isRefresh.not(),
                    isRefreshing = isRefresh,
                    errorMessage = null
                )
            }

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
                            isRefreshing = false,
                            reportData = reportCache.get(yearMonth) ?: ReportUiData.Empty
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isRefreshing = false,
                            errorMessage = error.message ?: MoneyMongError.UnExpectedError.message
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
