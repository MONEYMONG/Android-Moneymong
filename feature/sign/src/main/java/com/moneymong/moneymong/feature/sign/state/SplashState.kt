package com.moneymong.moneymong.feature.sign.state

import com.moneymong.moneymong.android.State

data class SplashState(
    val startAnimation: Boolean = false,
    val isTokenValid: Boolean? = null
) : com.moneymong.moneymong.android.State