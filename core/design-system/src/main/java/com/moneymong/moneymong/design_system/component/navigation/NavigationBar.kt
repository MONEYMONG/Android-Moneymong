package com.moneymong.moneymong.design_system.component.navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.R
import com.moneymong.moneymong.design_system.component.navigation.MDSNavigationBarItemDefaults.selectedIconColor
import com.moneymong.moneymong.design_system.component.navigation.MDSNavigationBarItemDefaults.selectedLabelColor
import com.moneymong.moneymong.design_system.component.navigation.MDSNavigationBarItemDefaults.unSelectedIconColor
import com.moneymong.moneymong.design_system.component.navigation.MDSNavigationBarItemDefaults.unSelectedLabelColor
import com.moneymong.moneymong.design_system.theme.Blue04
import com.moneymong.moneymong.design_system.theme.Body2
import com.moneymong.moneymong.design_system.theme.Gray02
import com.moneymong.moneymong.design_system.theme.Gray04
import com.moneymong.moneymong.design_system.theme.White

@Composable
fun MDSNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .background(color = White)
                .clip(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .border(
                    width = 1.dp,
                    color = Gray02,
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                )
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    drawLine(
                        color = Gray02,
                        strokeWidth = strokeWidth,
                        start = Offset(x = 0f, y = 0f + strokeWidth / 2),
                        end = Offset(x = size.width, y = 0f + strokeWidth / 2),
                    )
                }
                .selectableGroup()
                .padding(horizontal = 12.dp),
        content = content,
    )
}

@Composable
fun RowScope.MDSNavigationBarItem(
    selected: Boolean,
    labelText: String,
    @DrawableRes icon: Int,
    onClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .selectable(
                    selected = selected,
                    onClick = onClick,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                )
                .weight(1f),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = icon),
                tint = if (selected) selectedIconColor else unSelectedIconColor,
                contentDescription = null,
            )
            Text(
                text = labelText,
                style = Body2,
                color = if (selected) selectedLabelColor else unSelectedLabelColor,
            )
        }
    }
}

private object MDSNavigationBarItemDefaults {
    val selectedIconColor = Blue04
    val unSelectedIconColor = Gray04
    val selectedLabelColor = Blue04
    val unSelectedLabelColor = Gray04
}

@Preview(showBackground = true)
@Composable
fun MDSNavigationBarPreview() {
    MDSNavigationBar(
        modifier = Modifier,
        content = {
            MDSNavigationBarItem(
                selected = false,
                labelText = "장부",
                icon = R.drawable.ic_record,
                onClick = {},
            )
            MDSNavigationBarItem(
                selected = false,
                labelText = "마이몽",
                icon = R.drawable.ic_mymong,
                onClick = {},
            )
        },
    )
}
