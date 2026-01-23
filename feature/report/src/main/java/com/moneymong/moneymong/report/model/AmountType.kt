package com.moneymong.moneymong.report.model

enum class AmountType(val label: String, val symbol: String) {
    INCOME(label = "수입", symbol = "+"),
    EXPENSE(label = "지출", symbol = "-");
}