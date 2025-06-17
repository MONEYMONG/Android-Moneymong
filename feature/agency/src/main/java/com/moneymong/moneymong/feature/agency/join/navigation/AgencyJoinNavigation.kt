package com.moneymong.moneymong.feature.agency.join.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.moneymong.moneymong.feature.agency.join.AgencyJoinScreen

const val agencyJoinRoute = "agencyJoin_route"

fun NavController.navigateAgencyJoin(navOptions: NavOptions? = null) {
    navigate(agencyJoinRoute, navOptions)
}

fun NavGraphBuilder.agencyJoinScreen(
    navigateToComplete: () -> Unit,
    navigateUp: () -> Unit,
) {
    composable(route = agencyJoinRoute) {
        AgencyJoinScreen(
            navigateToComplete = navigateToComplete,
            navigateUp = navigateUp,
        )
    }
}