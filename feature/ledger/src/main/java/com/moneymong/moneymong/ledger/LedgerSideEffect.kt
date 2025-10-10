package com.moneymong.moneymong.ledger

import com.moneymong.moneymong.android.SideEffect

sealed class LedgerSideEffect : SideEffect {
    data object LedgerOpenSheet : LedgerSideEffect()
    data object LedgerCloseSheet : LedgerSideEffect()
    data object LedgerNavigateToLedgerManual : LedgerSideEffect()
    data object LedgerFetchRetry : LedgerSideEffect()
    data class LedgerNavigateToLedgerDetail(val id: Int): LedgerSideEffect()
    data class LedgerSelectedAgencyChange(val agencyId: Int): LedgerSideEffect()
    data class LedgerVisibleSnackbar(val message: String, val withDismissAction: Boolean): LedgerSideEffect()
}
