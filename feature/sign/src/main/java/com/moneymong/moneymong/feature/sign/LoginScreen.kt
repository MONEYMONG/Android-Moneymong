package com.moneymong.moneymong.feature.sign

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moneymong.moneymong.design_system.error.ErrorScreen
import com.moneymong.moneymong.design_system.theme.Gray01
import com.moneymong.moneymong.design_system.theme.MMHorizontalSpacing
import com.moneymong.moneymong.feature.sign.util.KakaoLogin
import com.moneymong.moneymong.feature.sign.view.KakaoLoginView
import com.moneymong.moneymong.feature.sign.view.SignUpOnboardingCarouselView
import com.moneymong.moneymong.feature.sign.viewmodel.LoginViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun LoginScreen(
    navigateToSignup: () -> Unit,
    navigateToLedger: () -> Unit,
    navigateToLogin: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(key1 = state.isSchoolInfoProvided) {
        if (state.isSchoolInfoProvided == true) {
            navigateToLedger()
        } else if (state.isSchoolInfoProvided == false) {
            navigateToSignup()
            viewModel.isSchoolInfoProvidedChanged(null)
        }
    }

    LaunchedEffect(key1 = state.isLoginRequired)
    {
        if (state.isLoginRequired == true) {
            navigateToLogin()
            viewModel.isLoginRequiredChanged(false)
        }
    }

    if (state.visibleError == true) {
        ErrorScreen(
            modifier = Modifier.fillMaxSize(),
            message = state.errorMessage,
            onRetry = { viewModel.visibleErrorChanged(false) }
        )
    } else if (state.visibleError == false) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Gray01)
                .padding(start = MMHorizontalSpacing, end = MMHorizontalSpacing, bottom = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(36.dp))
            SignUpOnboardingCarouselView()
            Spacer(Modifier.weight(1f))
            KakaoLoginView(
                modifier = Modifier.fillMaxWidth(),
                loginByKaKao = {
                    KakaoLogin(
                        context = context,
                        onSuccess = viewModel::onKakaoLoginSuccess,
                        onFailure = viewModel::onKakaoLoginFailure
                    )
                }
            )
        }
    }
}
