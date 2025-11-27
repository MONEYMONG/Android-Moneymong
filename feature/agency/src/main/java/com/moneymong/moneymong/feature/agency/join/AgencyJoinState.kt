package com.moneymong.moneymong.feature.agency.join

import com.moneymong.moneymong.android.State

data class AgencyJoinState(
    val isError: Boolean = false,
    val snackBarMessage: String = "",
    val inputCode: String = "",
    val codeAccess: Boolean = false,
    val visiblePopUpError: Boolean = false,
    val errorPopUpMessage: String = "",
    val isBackButton: Boolean = false,
) : State