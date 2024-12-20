package com.moneymong.moneymong.model.sign

data class UniversitiesResponse(
    val universities: List<University>
)

data class University(
    val id: Int,
    val schoolName: String
)