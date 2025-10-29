package com.moneymong.moneymong.feature.agency.join.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.moneymong.moneymong.feature.agency.join.AgencyJoinScreen

const val agencyJoinRoute = "agencyJoin_route"
const val IS_BACK_BUTTON = "IsBackButton"
const val agencyJoinRouteWithArgs = "$agencyJoinRoute/{${IS_BACK_BUTTON}}"

fun NavController.navigateAgencyJoin(
    navOptions: NavOptions? = null,
    isBackButton: Boolean = false
) {
    navigate("${agencyJoinRoute}/${isBackButton}", navOptions)
}

fun NavGraphBuilder.agencyJoinScreen(
    navigateToComplete: () -> Unit,
    navigateUp: () -> Unit,
) {
    composable(
        route = agencyJoinRouteWithArgs,
        arguments = listOf(navArgument(IS_BACK_BUTTON) {
            type = NavType.BoolType
            defaultValue = false
        })
    ) {
        AgencyJoinScreen(
            navigateToComplete = navigateToComplete,
            navigateUp = navigateUp,
        )
    }
}

internal class AgencyJoinArgs(val isBackButton: Boolean) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        isBackButton = checkNotNull(savedStateHandle[IS_BACK_BUTTON])
    )
}