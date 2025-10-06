package com.moneymong.moneymong.design_system.component.tag

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.theme.Blue04
import com.moneymong.moneymong.design_system.theme.Body2
import com.moneymong.moneymong.design_system.theme.Body4
import com.moneymong.moneymong.design_system.theme.Gray03
import com.moneymong.moneymong.design_system.theme.Gray05
import com.moneymong.moneymong.design_system.theme.Gray06
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
    @DrawableRes iconResource: Int? = null,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .border(
                width = 1.4.dp,
                color = Gray03,
                shape = RoundedCornerShape(size = Int.MAX_VALUE.dp)
            )
            .background(
                color = White,
                shape = RoundedCornerShape(size = Int.MAX_VALUE.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = Gray06,
            style = Body4,
        )
        if (iconResource != null) {
            Icon(
                modifier = Modifier
                    .size(18.dp)
                    .noRippleClickable(onClick),
                painter = painterResource(id = iconResource),
                contentDescription = "Tag icon",
                tint = Gray05
            )
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
            iconResource = com.moneymong.moneymong.design_system.R.drawable.ic_pencil
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
            onClick = {},
        )
        MDSOutlineTag(
            text = "tag",
            iconResource = com.moneymong.moneymong.design_system.R.drawable.ic_close_default,
            onClick = {},
        )
    }
}
