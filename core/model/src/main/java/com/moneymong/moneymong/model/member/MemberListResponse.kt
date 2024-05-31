package com.moneymong.moneymong.model.member

data class MemberListResponse(
    val agencyUsers: List<AgencyUser>,
    val count: Int
)