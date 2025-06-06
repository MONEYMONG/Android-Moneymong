package com.moneymong.moneymong

import com.moneymong.moneymong.common.base.State

data class MainState(
    val shouldUpdate: Boolean = false,
) : State
