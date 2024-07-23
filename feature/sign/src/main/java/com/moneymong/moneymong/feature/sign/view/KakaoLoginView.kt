package com.moneymong.moneymong.feature.sign.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.moneymong.moneymong.design_system.R

@Composable
fun KakaoLoginView(
    modifier: Modifier = Modifier,
    loginByKaKao: () -> Unit
) {

    Image(
        modifier = modifier.clickable {
            loginByKaKao()
        },
        painter = painterResource(id = R.drawable.img_kakao_login),
        contentDescription = "LoginByKakao",
        contentScale = ContentScale.FillWidth,
    )
}