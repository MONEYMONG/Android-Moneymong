package com.moneymong.moneymong.feature.agency.register.complete

import com.moneymong.moneymong.common.base.BaseViewModel
import com.moneymong.moneymong.common.event.Event
import com.moneymong.moneymong.common.event.EventTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AgencyRegisterCompleteViewModel @Inject constructor(
    private val eventTracker: EventTracker
) :
    BaseViewModel<AgencyRegisterCompleteState, AgencyRegisterCompleteSideEffect>(
        AgencyRegisterCompleteState
    ) {

    fun navigateToLedger() {
        eventTracker.logEvent(Event.LEDGER_CLICK)
        eventEmit(sideEffect = AgencyRegisterCompleteSideEffect.NavigateToLedger)
    }

    fun navigateToAgencySearch() =
        eventEmit(sideEffect = AgencyRegisterCompleteSideEffect.NavigateToAgencySearch)

    fun navigateToLedgerManual() {
        eventTracker.logEvent(Event.OPERATION_COST_CLICK)
        eventEmit(sideEffect = AgencyRegisterCompleteSideEffect.NavigateToLedgerManual)
    }
}