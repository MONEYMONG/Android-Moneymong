package com.moneymong.moneymong.feature.sign.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.moneymong.moneymong.feature.sign.LoginScreen

const val loginRoute = "login_route"

fun NavController.navigateLogin(
    navOptions: NavOptions? = navOptions {
        popUpTo(graph.id) { inclusive = true }
    }
) {
    navigate(loginRoute, navOptions)
}

fun NavGraphBuilder.loginScreen(
    navigateToLedger: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToAgencyRegister: () -> Unit,
) {
    composable(route = loginRoute) {
        LoginScreen(
            navigateToLedger = navigateToLedger,
            navigateToLogin = navigateToLogin,
            navigateToAgencyRegister = navigateToAgencyRegister,
        )
    }
}

