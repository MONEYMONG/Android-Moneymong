package com.moneymong.moneymong.model.member

data class AgencyUser(
    val id: Long,
    val userId: Long,
    val nickname: String,
    val agencyUserRole: String
)