package com.moneymong.moneymong.model.agency

import com.google.gson.annotations.SerializedName

data class AgencyRegisterRequest(
    val name: String,
    @SerializedName("agencyType")
    val type: String
)