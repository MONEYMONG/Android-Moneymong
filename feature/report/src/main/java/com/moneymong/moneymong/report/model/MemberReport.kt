package com.moneymong.moneymong.report.model

data class MemberReport(
    val name: String,
    val income: Long,
    val expense: Long,
    val incomePercent: Double,
    val expensePercent: Double
)

internal val mockMemberReports: List<MemberReport> = listOf(
    MemberReport(
        name = "장희직",
        income = 5000L,
        expense = 5000L,
        incomePercent = 100.0,
        expensePercent = 50.0
    ),
    MemberReport(
        name = "김희직",
        income = 45000L,
        expense = 5000L,
        incomePercent = 90.0,
        expensePercent = 50.0
    ),
)