package com.moneymong.moneymong.feature.sign

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moneymong.moneymong.common.ui.noRippleClickable
import com.moneymong.moneymong.design_system.R
import com.moneymong.moneymong.design_system.component.selection.MDSSelection
import com.moneymong.moneymong.design_system.component.textfield.MDSTextField
import com.moneymong.moneymong.design_system.component.textfield.util.MDSTextFieldIcons
import com.moneymong.moneymong.design_system.error.ErrorDialog
import com.moneymong.moneymong.design_system.error.ErrorScreen
import com.moneymong.moneymong.design_system.theme.Blue04
import com.moneymong.moneymong.design_system.theme.Body2
import com.moneymong.moneymong.design_system.theme.Body3
import com.moneymong.moneymong.design_system.theme.Gray07
import com.moneymong.moneymong.design_system.theme.MMHorizontalSpacing
import com.moneymong.moneymong.design_system.theme.White
import com.moneymong.moneymong.feature.sign.sideeffect.SignUpSideEffect
import com.moneymong.moneymong.feature.sign.state.SignUpState
import com.moneymong.moneymong.feature.sign.util.AgencyType
import com.moneymong.moneymong.feature.sign.view.SignUpButtonView
import com.moneymong.moneymong.feature.sign.view.SignUpTitleView
import com.moneymong.moneymong.feature.sign.viewmodel.SignUpViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpScreen(
    navigateToLedger: () -> Unit,
    navigateToSignUpUniversity : (String, AgencyType?) -> Unit,
    navigateToAgency : () -> Unit,
    navigateUp: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val state = viewModel.collectAsState().value

    BackHandler {
        navigateUp()
    }

    if(state.visibleError){
        ErrorScreen(
            modifier = Modifier.fillMaxSize(),
            message = state.errorMessage,
            onRetry = {
                viewModel.visibleErrorChanged(false)
            }
        )
    }
    else if(state.visiblePopUpError){
        ErrorDialog(
            message = state.popUpErrorMessage,
            onConfirm = {
                viewModel.visiblePopUpErrorChanged(false)
            }
        )
    }
    else{
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
            content = {
                SignUpContent(
                    navigateToLedger = navigateToLedger,
                    navigateToSignUpUniversity = navigateToSignUpUniversity,
                    navigateToAgency = navigateToAgency,
                    viewModel = viewModel,
                    state = state
                )
            }
        )
    }
}


@Composable
fun SignUpContent(
    navigateToLedger: () -> Unit,
    navigateToSignUpUniversity : (String, AgencyType?) -> Unit,
    navigateToAgency : () -> Unit,
    viewModel: SignUpViewModel,
    state: SignUpState
) {

    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = state.isUnivCreated) {
        if (state.isUnivCreated) {
            viewModel.registerAgency()
        }
    }

    LaunchedEffect(key1 = state.isAgencyCreated) {
        if (state.isAgencyCreated) {
            navigateToLedger()
        }
    }

    viewModel.collectSideEffect {
        when (it) {
            is SignUpSideEffect.CreateUniversityApi -> {
                viewModel.createUniv(it.universityName, it.grade)
            }
            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = MMHorizontalSpacing),
            horizontalAlignment = Alignment.Start
        ) {
            SignUpTitleView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(89.dp)
                    .padding(top = 12.dp, bottom = 12.dp),
            )

            Column(
                modifier = Modifier
                    .padding(top = 28.dp, end = 28.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "소속 유형",
                    style = Body2,
                    color = Color.Black
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MDSSelection(
                        modifier = Modifier.weight(1f),
                        text = "기타 모임",
                        enabled = true,
                        isSelected = state.agencyType == AgencyType.GENERAL,
                        onClick = { viewModel.onChangeAgencyType(AgencyType.GENERAL) }
                    )
                    MDSSelection(
                        modifier = Modifier.weight(1f),
                        text = "동아리",
                        enabled = true,
                        isSelected = state.agencyType == AgencyType.CLUB,
                        onClick = { viewModel.onChangeAgencyType(AgencyType.CLUB) }
                    )
                    MDSSelection(
                        modifier = Modifier.weight(1f),
                        text = "학생회",
                        enabled = true,
                        isSelected = state.agencyType == AgencyType.STUDENT_COUNCIL,
                        onClick = { viewModel.onChangeAgencyType(AgencyType.STUDENT_COUNCIL) }
                    )
                }
            }
            AnimatedVisibility(visible = state.MDSSelected) {
                Column(
                    modifier = Modifier
                    .padding(top = 28.dp),
                    ) {
                    Text(
                        text = "소속 이름",
                        style = Body2,
                        color = Blue04
                    )
                    MDSTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { focusState ->
                                if (focusState.isFocused) {
                                    viewModel.updateEdittextFocused(true)
                                } else {
                                    viewModel.updateEdittextFocused(false)
                                }
                            },
                        value = state.agencyName,
                        onValueChange = viewModel::updateAgencyName,
                        title = "",
                        isFilled = false,
                        singleLine = true,
                        maxCount = 50,
                        placeholder = "",
                        icon = MDSTextFieldIcons.Clear,
                        onIconClick = { viewModel.updateAgencyName(TextFieldValue()) },
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            SignUpButtonView(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = if (state.editTextFocused) 0.dp else MMHorizontalSpacing),
                isEnabled = state.isButtonVisible,
                visiblePopUpError = state.visiblePopUpError,
                popUpErrorMessage = state.popUpErrorMessage,
                visiblePopUpErrorChanged = { visiblePopUpError ->
                    viewModel.visiblePopUpErrorChanged(visiblePopUpError)
                },
                onCreateUniversity = {
                    viewModel.eventEmit(
                        SignUpSideEffect.CreateUniversityApi(
                            state.selectedUniv,
                            state.gradeInfor
                        )
                    )
                },
                navigateToSignUpUniversity = { agencyName, agencyType ->
                    navigateToSignUpUniversity(agencyName, agencyType)
                },
                agencyName = state.agencyName.text,
                agencyType = state.agencyType,
                pageType = 1
            )

            if (!state.editTextFocused){
                Spacer(modifier = Modifier.height(60.dp))
            }

        }

        if (!state.editTextFocused) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .noRippleClickable {
                            viewModel.eventEmit(
                                SignUpSideEffect.CreateUniversityApi(
                                    state.selectedUniv,
                                    state.gradeInfor
                                )
                            )
                            navigateToAgency()
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
