package com.moneymong.moneymong.design_system.component.textfield.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.theme.Red03
import com.moneymong.moneymong.ui.toSp

@Composable
fun withRequiredMark(title: String) =
    buildAnnotatedString {
        val spacingInSp = 2.dp.toSp

        val markColor = Red03

        append(text = title)
        withStyle(style = SpanStyle(fontSize = spacingInSp)) {
            append(text = "\u2003")
        }
        withStyle(style = SpanStyle(color = markColor)) {
            append(text = "*")
        }
    }
