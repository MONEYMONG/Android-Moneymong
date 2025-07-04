package com.moneymong.moneymong.feature.mymong.main.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.R
import com.moneymong.moneymong.design_system.component.indicator.LoadingItem
import com.moneymong.moneymong.design_system.error.ErrorItem
import com.moneymong.moneymong.design_system.theme.Blue04
import com.moneymong.moneymong.design_system.theme.Body2
import com.moneymong.moneymong.design_system.theme.Gray08
import com.moneymong.moneymong.design_system.theme.Gray10
import com.moneymong.moneymong.design_system.theme.Heading1

@Composable
internal fun MyMongInfoView(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isError: Boolean,
    errorMessage: String,
    name: String,
    email: String,
    getInfo: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            LoadingItem()
        }
        if (isError) {
            ErrorItem(
                message = errorMessage,
                onRetry = getInfo
            )
        }
        val showContent = isLoading.not() && isError.not()
        Column(modifier = if (showContent) Modifier else Modifier.alpha(alpha = 0f)) {
            Profile(
                name = name,
                email = email
            )
        }
    }
}

@Composable
private fun Profile(
    modifier: Modifier = Modifier,
    name: String,
    email: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Spacer(modifier = Modifier.width(6.dp))
        Image(
            modifier = modifier
                .size(36.dp)
                .drawBehind {
                    drawCircle(
                        color = Blue04,
                        radius = 48.dp.toPx() / 2f
                    )
                },
            painter = painterResource(id = R.drawable.img_profile),
            contentDescription = "profile Image"
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = name + "님",
                color = Gray10,
                style = Heading1
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = R.drawable.img_kakao_logo),
                    contentDescription = "Kakao Email"
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = email,
                    color = Gray08,
                    style = Body2
                )
            }
        }
    }
}