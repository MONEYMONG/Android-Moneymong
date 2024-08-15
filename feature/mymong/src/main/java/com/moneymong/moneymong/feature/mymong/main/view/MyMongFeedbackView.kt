package com.moneymong.moneymong.feature.mymong.main.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.component.button.MDSButton
import com.moneymong.moneymong.design_system.component.button.MDSButtonSize
import com.moneymong.moneymong.design_system.component.button.MDSButtonType
import com.moneymong.moneymong.design_system.theme.Body4
import com.moneymong.moneymong.design_system.theme.Gray08
import com.moneymong.moneymong.design_system.R
import com.moneymong.moneymong.design_system.theme.White
import com.moneymong.moneymong.feature.mymong.main.util.myMongRoundRectShadow

@Composable
fun MyMongFeedbackView(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .myMongRoundRectShadow()
            .background(color = White, shape = RoundedCornerShape(12.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "머니몽에게 자유롭게\n문의 해보세요!",
                    style = Body4,
                    color = Gray08
                )
                Spacer(modifier = Modifier.height(8.dp))
                MDSButton(
                    modifier = Modifier.padding(end = 10.dp),
                    text = "머니몽 팀에게 카톡하기",
                    size = MDSButtonSize.SMALL,
                    type = MDSButtonType.SECONDARY,
                    contentHorizontalPadding = 12.dp,
                    onClick = onClick
                )
            }
            Image(
                modifier = Modifier.size(134.dp),
                painter = painterResource(id = R.drawable.img_kakao_feedback),
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyMongFeedbackPreview() {
    MyMongFeedbackView {

    }
}