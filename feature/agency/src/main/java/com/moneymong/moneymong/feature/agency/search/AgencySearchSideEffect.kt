package com.moneymong.moneymong.feature.agency.search

import com.moneymong.moneymong.common.base.SideEffect

sealed interface AgencySearchSideEffect : SideEffect {
    data class NavigateToRegister(val isUniversityStudent: Boolean) : AgencySearchSideEffect
    data class NavigateToAgencyJoin(val agencyId: Long) : AgencySearchSideEffect
    data object FollowAsk : AgencySearchSideEffect
}