package com.moneymong.moneymong.design_system.component.textfield.util

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.moneymong.moneymong.design_system.theme.Red03


fun withRequiredMark(title: String) = buildAnnotatedString {
    val markColor = Red03

    append(text = title)
    withStyle(style = SpanStyle(color = markColor)) {
        append(text = "*")
    }
}