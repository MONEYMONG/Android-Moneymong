package com.moneymong.moneymong.report.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions


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

}