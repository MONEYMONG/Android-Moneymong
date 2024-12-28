package com.moneymong.moneymong.feature.sign.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.component.button.MDSButton
import com.moneymong.moneymong.design_system.component.button.MDSButtonSize
import com.moneymong.moneymong.design_system.component.button.MDSButtonType
import com.moneymong.moneymong.design_system.error.ErrorDialog
import com.moneymong.moneymong.feature.sign.util.AgencyType

@Composable
fun SignUpButtonView(
    modifier: Modifier = Modifier,
    isEnabled: Boolean,
    visiblePopUpError: Boolean,
    popUpErrorMessage: String,
    visiblePopUpErrorChanged: (Boolean) -> Unit,
    onCreateUniversity: () -> Unit,
    navigateToSignUpUniversity : (String, AgencyType?) -> Unit,
    agencyName: String,
    agencyType: AgencyType?,
    pageType : Int,
    cornerShape : Dp = 10.dp
) {
    if (visiblePopUpError) {
        ErrorDialog(
            message = popUpErrorMessage,
            onConfirm = {
                visiblePopUpErrorChanged(false)
            }
        )
    } else {
        Column(
            modifier = modifier
        ) {
            MDSButton(
                modifier = Modifier.fillMaxWidth(),
//                    .height(56.dp),
                onClick = {
                    if(agencyType == AgencyType.GENERAL || pageType == 2) onCreateUniversity() else if (agencyType != AgencyType.GENERAL && pageType == 1) navigateToSignUpUniversity(agencyName, agencyType)
                },
                text = if(agencyType == AgencyType.GENERAL || pageType == 2) "등록하기" else "다음으로",
                type = MDSButtonType.PRIMARY,
                size = MDSButtonSize.LARGE,
                enabled = isEnabled,
                cornerShape = cornerShape
            )

        }
    }
}