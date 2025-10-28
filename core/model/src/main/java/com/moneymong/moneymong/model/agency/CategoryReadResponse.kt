package com.moneymong.moneymong.model.agency

data class CategoryReadResponse(
    val agencyId: Long,
    val categories: List<CategoryResponse>,
)

data class CategoryResponse(
    val name: String,
)
