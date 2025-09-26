package com.moneymong.moneymong.feature.agency.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.moneymong.moneymong.feature.agency.register.AgencyRegisterScreen

const val agencyRegisterRoute = "agencyRegister_route"
const val VISIBLE_INVITE_CODE = "VisibleInviteCode"
const val agencyRegisterRouteWithArgs = "${agencyRegisterRoute}/{${VISIBLE_INVITE_CODE}}"

fun NavController.navigateAgencyRegister(
    navOptions: NavOptions? = null,
    visibleInviteCode: Boolean = false,
) {
    navigate("${agencyRegisterRoute}/${visibleInviteCode}", navOptions)
}

fun NavGraphBuilder.agencyRegisterScreen(
    navigateToLedger: () -> Unit
) {
    composable(
        route = agencyRegisterRouteWithArgs,
        arguments = listOf(navArgument(VISIBLE_INVITE_CODE) {
            type = NavType.BoolType
            defaultValue = false
        })
    ) {
        AgencyRegisterScreen(
            navigateToLedger = navigateToLedger,
        )
    }
}


internal class AgencyRegisterArgs(val visibleInviteCode: Boolean) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        visibleInviteCode = checkNotNull(savedStateHandle[VISIBLE_INVITE_CODE])
    )
}