package com.moneymong.moneymong.feature.sign.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.moneymong.moneymong.feature.sign.util.AgencyType
import com.moneymong.moneymong.feature.sign.view.SignUpUniversity

const val signUpUniversityRoute = "signup_university_route"

fun NavController.navigateSignUpUniversity(agencyName: String, agencyType: AgencyType?, navOptions: NavOptions? = null) {
    val routeWithParams = "signup_university_route?agencyName=$agencyName&agencyType=${agencyType?.name}"
    navigate(route = routeWithParams, navOptions = navOptions)
}

fun NavGraphBuilder.signUpUniversity(
    navigateToLedger: () -> Unit,
    navigateToAgency : () -> Unit,
    navigateUp: () -> Unit,
) {

    composable(route = "$signUpUniversityRoute?agencyName={agencyName}&agencyType={agencyType}") { navBackStackEntry ->
        val agencyName = navBackStackEntry.arguments?.getString("agencyName") ?: ""
        val agencyType = navBackStackEntry.arguments?.getString("agencyType")?.let { AgencyType.valueOf(it) }.run { AgencyType.CLUB }

        SignUpUniversity(
            navigateToLedger = navigateToLedger,
            navigateToAgency = navigateToAgency,
            navigateUp = navigateUp,
            agencyName = agencyName,
            agencyType = agencyType
        )
    }
}
