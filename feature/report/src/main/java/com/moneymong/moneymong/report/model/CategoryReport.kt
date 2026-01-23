package com.moneymong.moneymong.report.model

data class CategoryReport(
    val name: String,
    val income: Long,
    val expense: Long,
    val incomePercent: Int,
    val expensePercent: Int
) {
    fun toCategoryReportItem(type: AmountType): CategoryReportItem {
        return CategoryReportItem(
            name = name,
            amount = if (type == AmountType.INCOME) income else expense,
            percent = if (type == AmountType.INCOME) incomePercent else expensePercent
        )
    }
}

fun List<CategoryReport>.toCategoryReportItemsWithSort(
    type: AmountType
): List<CategoryReportItem> {
    return this.map { categoryReport -> categoryReport.toCategoryReportItem(type) }.sortedByDescending { it.amount }
}

data class CategoryReportItem(
    val name: String,
    val amount: Long,
    val percent: Int
)

internal val mockCategoryReports = listOf(
    CategoryReport(
        name = "식비",
        income = 200_000L,
        expense = 240_000L,
        incomePercent = 20,
        expensePercent = 30
    ),
    CategoryReport(
        name = "교통비",
        income = 150_000L,
        expense = 160_000L,
        incomePercent = 15.321321.toInt(),
        expensePercent = 20.543543.toInt()
    ),
    CategoryReport(
        name = "생활비",
        income = 350_000L,
        expense = 200_000L,
        incomePercent = 35.321.toInt(),
        expensePercent = 25.432.toInt()
    ),
    CategoryReport(
        name = "의료비",
        income = 100_000L,
        expense = 120_000L,
        incomePercent = 10,
        expensePercent = 15
    ),
    CategoryReport(
        name = "기타",
        income = 200_000L,
        expense = 80_000L,
        incomePercent = 20,
        expensePercent = 10
    )
)