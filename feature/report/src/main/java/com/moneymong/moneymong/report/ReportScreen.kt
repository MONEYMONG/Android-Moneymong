package com.moneymong.moneymong.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moneymong.moneymong.design_system.error.ErrorScreen
import com.moneymong.moneymong.design_system.theme.Gray01
import com.moneymong.moneymong.design_system.theme.MMHorizontalSpacing
import com.moneymong.moneymong.design_system.theme.White
import com.moneymong.moneymong.report.component.ReportTopBar
import com.moneymong.moneymong.report.view.CategoryReportView
import com.moneymong.moneymong.report.view.MemberReportView
import com.moneymong.moneymong.report.view.ReportMonthly
import com.moneymong.moneymong.report.view.ReportSummary
import java.time.YearMonth


@Composable
fun ReportRoute(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    viewModel: ReportViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

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
                updateReportToPreviousMonth = viewModel::updateReportToPreviousMonth,
                updateReportToNextMonth = viewModel::updateReportToNextMonth
            )
        }
    }
}


@Composable
private fun ReportScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    selectYearMonth: YearMonth,
    reportData: ReportUiData,
    updateReportToPreviousMonth: () -> Unit,
    updateReportToNextMonth: () -> Unit
) {

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .background(color = Gray01)
    ) {
        ReportTopBar(
            modifier = Modifier.fillMaxWidth(),
            onClose = navigateUp
        )
        ReportSummary(
            modifier = Modifier.padding(horizontal = MMHorizontalSpacing),
            balance = reportData.totalReport.balance,
            income = reportData.totalReport.income,
            expense = reportData.totalReport.expense
        )
        Spacer(modifier = Modifier.height(20.dp))

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
                updateToPreviousMonth = updateReportToPreviousMonth,
                updateToNextMonth = updateReportToNextMonth
            )
            Spacer(modifier = Modifier.height(32.dp))
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

@Preview
@Composable
private fun ReportScreenPreview() {
    ReportScreen(
        navigateUp = {},
        selectYearMonth = YearMonth.now(),
        reportData = ReportUiData.Empty,
        updateReportToPreviousMonth = {},
        updateReportToNextMonth = {}
    )
}