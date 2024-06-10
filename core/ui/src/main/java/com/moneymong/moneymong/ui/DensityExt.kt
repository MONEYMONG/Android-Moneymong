package com.moneymong.moneymong.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

inline val Int.pxToDp: Dp
    @Composable get() = with(LocalDensity.current) { this@pxToDp.toDp() }

inline val TextUnit.spToDp: Dp
    @Composable get() = with(LocalDensity.current) { this@spToDp.toDp() }

inline val Dp.toSp: TextUnit
    @Composable get() = with(LocalDensity.current) { this@toSp.toSp() }