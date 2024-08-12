package com.moneymong.moneymong.feature.agency.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.moneymong.moneymong.feature.agency.register.AgencyRegisterScreen

const val agencyRegisterRoute = "agencyRegister_route"
const val IS_UNIVERSITY_STUDENT = "isUniversityStudent"
const val agencyRegisterRouteWithArgs = "${agencyRegisterRoute}/{${IS_UNIVERSITY_STUDENT}}"

private val arguments = listOf(
    navArgument(IS_UNIVERSITY_STUDENT) {
        type = NavType.BoolType
        defaultValue = false
    }
)

fun NavController.navigateAgencyRegister(
    isUniversityStudent: Boolean,
    navOptions: NavOptions? = null
) {
    navigate("${agencyRegisterRoute}/${isUniversityStudent}", navOptions)
}

fun NavGraphBuilder.agencyRegisterScreen(
    navigateToComplete: () -> Unit,
    navigateUp: () -> Unit
) {
    composable(
        route = agencyRegisterRouteWithArgs,
        arguments = arguments
    ) { backStackEntry ->
        AgencyRegisterScreen(
            navigateToComplete = navigateToComplete,
            navigateUp = navigateUp,
            registrableClubOrCouncil = backStackEntry.arguments?.getBoolean(IS_UNIVERSITY_STUDENT)
                ?: false
        )
    }
}