package com.moneymong.moneymong.report.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.moneymong.moneymong.report.ReportRoute


const val reportRoute = "report_route"
const val AGENCY_ID = "agencyId"
const val reportRouteWithArgs = "${reportRoute}/{${AGENCY_ID}}"

fun NavController.navigateReport(
    navOptions: NavOptions? = null,
    agencyId: Int
) {
    navigate("${reportRoute}/${agencyId}", navOptions)
}


fun NavGraphBuilder.reportScreen(
    navigateUp: () -> Unit
) {
    composable(
        route = reportRouteWithArgs,
        arguments = listOf(navArgument(AGENCY_ID) {
            type = NavType.IntType
        })
    ) {
        ReportRoute(
            navigateUp = navigateUp
        )
    }
}


internal class ReportArgs(val agencyId: Int) {
    constructor(savedStateHandle: SavedStateHandle) : this(agencyId = checkNotNull(savedStateHandle[AGENCY_ID]))
}