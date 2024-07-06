package com.moneymong.moneymong.ledger

import com.moneymong.moneymong.common.base.State
import com.moneymong.moneymong.ledger.view.LedgerTransactionType
import com.moneymong.moneymong.model.agency.MyAgencyResponse
import com.moneymong.moneymong.model.ledger.LedgerDetail
import com.moneymong.moneymong.model.ledger.LedgerTransactionListResponse
import com.moneymong.moneymong.model.ledger.OnboardingType
import com.moneymong.moneymong.model.member.AgencyUser
import java.time.LocalDate

enum class LedgerSheetType {
    Agency,
    DatePicker
}

data class LedgerState(
    val isAgencyExistLoading: Boolean = true,
    val isLedgerTransactionLoading: Boolean = true,
    val isMyAgencyLoading: Boolean = true,
    val isAgencyMemberLoading: Boolean = true,
    val agencyId: Int = 0,
    val userId: Int = 0,
    val isExistLedger: Boolean = false,
    val showBottomSheet: Boolean = false,
    val ledgerTransaction: LedgerTransactionListResponse? = null,
    val transactionType: LedgerTransactionType = LedgerTransactionType.전체,
    val startDate: LocalDate = LocalDate.now().minusMonths(6),
    val endDate: LocalDate = LocalDate.now(),
    val visibleError: Boolean = false,
    val agencyList: List<MyAgencyResponse> = emptyList(),
    val memberList: List<AgencyUser> = emptyList(),
    val errorMessage: String = "",
    val sheetType: LedgerSheetType = LedgerSheetType.DatePicker,
    val visibleOnboarding: Boolean = false,
    val isRefreshing: Boolean = false,
) : State {

    val filterTransactionList: List<LedgerDetail>
        get() = if (transactionType == LedgerTransactionType.전체) {
            ledgerTransaction?.ledgerInfoViewDetails.orEmpty()
        } else {
            ledgerTransaction?.ledgerInfoViewDetails?.filter { it.fundType == transactionType.type }
                .orEmpty()
        }

    val hasTransaction: Boolean
        get() = filterTransactionList.isNotEmpty()

    val existAgency: Boolean
        get() = agencyList.isNotEmpty()

    val currentAgency: MyAgencyResponse?
        get() = agencyList.find { it.id == agencyId }

    val isStaff: Boolean
        get() = memberList.find { it.userId.toInt() == userId }?.agencyUserRole.orEmpty() == "STAFF"

    val isLoading: Boolean
        get() = isAgencyExistLoading || isLedgerTransactionLoading || isMyAgencyLoading || isAgencyMemberLoading

    val onboardingType: OnboardingType
        get() = if (isStaff) OnboardingType.STAFF else OnboardingType.MEMBER
}
