package com.moneymong.moneymong.report.model

data class CategoryReport(
    val name: String,
    val income: Long,
    val expense: Long,
    val incomePercent: Double,
    val expensePercent: Double
) {
    fun toCategoryReportItem(type: AmountType): CategoryReportItem {
        return CategoryReportItem(
            name = name,
            amount = if (type == AmountType.INCOME) income else expense,
            percent = if (type == AmountType.INCOME) incomePercent else expensePercent
        )
    }
}

data class CategoryReportItem(
    val name: String,
    val amount: Long,
    val percent: Double
)

internal val mockCategoryReports = listOf(
    CategoryReport(
        name = "식비",
        income = 200_000L,
        expense = 240_000L,
        incomePercent = 20.0,
        expensePercent = 30.0
    ),
    CategoryReport(
        name = "교통비",
        income = 150_000L,
        expense = 160_000L,
        incomePercent = 15.0,
        expensePercent = 20.0
    ),
    CategoryReport(
        name = "생활비",
        income = 350_000L,
        expense = 200_000L,
        incomePercent = 35.0,
        expensePercent = 25.0
    ),
    CategoryReport(
        name = "의료비",
        income = 100_000L,
        expense = 120_000L,
        incomePercent = 10.0,
        expensePercent = 15.0
    ),
    CategoryReport(
        name = "기타",
        income = 200_000L,
        expense = 80_000L,
        incomePercent = 20.0,
        expensePercent = 10.0
    )
)