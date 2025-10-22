package com.moneymong.moneymong.design_system.component.tag

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.R
import com.moneymong.moneymong.design_system.theme.Blue04
import com.moneymong.moneymong.design_system.theme.Body2
import com.moneymong.moneymong.design_system.theme.Body3
import com.moneymong.moneymong.design_system.theme.Gray03
import com.moneymong.moneymong.design_system.theme.Gray05
import com.moneymong.moneymong.design_system.theme.Gray06
import com.moneymong.moneymong.design_system.theme.Gray08
import com.moneymong.moneymong.design_system.theme.White
import com.moneymong.moneymong.ui.noRippleClickable

@Composable
fun MDSTag(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color,
    contentColor: Color,
    @DrawableRes iconResource: Int? = null,
) {
    Row(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(size = Int.MAX_VALUE.dp)
            )
            .padding(horizontal = 8.dp, vertical = 1.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = contentColor,
            style = Body2,
        )
        if (iconResource != null) {
            Icon(
                modifier = Modifier.size(12.dp),
                painter = painterResource(id = iconResource),
                contentDescription = "Tag icon",
                tint = contentColor
            )
        }
    }
}

@Composable
fun MDSOutlineTag(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean = false,
    @DrawableRes iconResource: Int? = null,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .border(
                width = 1.4.dp,
                color = if (selected) Blue04 else Gray03,
                shape = RoundedCornerShape(size = Int.MAX_VALUE.dp)
            )
            .clip(RoundedCornerShape(size = Int.MAX_VALUE.dp))
            .background(
                color = White,
                shape = RoundedCornerShape(size = Int.MAX_VALUE.dp)
            )
            .clickable(
                enabled = iconResource == null,
                onClick = onClick
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = if (selected) Gray08 else Gray06,
            style = Body3,
        )
        when {
            !selected && iconResource != null -> {
                Icon(
                    modifier = Modifier
                        .size(18.dp)
                        .noRippleClickable(onClick),
                    painter = painterResource(id = iconResource),
                    contentDescription = "Tag icon",
                    tint = Gray05
                )
            }

            selected -> {
                Icon(
                    modifier = Modifier
                        .size(18.dp),
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = "Tag icon",
                    tint = Blue04
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MDSTagPreview() {
    Row(
        modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MDSTag(
            text = "tag",
            backgroundColor = Blue04,
            contentColor = White,
        )
        MDSTag(
            text = "tag",
            backgroundColor = Blue04,
            contentColor = White,
            iconResource = R.drawable.ic_pencil
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MDSOutlineTagPreview() {
    Row(
        modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MDSOutlineTag(
            text = "tag",
            selected = false,
            onClick = {},
        )
        MDSOutlineTag(
            text = "tag",
            selected = true,
            iconResource = R.drawable.ic_close_default,
            onClick = {},
        )
    }
}
