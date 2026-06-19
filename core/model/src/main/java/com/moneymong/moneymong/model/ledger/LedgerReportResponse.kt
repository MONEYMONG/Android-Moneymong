package com.moneymong.moneymong.model.ledger

data class LedgerReportResponse(
    val agencyId: Int,
    val agencyName: String,
    val period: ReportPeriod,
    val totalIncome: Long,
    val totalExpense: Long,
    val totalBalance: Long,
    val monthly: List<ReportMonthly>
)

data class ReportPeriod(
    val startYear: Int,
    val startMonth: Int,
    val endYear: Int,
    val endMonth: Int
)

data class ReportMonthly(
    val year: Int,
    val month: Int,
    val income: Long,
    val expense: Long,
    val netAmount: Long,
    val incomeShareOfPeriod: Double,
    val expenseShareOfPeriod: Double,
    val members: List<ReportMember>,
    val categories: List<ReportCategory>
)

data class ReportMember(
    val userId: Int,
    val nickname: String,
    val income: Long,
    val expense: Long,
    val incomeShare: Double,
    val expenseShare: Double
)

data class ReportCategory(
    val name: String,
    val income: Long,
    val expense: Long,
    val incomeShare: Double,
    val expenseShare: Double
)
