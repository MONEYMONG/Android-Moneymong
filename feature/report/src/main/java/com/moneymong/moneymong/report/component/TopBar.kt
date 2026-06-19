package com.moneymong.moneymong.report.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import com.moneymong.moneymong.design_system.theme.Gray04
import com.moneymong.moneymong.design_system.theme.Gray07
import com.moneymong.moneymong.design_system.theme.Gray10
import com.moneymong.moneymong.design_system.theme.Heading1
import com.moneymong.moneymong.design_system.theme.MMHorizontalSpacing
import com.moneymong.moneymong.ui.noRippleClickable

@Composable
internal fun ReportTopBar(
    modifier: Modifier = Modifier,
    isShareEnabled: Boolean,
    onShare: () -> Unit,
    onClose: () -> Unit
) {
    Box(modifier = modifier.padding(vertical = 8.dp, horizontal = MMHorizontalSpacing)) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "레포트",
            color = Gray10,
            style = Heading1
        )
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .then(
                        if (isShareEnabled) {
                            Modifier.noRippleClickable(onClick = onShare)
                        } else {
                            Modifier
                        }
                    ),
                painter = painterResource(id = R.drawable.ic_share_default),
                contentDescription = "공유하기",
                tint = if (isShareEnabled) Gray07 else Gray04
            )
            Icon(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .size(24.dp)
                    .noRippleClickable(onClick = onClose),
                painter = painterResource(id = R.drawable.ic_close_default),
                contentDescription = "닫기",
                tint = Gray07
            )
        }
    }
}
