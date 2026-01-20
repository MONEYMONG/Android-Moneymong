package com.moneymong.moneymong.report

import com.moneymong.moneymong.model.ledger.LedgerReportResponse
import com.moneymong.moneymong.report.model.CategoryReport
import com.moneymong.moneymong.report.model.MemberReport
import com.moneymong.moneymong.report.model.MonthlyReport
import com.moneymong.moneymong.report.model.TotalReport
import java.time.YearMonth

data class ReportUiState(
    val selectYearMonth: YearMonth = YearMonth.now(),
    val reportData: ReportUiData? = null,
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

data class ReportUiData(
    val totalReport: TotalReport,
    val monthlyReport: MonthlyReport,
    val memberReports: List<MemberReport>,
    val categoryReports: List<CategoryReport>
)

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
        incomePercent = this.monthly.first().incomeShareOfPeriod,
        expensePercent = this.monthly.first().expenseShareOfPeriod
    ),
    memberReports = this.members.map { member ->
        MemberReport(
            name = member.nickname,
            income = member.income,
            expense = member.expense,
            incomePercent = member.incomeShare,
            expensePercent = member.expenseShare
        )
    },
    categoryReports = this.categories.map { category ->
        CategoryReport(
            name = category.name,
            income = category.income,
            expense = category.expense,
            incomePercent = category.share,
            expensePercent = category.share
        )
    }
)