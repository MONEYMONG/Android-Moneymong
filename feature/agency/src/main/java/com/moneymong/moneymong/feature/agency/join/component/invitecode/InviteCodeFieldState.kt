package com.moneymong.moneymong.feature.agency.join.component.invitecode

import androidx.compose.ui.graphics.Color
import com.moneymong.moneymong.design_system.theme.Blue04
import com.moneymong.moneymong.design_system.theme.Gray01
import com.moneymong.moneymong.design_system.theme.Gray03
import com.moneymong.moneymong.design_system.theme.Red02
import com.moneymong.moneymong.design_system.theme.White

internal sealed class InviteCodeFieldState {

    interface Defaults {
        val backgroundColor: Color
        val borderColor: Color
    }

    data object FILLED : InviteCodeFieldState()

    data object EMPTY : InviteCodeFieldState(), Defaults {
        override val backgroundColor: Color = White
        override val borderColor: Color = Gray03
    }

    data object FOCUSED : InviteCodeFieldState(), Defaults {
        override val backgroundColor: Color = White
        override val borderColor: Color = Blue04
    }

    data object ERROR : InviteCodeFieldState(), Defaults {
        override val backgroundColor: Color = Gray01
        override val borderColor: Color = Red02
    }
}