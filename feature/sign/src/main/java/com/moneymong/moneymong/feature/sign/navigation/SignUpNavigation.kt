package com.moneymong.moneymong.feature.sign.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.moneymong.moneymong.feature.sign.SignUpScreen
import com.moneymong.moneymong.feature.sign.util.AgencyType

const val signUpRoute = "signup_route"

fun NavController.navigateSignUp(navOptions: NavOptions? = null) {
    navigate(route = signUpRoute, navOptions = navOptions)
}

fun NavGraphBuilder.signUpScreen(
    navigateToLedger: () -> Unit,
    navigateToSignUniversity : (String, AgencyType?) -> Unit,
    navigateToAgency : () -> Unit,
    navigateUp: () -> Unit
) {
    composable(route = signUpRoute) {
        SignUpScreen(
            navigateToLedger = navigateToLedger,
            navigateToSignUpUniversity = navigateToSignUniversity,
            navigateToAgency = navigateToAgency,
            navigateUp = navigateUp
        )
    }
}
