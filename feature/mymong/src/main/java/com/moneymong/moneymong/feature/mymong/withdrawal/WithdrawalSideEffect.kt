package com.moneymong.moneymong.feature.mymong.withdrawal

import com.moneymong.moneymong.android.SideEffect

sealed interface WithdrawalSideEffect : SideEffect {
    data object NavigateToLogin : WithdrawalSideEffect
    data object NavigateUp : WithdrawalSideEffect
}