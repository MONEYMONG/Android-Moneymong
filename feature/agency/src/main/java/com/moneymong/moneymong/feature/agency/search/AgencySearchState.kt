package com.moneymong.moneymong.feature.agency.search

import androidx.compose.foundation.text.input.TextFieldState
import com.moneymong.moneymong.android.State

data class AgencySearchState(
    val joinedAgencies: List<Agency> = emptyList(),
    val searchedAgencies: List<Agency> = emptyList(),
    val isSearched: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val visibleWarningDialog: Boolean = false,
    val isUniversityStudent: Boolean = false,
    val visibleSearchBar: Boolean = false,
    val searchTextFieldState: TextFieldState = TextFieldState(),
) : State {

    val joinedAgenciesIds: List<Long>
        get() = joinedAgencies.map { it.id }
}