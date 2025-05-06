package com.moneymong.moneymong.ledger.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.moneymong.moneymong.design_system.theme.Body3
import com.moneymong.moneymong.design_system.theme.Body4
import com.moneymong.moneymong.design_system.theme.Gray07
import com.moneymong.moneymong.design_system.theme.Gray08
import com.moneymong.moneymong.design_system.theme.White
import com.moneymong.moneymong.ledger.R

@Composable
fun LedgerAgencyEmptyView(
    modifier: Modifier = Modifier,
    onClickAgencyRegister: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(320.dp, 100.dp),
            painter = painterResource(id = R.drawable.img_empty_agency),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "기록한 장부를 만들어보세요",
            style = Body3,
            color = Gray07
        )
        Spacer(modifier = Modifier.height(16.dp))
        MDSButton(
            modifier = Modifier.width(140.dp),
            text = "장부 생성하기",
            type = MDSButtonType.PRIMARY,
            size = MDSButtonSize.MEDIUM,
            onClick = onClickAgencyRegister
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LedgerAgencyEmptyPreview() {
    LedgerAgencyEmptyView() {
    }
}