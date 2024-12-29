package com.moneymong.moneymong.feature.sign.view

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moneymong.moneymong.common.ui.noRippleClickable
import com.moneymong.moneymong.design_system.R
import com.moneymong.moneymong.design_system.error.ErrorDialog
import com.moneymong.moneymong.design_system.error.ErrorScreen
import com.moneymong.moneymong.design_system.theme.Black
import com.moneymong.moneymong.design_system.theme.Blue04
import com.moneymong.moneymong.design_system.theme.Body3
import com.moneymong.moneymong.design_system.theme.Gray06
import com.moneymong.moneymong.design_system.theme.Gray07
import com.moneymong.moneymong.design_system.theme.Heading2
import com.moneymong.moneymong.design_system.theme.MMHorizontalSpacing
import com.moneymong.moneymong.design_system.theme.White
import com.moneymong.moneymong.feature.sign.state.SignUpUniversityState
import com.moneymong.moneymong.feature.sign.util.AgencyType
import com.moneymong.moneymong.feature.sign.viewmodel.SignUpUniversityViewModel
import org.orbitmvi.orbit.compose.collectAsState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpUniversity(
    navigateToLedger: () -> Unit,
    navigateToAgency: () -> Unit,
    navigateUp: () -> Unit,
    agencyName: String,
    agencyType: AgencyType,
    viewModel: SignUpUniversityViewModel = hiltViewModel()
) {
    val state = viewModel.collectAsState().value

    BackHandler {
        navigateUp()
    }

    if (state.visibleError) {
        ErrorScreen(
            modifier = Modifier.fillMaxSize(),
            message = state.errorMessage,
            onRetry = {
                viewModel.visibleErrorChanged(false)
            }
        )
    } else if (state.visiblePopUpError) {
        ErrorDialog(
            message = state.popUpErrorMessage,
            onConfirm = {
                viewModel.visiblePopUpErrorChanged(false)
            }
        )
    } else {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(White),
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .background(White)
                        .padding(horizontal = MMHorizontalSpacing),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chevron_left),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .background(White)
                            .noRippleClickable {
                                navigateUp()
                            },
                        tint = Gray07
                    )
                }
            },
            content = { paddingValues ->
                SignUpUniversityContent(
                    modifier = Modifier.padding(paddingValues),
                    navigateToLedger = navigateToLedger,
                    agencyName = agencyName,
                    agencyType = agencyType,
                    viewModel = viewModel,
                    state = state
                )
            }
        )
    }
}

@Composable
fun SignUpUniversityContent(
    modifier: Modifier,
    navigateToLedger: () -> Unit,
    agencyName: String,
    agencyType: AgencyType,
    viewModel: SignUpUniversityViewModel,
    state: SignUpUniversityState

) {

    LaunchedEffect(key1 = state.isUnivCreated) {
        if (state.isUnivCreated) {
            viewModel.registerAgency(agencyName, agencyType)
        }
    }

    LaunchedEffect(key1 = state.isAgencyCreated) {
        if (state.isAgencyCreated) {
            navigateToLedger()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = modifier
                .background(White)
                .padding(horizontal = MMHorizontalSpacing),
        ) {
            Text(
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
                text = "어디 학교 교내 동아리인가요?",
                style = Heading2,
                color = Black
            )
            Text(
                modifier = Modifier.padding(bottom = 12.dp),
                text = "소속 대학교를 알려주세요",
                style = Body3,
                color = Gray06
            )

            SearchUnivView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp, bottom = 28.dp)
                    .weight(1.0F),
                isFilled = state.isFilled,
                isFilledChanged = { isFilled -> viewModel.isFilledChanged(isFilled) },
                isListVisible = state.isListVisible,
                isListVisibleChanged = { isListVisible ->
                    viewModel.isListVisibleChanged(
                        isListVisible
                    )
                },
                isItemSelectedChanged = { isItemSelected ->
                    viewModel.isItemSelectedChanged(
                        isItemSelected
                    )
                },
                isItemSelected = state.isItemSelected,
                textValue = state.textValue,
                universityResponse = state.universityResponse,
                onClick = {
                    viewModel.isSelectedChanged(true)
                    viewModel.selectedUnivChanged(it)
                },
                onChange = { viewModel.textValueChanged(it) },
                onSearchIconClicked = {
                    viewModel.searchUniv(it)
                },
                value = state.textValue,
                isButtonVisibleChanged = { isButtonVisible ->
                    viewModel.isButtonVisibleChanged(
                        isButtonVisible
                    )
                },
                selectedUniv = state.selectedUniv,
                changeButtonCornerShape = { cornerShape ->
                    viewModel.changeButtonCornerShape(
                        cornerShape
                    )
                },
                changeEditTextFocus = { editTextFocused ->
                    viewModel.changeEditTextFocus(
                        editTextFocused
                    )
                }
            )

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.Bottom
        ) {
            SignUpButtonView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = if (state.editTextFocused) 0.dp else MMHorizontalSpacing),
                isEnabled = state.isItemSelected,
                visiblePopUpError = state.visiblePopUpError,
                popUpErrorMessage = state.popUpErrorMessage,
                visiblePopUpErrorChanged = { visiblePopUpError ->
                    viewModel.visiblePopUpErrorChanged(visiblePopUpError)
                },
                onCreateUniversity = {
                    viewModel.createUniv(state.selectedUniv, state.gradeInfor)
                },
                navigateToSignUpUniversity = { agencyName, agencyType -> },
                agencyName = agencyName,
                agencyType = agencyType,
                pageType = 2,
                cornerShape = state.buttonCornerShape
            )
            if (!state.editTextFocused) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .noRippleClickable {
                            viewModel.createUniv(state.selectedUniv, state.gradeInfor)
                        },
                    textAlign = TextAlign.Center,
                    text = "총무에게 초대받았어요",
                    style = Body3,
                    color = Blue04
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}