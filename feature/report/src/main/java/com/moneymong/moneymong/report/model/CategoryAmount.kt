package com.moneymong.moneymong.report.model

data class CategoryAmount(
    val name: String,
    val income: Int,
    val expense: Int,
    val incomePercent: Float,
    val expensePercent: Float
) {
    fun toCategoryAmountItem(type: AmountType): CategoryAmountItem {
        return CategoryAmountItem(
            name = name,
            amount = if (type == AmountType.INCOME) income else expense,
            percent = if (type == AmountType.INCOME) incomePercent else expensePercent
        )
    }
}

data class CategoryAmountItem(
    val name: String,
    val amount: Int,
    val percent: Float
)

internal val mockCategoryAmounts = listOf(
    CategoryAmount(
        name = "식비",
        income = 200_000,
        expense = 240_000,
        incomePercent = 20f,
        expensePercent = 30f
    ),
    CategoryAmount(
        name = "교통비",
        income = 150_000,
        expense = 160_000,
        incomePercent = 15f,
        expensePercent = 20f
    ),
    CategoryAmount(
        name = "생활비",
        income = 350_000,
        expense = 200_000,
        incomePercent = 35f,
        expensePercent = 25f
    ),
    CategoryAmount(
        name = "의료비",
        income = 100_000,
        expense = 120_000,
        incomePercent = 10f,
        expensePercent = 15f
    ),
    CategoryAmount(
        name = "기타",
        income = 200_000,
        expense = 80_000,
        incomePercent = 20f,
        expensePercent = 10f
    )
)