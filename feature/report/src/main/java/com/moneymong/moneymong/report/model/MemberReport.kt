package com.moneymong.moneymong.report.model

data class MemberReport(
    val name: String,
    val income: Long,
    val expense: Long,
    val incomePercent: Int,
    val expensePercent: Int
)

internal val mockMemberReports: List<MemberReport> = listOf(
    MemberReport(
        name = "장희직",
        income = 5000L,
        expense = 5000L,
        incomePercent = 100,
        expensePercent = 50
    ),
    MemberReport(
        name = "김희직",
        income = 45000L,
        expense = 5000L,
        incomePercent = 90,
        expensePercent = 50
    ),
)