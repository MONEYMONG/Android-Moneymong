package com.moneymong.moneymong.model.agency

import com.google.gson.annotations.SerializedName

data class AgenciesGetResponse(
    @SerializedName("agencyList")
    val agencies: List<AgencyGetResponse>
)

data class AgencyGetResponse(
    val id: Long,
    val name: String,
    val headCount: Int,
    val type: String
)