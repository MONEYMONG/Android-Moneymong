package com.moneymong.moneymong.model.sign

data class TokenResponse(
    val accessToken : String,
    val refreshToken : String,
    val loginSuccess : Boolean,
    val schoolInfoExist : Boolean
)
