package com.moneymong.moneymong.common.ui

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SystemBarColors(
    val statusBarColor: Color?,
    val navigationBarColor: Color?
) {
    companion object {
        val INITIAL = SystemBarColors(
            statusBarColor = null,
            navigationBarColor = null
        )
    }
}

object SystemBarColorController {

    private val _systemBarColors = MutableStateFlow(SystemBarColors.INITIAL)
    val systemBarColors: StateFlow<SystemBarColors> = _systemBarColors.asStateFlow()

    fun setSystemBarColors(color: Color) {
        _systemBarColors.value = SystemBarColors(
            statusBarColor = color,
            navigationBarColor = color
        )
    }

    fun initialSystemBarColors() {
        _systemBarColors.value = SystemBarColors.INITIAL
    }

    fun setStatusBarColor(color: Color?) {
        _systemBarColors.value = _systemBarColors.value.copy(statusBarColor = color)
    }

    fun setNavigationBarColor(color: Color?) {
        _systemBarColors.value = _systemBarColors.value.copy(navigationBarColor = color)
    }
}