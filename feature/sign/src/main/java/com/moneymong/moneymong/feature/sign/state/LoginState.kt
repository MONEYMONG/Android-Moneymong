package com.moneymong.moneymong.feature.sign.state

import com.moneymong.moneymong.android.State

data class LoginState(
    val isClickable: Boolean = false,
    val hasAnyAgency: Boolean? = null,
    val visibleError : Boolean = false,
    val errorMessage : String = ""
) : com.moneymong.moneymong.android.State