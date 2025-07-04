package com.moneymong.moneymong.feature.sign.sideeffect

import com.moneymong.moneymong.common.base.SideEffect

sealed class SignUpSideEffect : SideEffect {
    data class CreateUniversityApi(
        val universityName : String,
        val grade: Int
    ) : SignUpSideEffect()
}