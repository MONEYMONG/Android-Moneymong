package com.moneymong.moneymong.model.member

data class UpdateAuthorRequest(
    val role: String,
    val userId: Long
)