package com.moneymong.moneymong.feature.agency.join

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moneymong.moneymong.common.ui.noRippleClickable
import com.moneymong.moneymong.design_system.R
import com.moneymong.moneymong.design_system.component.snackbar.MDSSnackbarHost
import com.moneymong.moneymong.design_system.error.ErrorDialog
import com.moneymong.moneymong.design_system.theme.Black
import com.moneymong.moneymong.design_system.theme.Gray10
import com.moneymong.moneymong.design_system.theme.Heading3
import com.moneymong.moneymong.design_system.theme.MMHorizontalSpacing
import com.moneymong.moneymong.design_system.theme.White
import com.moneymong.moneymong.feature.agency.join.component.AgencyInviteCodeView
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.compose.collectAsState


@Composable
fun AgencyJoinScreen(
    modifier: Modifier = Modifier,
    viewModel: AgencyJoinViewModel = hiltViewModel(),
    navigateToComplete: () -> Unit,
    navigateUp: () -> Unit,
) {
    val state = viewModel.collectAsState().value

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(White)
            .padding(horizontal = MMHorizontalSpacing),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .height(44.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .noRippleClickable {
                            navigateUp()
                        },
                    painter = painterResource(id = R.drawable.ic_close_default),
                    contentDescription = null,
                    tint = Black
                )
            }
        },
        content = { innerPadding ->
            JoinContent(
                modifier = Modifier.padding(innerPadding),
                state = state,
                viewModel = viewModel,
                navigateToComplete = navigateToComplete,
                navigateUp = navigateUp
            )
        }
    )
}

@Composable
private fun JoinContent(
    modifier: Modifier = Modifier,
    state: AgencyJoinState,
    viewModel: AgencyJoinViewModel,
    navigateToComplete: () -> Unit,
    navigateUp: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    if (state.visiblePopUpError) {
        ErrorDialog(
            message = state.errorPopUpMessage,
            onConfirm = {
                viewModel.visiblePopUpErrorChanged(false)
                viewModel.onIsErrorChanged(false)
                viewModel.resetNumbers()
                navigateUp()
            }
        )
    }

    LaunchedEffect(key1 = state.isError) {
        if (state.isError) {
            val result = snackbarHostState.showSnackbar(
                message = state.snackBarMessage,
                actionLabel = "다시입력"
            )

            if (result == SnackbarResult.ActionPerformed) {
                viewModel.onIsErrorChanged(false)
                viewModel.resetNumbers()
            }
        } else {
            val snackbarFadeOutMillis = 75L //  androidx.compose.material3.SnackbarHostState.kt
            delay(snackbarFadeOutMillis)
            focusRequester.requestFocus()
        }
    }

    LaunchedEffect(key1 = state.codeAccess) {
        if (state.codeAccess) {
            navigateToComplete()
        }
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White)
            .padding(top = 8.dp),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "초대코드를 입력해주세요",
            color = Gray10,
            style = Heading3
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 151.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            AgencyInviteCodeView(
                modifier = Modifier,
                focusRequester = focusRequester,
                isError = state.isError,
                inputCode = state.inputCode,
                onValueChanged = viewModel::changeInputNumber,
                checkInviteCode = {
                    viewModel.findLedgerByInviteCode()
                    focusManager.clearFocus()
                },
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        MDSSnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .fillMaxWidth()
                .align(BottomCenter)
                .padding(bottom = 20.dp)
        )
    }
}



