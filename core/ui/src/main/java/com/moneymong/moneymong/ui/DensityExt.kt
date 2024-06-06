package com.moneymong.moneymong.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

inline val Int.pxToDp: Dp
    @Composable get() = with(LocalDensity.current) { this@pxToDp.toDp() }