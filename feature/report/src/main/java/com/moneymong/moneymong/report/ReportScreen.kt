package com.moneymong.moneymong.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moneymong.moneymong.design_system.component.indicator.LoadingItem
import com.moneymong.moneymong.design_system.component.indicator.MDSRefreshIndicator
import com.moneymong.moneymong.design_system.error.ErrorDialog
import com.moneymong.moneymong.design_system.error.ErrorScreen
import com.moneymong.moneymong.design_system.theme.Gray01
import com.moneymong.moneymong.design_system.theme.MMHorizontalSpacing
import com.moneymong.moneymong.design_system.theme.White
import com.moneymong.moneymong.report.component.ReportTopBar
import com.moneymong.moneymong.report.share.ReportShareLauncher
import com.moneymong.moneymong.report.view.CategoryReportView
import com.moneymong.moneymong.report.view.MemberReportView
import com.moneymong.moneymong.report.view.ReportMonthly
import com.moneymong.moneymong.report.view.ReportSummary
import java.time.YearMonth
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReportRoute(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    viewModel: ReportViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var visibleShareErrorDialog by remember { mutableStateOf(false) }

    if (visibleShareErrorDialog) {
        ErrorDialog(
            message = "레포트를 공유할 수 없어요",
            description = "잠시 후 다시 시도해주세요.",
            onConfirm = { visibleShareErrorDialog = false }
        )
    }

    if (uiState.isFutureMonthDialogVisible) {
        ErrorDialog(
            message = "조회할 수 없는 기간이에요",
            description = "현재 달 이후의 레포트는 조회할 수 없어요.",
            onConfirm = viewModel::dismissFutureMonthDialog
        )
    }

    when {
        uiState.errorMessage != null -> {
            ErrorScreen(
                modifier = modifier,
                message = uiState.errorMessage,
                onRetry = viewModel::fetchReport
            )
        }

        else -> {
            ReportScreen(
                modifier = modifier,
                navigateUp = navigateUp,
                selectYearMonth = uiState.selectYearMonth,
                reportData = uiState.reportData,
                isLoading = uiState.isLoading,
                isRefreshing = uiState.isRefreshing,
                refreshReport = viewModel::refreshReport,
                shareReport = {
                    if (uiState.isLoading) return@ReportScreen

                    coroutineScope.launch {
                        runCatching {
                            ReportShareLauncher.launch(
                                context = context,
                                yearMonth = uiState.selectYearMonth,
                                reportData = uiState.reportData
                            )
                        }.onFailure {
                            visibleShareErrorDialog = true
                        }
                    }
                },
                updateReportToPreviousMonth = viewModel::updateReportToPreviousMonth,
                updateReportToNextMonth = viewModel::updateReportToNextMonth
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ReportScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    selectYearMonth: YearMonth,
    reportData: ReportUiData,
    isLoading: Boolean,
    isRefreshing: Boolean,
    refreshReport: () -> Unit,
    shareReport: () -> Unit,
    updateReportToPreviousMonth: () -> Unit,
    updateReportToNextMonth: () -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = refreshReport
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = Gray01)
        ) {
            ReportTopBar(
                modifier = Modifier.fillMaxWidth(),
                isShareEnabled = isLoading.not(),
                onShare = shareReport,
                onClose = navigateUp
            )
            if (isLoading.not()) {
                ReportSummary(
                    modifier = Modifier.padding(horizontal = MMHorizontalSpacing),
                    balance = reportData.totalReport.balance,
                    income = reportData.totalReport.income,
                    expense = reportData.totalReport.expense
                )
                Spacer(modifier = Modifier.height(20.dp))
            }

            Column(
                modifier = Modifier
                    .background(color = White)
                    .padding(horizontal = MMHorizontalSpacing)
            ) {
                ReportMonthly(
                    yearMonth = selectYearMonth,
                    monthlyIncome = reportData.monthlyReport.income,
                    monthlyExpense = reportData.monthlyReport.expense,
                    monthlyIncomePercent = reportData.monthlyReport.incomePercent,
                    monthlyExpensePercent = reportData.monthlyReport.expensePercent,
                    isLoading = isLoading,
                    updateToPreviousMonth = updateReportToPreviousMonth,
                    updateToNextMonth = updateReportToNextMonth
                )
                Spacer(modifier = Modifier.height(32.dp))
                if (isLoading) {
                    LoadingItem(modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(20.dp))
                } else {
                    if (reportData.memberReports.isNotEmpty()) {
                        MemberReportView(memberReports = reportData.memberReports)
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                    if (reportData.categoryReports.isNotEmpty()) {
                        CategoryReportView(
                            selectMonth = selectYearMonth.monthValue,
                            categoryReports = reportData.categoryReports
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
        MDSRefreshIndicator(
            pullRefreshState = pullRefreshState,
            isRefreshing = isRefreshing
        )
    }
}

@Preview
@Composable
private fun ReportScreenPreview() {
    ReportScreen(
        navigateUp = {},
        selectYearMonth = YearMonth.now(),
        reportData = ReportUiData.Empty,
        isLoading = false,
        isRefreshing = false,
        refreshReport = {},
        shareReport = {},
        updateReportToPreviousMonth = {},
        updateReportToNextMonth = {}
    )
}
