package com.moneymong.moneymong.model.ledger

enum class FundType(val sign: String) {
    NONE(""),
    INCOME("+"),
    EXPENSE("-")
}