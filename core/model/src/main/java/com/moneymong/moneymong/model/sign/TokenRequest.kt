package com.moneymong.moneymong.model.sign

data class TokenRequest (
    val provider : String,
    val accessToken : String,
    val name: String,
    val code: String
)