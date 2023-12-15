package com.moneymong.moneymong.design_system.component.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.moneymong.moneymong.design_system.R
import com.moneymong.moneymong.design_system.component.button.MDSButton
import com.moneymong.moneymong.design_system.component.button.MDSButtonSize
import com.moneymong.moneymong.design_system.component.button.MDSButtonType
import com.moneymong.moneymong.design_system.theme.Body4
import com.moneymong.moneymong.design_system.theme.Gray05
import com.moneymong.moneymong.design_system.theme.Gray10
import com.moneymong.moneymong.design_system.theme.Heading1

@Composable
fun MDSModal(
    modifier: Modifier = Modifier,
    icon: Int,
    title: String,
    description: String,
    negativeBtnText: String,
    positiveBtnText: String,
    onClickNegative: () -> Unit,
    onClickPositive: () -> Unit,
    onDismissRequest: () -> Unit = onClickNegative
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Icon(
                modifier = Modifier.size(52.dp),
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = title,
                style = Heading1,
                color = Gray10
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = description,
                style = Body4,
                color = Gray05
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.padding(horizontal = 22.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MDSButton(
                    modifier = Modifier.width(132.dp),
                    text = negativeBtnText,
                    size = MDSButtonSize.LARGE,
                    type = MDSButtonType.NEGATIVE,
                    onClick = onClickNegative
                )
                Spacer(modifier = Modifier.width(12.dp))
                MDSButton(
                    modifier = Modifier.width(132.dp),
                    text = positiveBtnText,
                    size = MDSButtonSize.LARGE,
                    type = MDSButtonType.PRIMARY,
                    onClick = onClickPositive
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MDSModalPreview() {
    MDSModal(
        icon = R.drawable.ic_camera_modal,
        title = "카메라 접근을 허용하시겠습니까?",
        description = "영수증 스캔을 위해 필요합니다",
        negativeBtnText = "허용 안함",
        positiveBtnText = "허용",
        onClickNegative = { /*TODO*/ },
        onClickPositive = { /*TODO*/ })
}