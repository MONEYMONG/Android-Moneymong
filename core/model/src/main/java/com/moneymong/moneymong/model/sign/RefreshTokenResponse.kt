package com.moneymong.moneymong.model.sign

data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String
)