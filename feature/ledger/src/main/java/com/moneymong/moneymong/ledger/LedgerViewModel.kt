package com.moneymong.moneymong.ledger

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.moneymong.moneymong.common.base.BaseViewModel
import com.moneymong.moneymong.common.error.MoneyMongError
import com.moneymong.moneymong.domain.usecase.agency.FetchAgencyIdUseCase
import com.moneymong.moneymong.domain.usecase.agency.FetchMyAgencyListUseCase
import com.moneymong.moneymong.domain.usecase.agency.SaveAgencyIdUseCase
import com.moneymong.moneymong.domain.usecase.ledger.FetchAgencyExistLedgerUseCase
import com.moneymong.moneymong.domain.usecase.ledger.FetchLedgerTransactionListUseCase
import com.moneymong.moneymong.domain.usecase.ledger.FetchVisibleLedgerOnboardingUseCase
import com.moneymong.moneymong.domain.usecase.ledger.PostDisplayedLedgerOnboardingUseCase
import com.moneymong.moneymong.domain.usecase.member.MemberListUseCase
import com.moneymong.moneymong.domain.usecase.user.FetchUserIdUseCase
import com.moneymong.moneymong.ledger.navigation.LedgerArgs
import com.moneymong.moneymong.ledger.view.LedgerTransactionType
import com.moneymong.moneymong.model.agency.MyAgencyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import java.time.LocalDate
import javax.inject.Inject

@OptIn(OrbitExperimental::class)
@HiltViewModel
class LedgerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchLedgerTransactionListUseCase: FetchLedgerTransactionListUseCase,
    private val fetchAgencyExistLedgerUseCase: FetchAgencyExistLedgerUseCase,
    private val fetchMyAgencyListUseCase: FetchMyAgencyListUseCase,
    private val fetchAgencyIdUseCase: FetchAgencyIdUseCase,
    private val fetchUserIdUseCase: FetchUserIdUseCase,
    private val saveAgencyIdUseCase: SaveAgencyIdUseCase,
    private val fetchMemberListUseCase: MemberListUseCase,
    private val fetchVisibleLedgerOnboardingUseCase: FetchVisibleLedgerOnboardingUseCase,
    private val postDisplayedLedgerOnboardingUseCase: PostDisplayedLedgerOnboardingUseCase
) : BaseViewModel<LedgerState, LedgerSideEffect>(LedgerState()) {

    init {
        checkPostSuccess(LedgerArgs(savedStateHandle).ledgerPostSuccess)
        fetchDefaultInfo()
        fetchMyAgencyList()
        fetchAgencyExistLedger()
        fetchAgencyMemberList()
        fetchLedgerTransactionList()
        fetchVisibleLedgerOnboarding()
    }

    fun fetchDefaultInfo() = blockingIntent {
        val agencyId = fetchAgencyIdUseCase()
        val userId = fetchUserIdUseCase()
        reduce {
            state.copy(
                agencyId = agencyId,
                userId = userId
            )
        }
    }

    fun saveAgencyId(agencyId: Int) = blockingIntent {
        reduce { state.copy(agencyId = agencyId) }
        saveAgencyIdUseCase(agencyId)
    }

    fun fetchAgencyExistLedger() = intent {
        reduce { state.copy(isAgencyExistLoading = true) }
        fetchAgencyExistLedgerUseCase(state.agencyId)
            .onSuccess {
                Log.d("fetchAgencyExistLedger${state.agencyId}",it.toString() )
                reduce {
                    state.copy(
                        isExistLedger = it,
                        visibleError = false
                    )
                }
            }.also { reduce { state.copy(isAgencyExistLoading = false) } }
    }

    fun fetchLedgerTransactionList() = intent {
        if (state.existAgency) {
            reduce { state.copy(isLedgerTransactionLoading = true) }
            fetchLedgerTransactionListUseCase(
                id = state.agencyId,
                startYear = state.startDate.year,
                startMonth = state.startDate.monthValue,
                endYear = state.endDate.year,
                endMonth = state.endDate.monthValue,
                page = 0,
                limit = 100
            ).onSuccess {
                Log.d("fetchLedgerTransactionList${state.existAgency}",it.toString() )

                reduce {
                    state.copy(
                        ledgerTransaction = it,
                        visibleError = false
                    )
                }
            }.onFailure {
                if (it.message.equals("장부가 존재하지 않습니다.")) {
                    reduce { //TODO - 서버 의논 후 변경 예정
                        state.copy(
                            visibleError = false,
                        )
                    }
                } else {
                    reduce {
                        state.copy(
                            visibleError = true,
                            errorMessage = it.message ?: MoneyMongError.UnExpectedError.message
                        )
                    }
                }
            }.also { reduce { state.copy(isLedgerTransactionLoading = false) } }
        }
    }

    fun fetchMyAgencyList() = blockingIntent {
        reduce { state.copy(isMyAgencyLoading = true) }
        fetchMyAgencyListUseCase()
            .onSuccess {
                Log.d("fetchMyAgencyList${state.existAgency}",it.toString() )

                reduce {
                    state.copy(
                        agencyList = it,
                        visibleError = false
                    )
                }
                if (it.isNotEmpty() && state.agencyId == 0) {
                    saveAgencyId(it.first().id)
                }
            }.onFailure {
                reduce {
                    state.copy(
                        visibleError = true,
                        errorMessage = it.message ?: MoneyMongError.UnExpectedError.message
                    )
                }
            }.also { reduce { state.copy(isMyAgencyLoading = false) } }
    }

    fun fetchAgencyMemberList() = blockingIntent {
        if (state.existAgency) {
            reduce { state.copy(isAgencyMemberLoading = true) }
            fetchMemberListUseCase(state.agencyId.toLong())
                .onSuccess {
                    reduce {
                        state.copy(
                            memberList = it.agencyUsers,
                            visibleError = false
                        )
                    }
                }.onFailure {
                    if (it.message.equals("소속에 가입 후 장부를 사용할 수 있습니다.")) {  //TODO - 서버 의논 후 변경 예정
                        reduce {
                            state.copy(
                                visibleError = false
                            )
                        }
                    } else {
                        reduce {
                            state.copy(
                                visibleError = true,
                                errorMessage = it.message
                                    ?: MoneyMongError.UnExpectedError.message
                            )
                        }
                    }
                }.also { reduce { state.copy(isAgencyMemberLoading = false) } }
        }
    }

    private fun fetchVisibleLedgerOnboarding() = intent {
        fetchVisibleLedgerOnboardingUseCase(onboardingType = state.onboardingType)
            .collectLatest { visible ->
                reduce { state.copy(visibleOnboarding = visible) }
            }
    }

    fun reFetchLedgerData(agencyId: Int) {
        saveAgencyId(agencyId)
        fetchAgencyExistLedger()
        fetchAgencyMemberList()
        fetchLedgerTransactionList()
    }

    fun onChangeTransactionType(transactionType: LedgerTransactionType) = intent {
        reduce { state.copy(transactionType = transactionType) }
    }

    fun onChangeSheetState(visible: Boolean) = intent {
        reduce { state.copy(showBottomSheet = visible) }
    }

    fun checkPostSuccess(success: Boolean) {
        if (success) {
            eventEmit(
                LedgerSideEffect.LedgerVisibleSnackbar(
                    message = "성공적으로 기록됐습니다",
                    withDismissAction = true
                )
            )
        }
    }

    fun onChangeVisibleErrorDialog(visible: Boolean) = intent {
        reduce { state.copy(visibleError = visible) }
    }

    fun onClickAgencyChange() = intent {
        reduce { state.copy(sheetType = LedgerSheetType.Agency) }
        postSideEffect(LedgerSideEffect.LedgerOpenSheet)
    }

    fun onClickPeriod() = intent {
        reduce { state.copy(sheetType = LedgerSheetType.DatePicker) }
        postSideEffect(LedgerSideEffect.LedgerOpenSheet)
    }

    fun onClickDateChange(startDate: LocalDate, endDate: LocalDate) {
        intent {
            reduce {
                state.copy(
                    startDate = startDate,
                    endDate = endDate
                )
            }
            postSideEffect(LedgerSideEffect.LedgerCloseSheet)
        }
        fetchLedgerTransactionList()
    }

    fun onDismissOnboarding() = intent {
        postDisplayedLedgerOnboardingUseCase(onboardingType = state.onboardingType)
        reduce { state.copy(visibleOnboarding = false) }
    }


    fun changeAgencyList(filteredAgencyList: List<MyAgencyResponse>) = intent {
        reduce {
            state.copy(
                agencyList = filteredAgencyList
            )
        }
    }
}
