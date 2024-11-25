package com.moneymong.moneymong.feature.agency.search

import androidx.compose.foundation.text.input.clearText
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.moneymong.moneymong.common.base.BaseViewModel
import com.moneymong.moneymong.common.error.MoneyMongError
import com.moneymong.moneymong.domain.usecase.agency.FetchAgenciesUseCase
import com.moneymong.moneymong.domain.usecase.agency.FetchAgencyByNameUseCase
import com.moneymong.moneymong.domain.usecase.agency.FetchMyAgencyListUseCase
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
    fetchAgenciesUseCase: FetchAgenciesUseCase,
    private val fetchMyAgencyListUseCase: FetchMyAgencyListUseCase,
    private val fetchMyUniversityUseCase: FetchMyUniversityUseCase,
    private val fetchAgencyByNameUseCase: FetchAgencyByNameUseCase,
) : BaseViewModel<AgencySearchState, AgencySearchSideEffect>(AgencySearchState()) {

    fun navigateToRegister() = intent {
        postSideEffect(AgencySearchSideEffect.NavigateToRegister(isUniversityStudent = state.isUniversityStudent))
    }

    fun navigateToJoin(agencyId: Long) =
        eventEmit(sideEffect = AgencySearchSideEffect.NavigateToAgencyJoin(agencyId))

    val agencies = fetchAgenciesUseCase().map { pagingData ->
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
                        isUniversityStudent = fetchMyUniversityResult.getOrThrow().universityName.isNotBlank(),
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

    fun searchAgency() = intent {
        reduce {
            state.copy(
                isLoading = true,
                isError = false
            )
        }
        fetchAgencyByNameUseCase(agencyName = state.searchTextFieldState.text.toString())
            .onSuccess { agencies ->
                reduce {
                    state.copy(
                        isLoading = false,
                        searchedAgencies = agencies.map { agencyResponse -> agencyResponse.toAgency() }
                    )
                }
            }.onFailure {
                reduce {
                    state.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = it.message ?: MoneyMongError.UnExpectedError.message
                    )
                }
            }
    }

    fun toggleVisibilitySearchBar() = intent {
        if (state.visibleSearchBar) {
            reduce {
                state.copy(
                    visibleSearchBar = state.visibleSearchBar.not(),
                    searchedAgencies = emptyList()
                ).also {
                    clearSearchTextField()
                }
            }
        } else {
            reduce {
                state.copy(visibleSearchBar = state.visibleSearchBar.not())
            }
        }
    }

    fun clearSearchTextField() = intent {
        state.searchTextFieldState.clearText()
    }

    fun changeVisibleWarningDialog(visible: Boolean) = intent {
        reduce {
            state.copy(visibleWarningDialog = visible)
        }
    }

    fun onClickAskFeedback() = eventEmit(AgencySearchSideEffect.FollowAsk)
}