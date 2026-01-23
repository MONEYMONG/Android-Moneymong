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
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchLedgerReportUseCase: FetchLedgerReportUseCase
) : ViewModel() {

    private val agencyId = ReportArgs(savedStateHandle).agencyId

    private val _uiState = MutableStateFlow(ReportUiState())
    val uiState: StateFlow<ReportUiState> = _uiState.asStateFlow()

    init {
        fetchReport()
    }

    fun fetchReport() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val yearMonth = _uiState.value.selectYearMonth

            fetchLedgerReportUseCase(
                agencyId = agencyId,
                year = yearMonth.year,
                month = yearMonth.monthValue
            ).fold(
                onSuccess = { reportResponse ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            reportData = reportResponse.toUiData()
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = error.message) }
                }
            )
        }
    }

    fun updateReportToPreviousMonth() {
        _uiState.update { it.copy(selectYearMonth = it.selectYearMonth.minusMonths(1)) }
        fetchReport()
    }

    fun updateReportToNextMonth() {
        _uiState.update { it.copy(selectYearMonth = it.selectYearMonth.plusMonths(1)) }
        fetchReport()
    }
}