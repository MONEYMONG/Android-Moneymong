package com.moneymong.moneymong.feature.agency.search

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.moneymong.moneymong.common.base.BaseViewModel
import com.moneymong.moneymong.common.error.MoneyMongError
import com.moneymong.moneymong.domain.usecase.agency.FetchMyAgencyListUseCase
import com.moneymong.moneymong.domain.usecase.agency.GetAgenciesUseCase
import com.moneymong.moneymong.domain.usecase.university.FetchMyUniversityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

@HiltViewModel
class AgencySearchViewModel @Inject constructor(
    getAgenciesUseCase: GetAgenciesUseCase,
    private val fetchMyAgencyListUseCase: FetchMyAgencyListUseCase,
    private val fetchMyUniversityUseCase: FetchMyUniversityUseCase
) : BaseViewModel<AgencySearchState, AgencySearchSideEffect>(AgencySearchState()) {

    fun navigateToRegister() = intent {
        postSideEffect(AgencySearchSideEffect.NavigateToRegister(isUniversityStudent = state.isUniversityStudent))
    }

    fun navigateToJoin(agencyId: Long) =
        eventEmit(sideEffect = AgencySearchSideEffect.NavigateToAgencyJoin(agencyId))

    val agencies = getAgenciesUseCase().map { pagingData ->
        pagingData.map {
            it.toAgency()
        }
    }.cachedIn(viewModelScope)

    init {
        getInitialData()
    }

    fun getInitialData() = intent {
        reduce {
            state.copy(
                isLoading = true,
                isError = false
            )
        }
        coroutineScope {
            val fetchMyAgenciesDeferred = async {
                fetchMyAgencyListUseCase()
            }
            val fetchMyUniversityDeferred = async {
                fetchMyUniversityUseCase()
            }

            val fetchMyUniversityResult = fetchMyUniversityDeferred.await()
            val fetchMyAgenciesResult = fetchMyAgenciesDeferred.await()

            if (fetchMyAgenciesResult.isSuccess && fetchMyUniversityResult.isSuccess) {
                reduce {
                    state.copy(
                        isLoading = false,
                        joinedAgencies = fetchMyAgenciesResult.getOrThrow()
                            .map { myAgencyResponse -> myAgencyResponse.toAgency() },
                        isUniversityStudent = fetchMyUniversityResult.getOrThrow().universityName != null,
                    )
                }
            } else {
                reduce {
                    state.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = fetchMyAgenciesResult.exceptionOrNull()?.message
                            ?: fetchMyUniversityResult.exceptionOrNull()?.message
                            ?: MoneyMongError.UnExpectedError.message
                    )
                }
            }
        }
    }

    fun changeVisibleWarningDialog(visible: Boolean) = intent {
        reduce {
            state.copy(visibleWarningDialog = visible)
        }
    }

    fun onClickAskFeedback() = eventEmit(AgencySearchSideEffect.FollowAsk)
}