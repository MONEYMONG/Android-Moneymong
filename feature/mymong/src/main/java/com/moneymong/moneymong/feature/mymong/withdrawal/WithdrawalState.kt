package com.moneymong.moneymong.feature.mymong.withdrawal

import com.moneymong.moneymong.android.State

data class WithdrawalState(
    val isAgreed: Boolean = false,
    val errorMessage: String = "",
    val visibleWithdrawalDialog: Boolean = false,
    val visibleErrorDialog: Boolean = false,
) : State