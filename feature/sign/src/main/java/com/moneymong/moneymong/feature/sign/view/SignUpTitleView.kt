package com.moneymong.moneymong.feature.sign.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.theme.Black
import com.moneymong.moneymong.design_system.theme.Body3
import com.moneymong.moneymong.design_system.theme.Gray06
import com.moneymong.moneymong.design_system.theme.Heading2
import com.moneymong.moneymong.design_system.theme.White

@Composable
fun SignUpTitleView(modifier: Modifier = Modifier, /*subTitleState: Boolean*/) {
    Column(
        modifier = modifier.background(White),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "회비관리가 필요한\n소속정보를 알려주세요!",
            style = Heading2,
            color = Black
        )
    }

}