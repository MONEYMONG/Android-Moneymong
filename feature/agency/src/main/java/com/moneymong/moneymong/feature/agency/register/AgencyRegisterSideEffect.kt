package com.moneymong.moneymong.feature.agency.register

import com.moneymong.moneymong.android.SideEffect

sealed interface AgencyRegisterSideEffect : SideEffect {
    data object NavigateToLedger : AgencyRegisterSideEffect
}