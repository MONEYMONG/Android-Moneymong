package com.moneymong.moneymong.report.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.R
import com.moneymong.moneymong.design_system.theme.Gray07
import com.moneymong.moneymong.design_system.theme.Gray10
import com.moneymong.moneymong.design_system.theme.Heading1
import com.moneymong.moneymong.ui.noRippleClickable

@Composable
internal fun ReportTopBar(
    modifier: Modifier = Modifier,
    onClose: () -> Unit
) {
    Box(modifier = modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "레포트",
            color = Gray10,
            style = Heading1
        )
        Icon(
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterEnd)
                .noRippleClickable(onClick = onClose),
            painter = painterResource(id = R.drawable.ic_close_default),
            contentDescription = "닫기",
            tint = Gray07
        )
    }
}