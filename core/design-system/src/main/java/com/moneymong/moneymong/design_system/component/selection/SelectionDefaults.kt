package com.moneymong.moneymong.design_system.component.selection

import androidx.compose.ui.graphics.Color
import com.moneymong.moneymong.design_system.theme.Blue01
import com.moneymong.moneymong.design_system.theme.Blue04
import com.moneymong.moneymong.design_system.theme.Gray03
import com.moneymong.moneymong.design_system.theme.Gray04
import com.moneymong.moneymong.design_system.theme.Gray05
import com.moneymong.moneymong.design_system.theme.White


enum class MDSSelectionType(
    val backgroundColor: Color,
    val contentColor: Color
) {
    PRIMARY(
        backgroundColor = Blue04,
        contentColor = White
    ),
    SECONDARY(
        backgroundColor = Blue01,
        contentColor = Blue04
    )
}

internal val unSelectedBackgroundColor = White
internal val unSelectedContentColor = Gray05

internal val disabledBackgroundColor = Gray03
internal val disabledContentColor = Gray04

internal val selectionBackgroundColor: (
    enabled: Boolean,
    isSelected: Boolean,
    type: MDSSelectionType
) -> Color = { enabled, isSelected, type ->
    when {
        enabled.not() -> disabledBackgroundColor
        isSelected.not() -> unSelectedBackgroundColor
        else -> type.backgroundColor
    }
}

internal val selectionContentColor: (
    enabled: Boolean,
    isSelected: Boolean,
    type: MDSSelectionType
) -> Color = { enabled, isSelected, type ->
    when {
        enabled.not() -> disabledContentColor
        isSelected.not() -> unSelectedContentColor
        else -> type.contentColor
    }
}

internal val selectionBorderColor: (
    enabled: Boolean,
    isSelected: Boolean,
    type: MDSSelectionType
) -> Color = { enabled, isSelected, type ->
    when {
        enabled.not() -> disabledBackgroundColor
        isSelected.not() -> Gray03
        else -> type.backgroundColor
    }
}