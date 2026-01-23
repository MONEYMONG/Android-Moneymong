package com.moneymong.moneymong.report

import com.moneymong.moneymong.model.ledger.LedgerReportResponse
import com.moneymong.moneymong.report.model.CategoryReport
import com.moneymong.moneymong.report.model.MemberReport
import com.moneymong.moneymong.report.model.MonthlyReport
import com.moneymong.moneymong.report.model.TotalReport
import com.moneymong.moneymong.report.model.mockCategoryReports
import java.time.YearMonth

data class ReportUiState(
    val selectYearMonth: YearMonth = YearMonth.now(),
    val reportData: ReportUiData = ReportUiData.Empty,
    val isLoading: Boolean = true,
    val errorMessage: String? = null
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
): ReportUiData = ReportUiData(
    totalReport = TotalReport(
        balance = this.totalBalance,
        income = this.totalIncome,
        expense = this.totalExpense
    ),
    monthlyReport = MonthlyReport(
        income = this.monthly.first().income,
        expense = this.monthly.first().expense,
        incomePercent = this.monthly.first().incomeShareOfPeriod.toInt(),
        expensePercent = this.monthly.first().expenseShareOfPeriod.toInt()
    ),
    memberReports = this.members.map { member ->
        MemberReport(
            name = member.nickname,
            income = member.income,
            expense = member.expense,
            incomePercent = member.incomeShare.toInt(),
            expensePercent = member.expenseShare.toInt()
        )
    },
//    categoryReports = this.categories.map { category ->
//        CategoryReport(
//            name = category.name,
//            income = category.income,
//            expense = category.expense,
//            incomePercent = category.share,
//            expensePercent = category.share
//        )
//    }
    categoryReports = mockCategoryReports
)