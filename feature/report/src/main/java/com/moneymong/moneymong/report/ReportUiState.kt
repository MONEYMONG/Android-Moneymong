package com.moneymong.moneymong.report

import com.moneymong.moneymong.model.ledger.LedgerReportResponse
import com.moneymong.moneymong.model.ledger.ReportMonthly
import com.moneymong.moneymong.report.model.CategoryReport
import com.moneymong.moneymong.report.model.MemberReport
import com.moneymong.moneymong.report.model.MonthlyReport
import com.moneymong.moneymong.report.model.TotalReport
import java.time.YearMonth

data class ReportUiState(
    val selectYearMonth: YearMonth = YearMonth.now(),
    val reportData: ReportUiData = ReportUiData.Empty,
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val isFutureMonthDialogVisible: Boolean = false
)

data class ReportUiData(
    val totalReport: TotalReport,
    val monthlyReport: MonthlyReport,
    val memberReports: List<MemberReport>,
    val categoryReports: List<CategoryReport>
) {
    companion object {
        val Empty = ReportUiData(
            totalReport = TotalReport(
                balance = 0L,
                income = 0L,
                expense = 0L
            ),
            monthlyReport = MonthlyReport(
                income = 0L,
                expense = 0L,
                incomePercent = 0,
                expensePercent = 0
            ),
            memberReports = emptyList(),
            categoryReports = emptyList()
        )
    }
}

internal fun LedgerReportResponse.toUiData(
    monthlyReport: ReportMonthly
): ReportUiData {
    return ReportUiData(
        totalReport = TotalReport(
            balance = this.totalBalance,
            income = this.totalIncome,
            expense = this.totalExpense
        ),
        monthlyReport = MonthlyReport(
            income = monthlyReport.income,
            expense = monthlyReport.expense,
            incomePercent = monthlyReport.incomeShareOfPeriod.toInt(),
            expensePercent = monthlyReport.expenseShareOfPeriod.toInt()
        ),
        memberReports = monthlyReport.members.map { member ->
            MemberReport(
                name = member.nickname,
                income = member.income,
                expense = member.expense,
                incomePercent = member.incomeShare.toInt(),
                expensePercent = member.expenseShare.toInt()
            )
        },
        categoryReports = monthlyReport.categories.map { category ->
            CategoryReport(
                name = category.name,
                income = category.income,
                expense = category.expense,
                incomePercent = category.incomeShare.toInt(),
                expensePercent = category.expenseShare.toInt()
            )
        }
    )
}
