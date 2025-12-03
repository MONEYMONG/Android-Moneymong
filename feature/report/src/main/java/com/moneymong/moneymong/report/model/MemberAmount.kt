package com.moneymong.moneymong.report.model

data class MemberAmount(
    val name: String,
    val income: Int,
    val expense: Int,
    val incomePercent: Float,
    val expensePercent: Float
)

internal val mockMemberAmounts: List<MemberAmount> = listOf(
    MemberAmount(
        "장희직",
        5000,
        5000,
        100f,
        50f
    ),
    MemberAmount(
        "김희직",
        45000,
        5000,
        90f,
        50f
    ),
)