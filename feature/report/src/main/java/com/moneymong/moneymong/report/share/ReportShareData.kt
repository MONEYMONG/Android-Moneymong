package com.moneymong.moneymong.report.share

import com.moneymong.moneymong.report.ReportUiData
import com.moneymong.moneymong.report.model.CategoryReport
import com.moneymong.moneymong.report.model.MemberReport
import java.time.YearMonth

internal data class ReportShareData(
    val title: String,
    val agencyName: String,
    val monthText: String,
    val totalBalance: Long,
    val totalIncome: Long,
    val totalExpense: Long,
    val monthlyIncome: Long,
    val monthlyExpense: Long,
    val monthlyBalance: Long,
    val monthlyIncomePercent: Int,
    val monthlyExpensePercent: Int,
    val members: List<MemberReport>,
    val categories: List<CategoryReport>,
    val fileName: String
) {
    companion object {
        fun from(
            yearMonth: YearMonth,
            reportData: ReportUiData
        ): ReportShareData {
            return ReportShareData(
                title = "머니몽 월간 레포트",
                agencyName = reportData.agencyName,
                monthText = "${yearMonth.year}년 ${yearMonth.monthValue}월",
                totalBalance = reportData.totalReport.balance,
                totalIncome = reportData.totalReport.income,
                totalExpense = reportData.totalReport.expense,
                monthlyIncome = reportData.monthlyReport.income,
                monthlyExpense = reportData.monthlyReport.expense,
                monthlyBalance = reportData.monthlyReport.income - reportData.monthlyReport.expense,
                monthlyIncomePercent = reportData.monthlyReport.incomePercent,
                monthlyExpensePercent = reportData.monthlyReport.expensePercent,
                members = reportData.memberReports,
                categories = reportData.categoryReports,
                fileName = "report-${yearMonth.year}-${yearMonth.monthValue.toString().padStart(2, '0')}.png"
            )
        }
    }
}
