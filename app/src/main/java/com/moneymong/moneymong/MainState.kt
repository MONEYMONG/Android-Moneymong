package com.moneymong.moneymong

import com.moneymong.moneymong.android.State

data class MainState(
    val shouldUpdate: Boolean = false,
    val pendingInviteCode: String? = null,
    val inviteJoinFinished: Boolean = false,
    val inviteJoinErrorMessage: String = ""
) : State
