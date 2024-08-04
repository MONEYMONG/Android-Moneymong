package com.moneymong.moneymong.feature.sign.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.component.button.MDSButton
import com.moneymong.moneymong.design_system.component.button.MDSButtonSize
import com.moneymong.moneymong.design_system.component.button.MDSButtonType
import com.moneymong.moneymong.design_system.error.ErrorDialog
import com.moneymong.moneymong.design_system.theme.Blue04
import com.moneymong.moneymong.design_system.theme.Body3
import com.moneymong.moneymong.feature.sign.sideeffect.SignUpSideEffect

@Composable
fun SignUpButtonView(
    modifier: Modifier = Modifier,
    isEnabled: Boolean,
    visiblePopUpError: Boolean,
    popUpErrorMessage: String,
    visiblePopUpErrorChanged: (Boolean) -> Unit,
    onCreateUniversity: () -> Unit,
    storeSchoolInfoExist: (Boolean) -> Unit
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
                onClick = {
                    onCreateUniversity()
                    storeSchoolInfoExist(true)
                },
                text = "가입하기",
                type = MDSButtonType.PRIMARY,
                size = MDSButtonSize.LARGE,
                enabled = isEnabled
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { onCreateUniversity() }
            ){
                Text(
                    textAlign = TextAlign.Center,
                    text = "입력할 대학 정보가 없어요",
                    color = Blue04,
                    style = Body3
                )
            }
        }
    }
}