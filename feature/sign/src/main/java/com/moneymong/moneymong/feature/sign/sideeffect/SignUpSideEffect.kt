package com.moneymong.moneymong.feature.sign.sideeffect

import com.moneymong.moneymong.android.SideEffect

sealed class SignUpSideEffect : com.moneymong.moneymong.android.SideEffect {
    data class CreateUniversityApi(
        val universityName : String,
        val grade: Int
    ) : SignUpSideEffect()
}