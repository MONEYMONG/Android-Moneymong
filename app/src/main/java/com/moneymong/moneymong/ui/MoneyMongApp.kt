package com.moneymong.moneymong.ui

import androidx.compose.runtime.Composable
import com.moneymong.moneymong.home.HomeScreen

@Composable
fun MoneyMongApp(
    expired: Boolean,
    onChangeExpired: (Boolean) -> Unit,
    inviteCode: String?,
    inviteJoinFinished: Boolean,
    joinAgency: () -> Unit,
) {
    HomeScreen(
        expired = expired,
        onChangeExpired = onChangeExpired,
        inviteCode = inviteCode,
        inviteJoinFinished = inviteJoinFinished,
        joinAgency = joinAgency
    )
}