package com.moneymong.moneymong

import com.moneymong.moneymong.android.State

data class MainState(
    val shouldUpdate: Boolean = false
): State
