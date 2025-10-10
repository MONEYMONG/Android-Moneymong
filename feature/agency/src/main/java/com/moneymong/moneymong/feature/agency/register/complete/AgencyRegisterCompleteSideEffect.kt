package com.moneymong.moneymong.feature.agency.register.complete

import com.moneymong.moneymong.android.SideEffect

sealed interface AgencyRegisterCompleteSideEffect : SideEffect {
    data object NavigateToAgencySearch : AgencyRegisterCompleteSideEffect
    data object NavigateToLedger : AgencyRegisterCompleteSideEffect
    data object NavigateToLedgerManual : AgencyRegisterCompleteSideEffect
}