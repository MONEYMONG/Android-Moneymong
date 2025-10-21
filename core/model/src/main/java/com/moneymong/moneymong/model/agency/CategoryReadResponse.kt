package com.moneymong.moneymong.model.agency

data class CategoryReadResponse(
    val categories: List<CategoryResponse>,
)

data class CategoryResponse(
    val name: String,
)
