package com.moneymong.moneymong

import com.moneymong.moneymong.android.State
import com.moneymong.moneymong.invite.InviteDeepLink

data class MainState(
    val shouldUpdate: Boolean = false,
    val pendingInvite: InviteDeepLink? = null,
    val inviteJoinFinished: Boolean = false,
    val inviteJoinErrorMessage: String = ""
) : State
