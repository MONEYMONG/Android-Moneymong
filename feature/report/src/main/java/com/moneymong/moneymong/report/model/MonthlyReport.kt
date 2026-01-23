package com.moneymong.moneymong.report.model

data class MonthlyReport(
    val income: Long,
    val expense: Long,
    val incomePercent: Int,
    val expensePercent: Int
)