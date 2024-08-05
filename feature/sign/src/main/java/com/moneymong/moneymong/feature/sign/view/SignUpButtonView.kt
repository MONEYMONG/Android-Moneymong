package com.moneymong.moneymong.feature.sign.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.common.ui.noRippleClickable
import com.moneymong.moneymong.design_system.component.button.MDSButton
import com.moneymong.moneymong.design_system.component.button.MDSButtonSize
import com.moneymong.moneymong.design_system.component.button.MDSButtonType
import com.moneymong.moneymong.design_system.error.ErrorDialog
import com.moneymong.moneymong.design_system.theme.Blue04
import com.moneymong.moneymong.design_system.theme.Body3

@Composable
fun SignUpButtonView(
    modifier: Modifier = Modifier,
    isEnabled: Boolean,
    visiblePopUpError: Boolean,
    popUpErrorMessage: String,
    visiblePopUpErrorChanged: (Boolean) -> Unit,
    onCreateUniversity: () -> Unit,
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
                },
                text = "가입하기",
                type = MDSButtonType.PRIMARY,
                size = MDSButtonSize.LARGE,
                enabled = isEnabled
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .noRippleClickable {
                        onCreateUniversity()
                    },
                textAlign = TextAlign.Center,
                text = "입력할 대학 정보가 없어요",
                color = Blue04,
                style = Body3
            )

        }
    }
}