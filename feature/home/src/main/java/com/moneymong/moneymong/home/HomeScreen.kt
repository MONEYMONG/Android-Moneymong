package com.moneymong.moneymong.home

import android.app.Activity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.moneymong.moneymong.design_system.error.ErrorDialog
import com.moneymong.moneymong.feature.agency.join.navigation.agencyCompleteScreen
import com.moneymong.moneymong.feature.agency.join.navigation.agencyJoinScreen
import com.moneymong.moneymong.feature.agency.join.navigation.navigateAgencyJoin
import com.moneymong.moneymong.feature.agency.join.navigation.navigateAgencyJoinComplete
import com.moneymong.moneymong.feature.agency.navigation.agencyRegisterCompleteScreen
import com.moneymong.moneymong.feature.agency.navigation.agencyRegisterScreen
import com.moneymong.moneymong.feature.agency.navigation.agencyRoute
import com.moneymong.moneymong.feature.agency.navigation.agencyScreen
import com.moneymong.moneymong.feature.agency.navigation.navigateAgency
import com.moneymong.moneymong.feature.agency.navigation.navigateAgencyRegister
import com.moneymong.moneymong.feature.mymong.navigation.myMongNavGraph
import com.moneymong.moneymong.feature.mymong.navigation.navigatePrivacyPolicy
import com.moneymong.moneymong.feature.mymong.navigation.navigateTermsOfUse
import com.moneymong.moneymong.feature.mymong.navigation.navigateWithdrawal
import com.moneymong.moneymong.feature.sign.navigation.loginScreen
import com.moneymong.moneymong.feature.sign.navigation.navigateLogin
import com.moneymong.moneymong.feature.sign.navigation.navigateSignUpUniversity
import com.moneymong.moneymong.feature.sign.navigation.signCompleteScreen
import com.moneymong.moneymong.feature.sign.navigation.signUpScreen
import com.moneymong.moneymong.feature.sign.navigation.signUpUniversity
import com.moneymong.moneymong.feature.sign.navigation.splashRoute
import com.moneymong.moneymong.feature.sign.navigation.splashScreen
import com.moneymong.moneymong.home.navigation.rememberHomeNavigator
import com.moneymong.moneymong.home.view.HomeBottomBarView
import com.moneymong.moneymong.ledger.navigation.ledgerScreen
import com.moneymong.moneymong.ledger.navigation.navigateLedger
import com.moneymong.moneymong.ledgerdetail.navigation.ledgerDetailScreen
import com.moneymong.moneymong.ledgerdetail.navigation.navigateLedgerDetail
import com.moneymong.moneymong.ledgermanual.navigation.ledgerManualScreen
import com.moneymong.moneymong.ledgermanual.navigation.navigateLedgerManual
import com.moneymong.moneymong.ocr.navigation.ocrScreen
import com.moneymong.moneymong.ocr_detail.navigation.navigateOCRDetail
import com.moneymong.moneymong.ocr_detail.navigation.ocrDetailScreen
import com.moneymong.moneymong.ocr_result.navigation.navigateOCRResult
import com.moneymong.moneymong.ocr_result.navigation.ocrResultScreen
import com.moneymong.moneymong.ui.pxToDp

@Composable
fun HomeScreen(
    expired: Boolean,
    onChangeExpired: (Boolean) -> Unit
) {
    val homeNavigator = rememberHomeNavigator()
    val homeNavController = homeNavigator.navHostController

    val view = LocalView.current
    val window = (view.context as Activity).window

    WindowInsetsControllerCompat(window, view).run {
        isAppearanceLightStatusBars = homeNavigator.isSystemBarDarkIcons
        isAppearanceLightNavigationBars = homeNavigator.isSystemBarDarkIcons
    }


    if (expired) {
        ErrorDialog(
            message = "로그인 정보 만료",
            description = "안전한 서비스 이용을 위해\n다시 로그인이 필요해요",
            confirmText = "로그인 하기",
            onConfirm = {
                homeNavController.navigateLogin()
                onChangeExpired(false)
            }
        )
    }

    Scaffold(
        bottomBar = {
            HomeBottomBarView(
                visible = homeNavigator.includeCurrentRouteInTabs(),
                tabs = HomeBottomTabs.entries.toList(),
                currentRoute = homeNavigator.currentRoute,
                navigateToTab = { homeNavigator.navigate(it.route) }
            )
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { padding ->

        val bottomBarHeight =
            (padding.calculateBottomPadding() - WindowInsets.safeDrawing.getBottom(LocalDensity.current).pxToDp)
                .coerceAtLeast(0.dp)

        Box {
            NavHost(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.safeDrawing),
                navController = homeNavController,
                startDestination = splashRoute,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None }
            ) {
                splashScreen(
                    navigateToLedger = homeNavController::navigateLedger,
                    navigateToLogin = homeNavController::navigateLogin
                )

                // sign
                loginScreen(
                    navigateToLedger = homeNavController::navigateLedger,
                    navigateToAgencyRegister = homeNavController::navigateAgencyRegister
                )

                signUpScreen(
                    navigateToLedger = homeNavController::navigateLedger,
                    navigateToSignUniversity = homeNavController::navigateSignUpUniversity,
                    navigateToAgency = homeNavController::navigateAgency,
                    navigateUp = homeNavController::navigateUp
                )

                signUpUniversity(
                    navigateToLedger = homeNavController::navigateLedger,
                    navigateToAgency = homeNavController::navigateAgency,
                    navigateUp = homeNavController::navigateUp
                )

                signCompleteScreen(
                    navigateToLedger = homeNavController::navigateLedger
                )

                // agency
                agencyScreen(
                    padding = padding,
                    navigateToRegister = homeNavController::navigateAgencyRegister,
                    navigateAgencyJoin = {}
                )

                agencyJoinScreen(
                    navigateToComplete = homeNavController::navigateAgencyJoinComplete,
                    navigateUp = homeNavController::navigateUp
                )

                agencyCompleteScreen(
                    navigateToSearch = {
                        homeNavController.navigateAgency(
                            navOptions = navOptions {
                                popUpTo(agencyRoute) { inclusive = true }
                            }
                        )
                    },
                    navigateToLedger = homeNavController::navigateLedger,
                )

                agencyRegisterScreen(
                    navigateToLedger = homeNavController::navigateLedger
                )

                agencyRegisterCompleteScreen(
                    navigateToSearch = {
                        homeNavController.navigateAgency(
                            navOptions = navOptions {
                                popUpTo(agencyRoute) { inclusive = true }
                            }
                        )
                    },
                    navigateToLedger = homeNavController::navigateLedger,
                    navigateToLedgerManual = homeNavController::navigateLedgerManual
                )

                // ledger
                ledgerScreen(
                    padding = PaddingValues(bottom = bottomBarHeight),
                    navigateToAgencyRegister = homeNavController::navigateAgencyRegister,
                    navigateToAgencyJoin = homeNavController::navigateAgencyJoin,
                    navigateToLedgerDetail = homeNavController::navigateLedgerDetail,
                    navigateToLedgerManual = homeNavController::navigateLedgerManual,
                )

                ledgerDetailScreen(navigateToLedger = homeNavController::navigateLedger)

                ledgerManualScreen(
                    navigateToLedger = homeNavController::navigateLedger,
                    popBackStack = homeNavController::popBackStack
                )

                // ocr
                ocrScreen(
                    navigateToOCRResult = homeNavController::navigateOCRResult,
                    navigateToLedger = homeNavController::navigateLedger,
                    popBackStack = homeNavController::popBackStack
                )

                ocrResultScreen(
                    navigateToLedger = homeNavController::navigateLedger,
                    navigateToOCRDetail = homeNavController::navigateOCRDetail,
                    popBackStack = homeNavController::popBackStack
                )

                ocrDetailScreen(
                    navigateToLedger = homeNavController::navigateLedger,
                    popBackStack = homeNavController::popBackStack
                )

                // mymong
                myMongNavGraph(
                    padding = PaddingValues(bottom = bottomBarHeight),
                    navigateToTermsOfUse = homeNavController::navigateTermsOfUse,
                    navigateToPrivacyPolicy = homeNavController::navigatePrivacyPolicy,
                    navigateToWithdrawal = homeNavController::navigateWithdrawal,
                    navigateToLogin = homeNavController::navigateLogin,
                    navigateUp = homeNavController::navigateUp
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsTopHeight(WindowInsets.safeDrawing)
                    .align(alignment = Alignment.TopCenter)
                    .background(color = homeNavigator.statusBarColor)
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsBottomHeight(WindowInsets.safeDrawing)
                    .align(alignment = Alignment.BottomCenter)
                    .background(color = homeNavigator.navigationBarColor)
            )
        }
    }
}