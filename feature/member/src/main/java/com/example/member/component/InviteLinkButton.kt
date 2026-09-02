package com.example.member.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.theme.Black
import com.moneymong.moneymong.design_system.theme.Body4
import com.moneymong.moneymong.design_system.theme.Caption
import com.moneymong.moneymong.design_system.theme.White
import com.moneymong.moneymong.ui.noRippleClickable


private val InviteLinkGradientStartColor = Color(0xFF4F83FD)
private val InviteLinkGradientEndColor = Color(0xFF7F5FFF)

@Composable
fun InviteLinkButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .drawWithCache {
                val start = Offset(
                    x = size.width * (182f / 343f),
                    y = size.height * (5f / 46f),
                )

                val end = Offset(
                    x = size.width * (185f / 343f),
                    y = size.height * (34f / 46f),
                )
                val brush = Brush.linearGradient(
                    start = start,
                    end = end,
                    colors = listOf(
                        InviteLinkGradientStartColor,
                        InviteLinkGradientEndColor
                    )
                )

                onDrawBehind {
                    drawRect(brush = brush)
                }
            }
            .border(
                width = 1.dp,
                color = Black.copy(alpha = 0.1f),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(vertical = 10.dp)
            .noRippleClickable(onClick),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .background(color = Black.copy(alpha = 0.2f), shape = RoundedCornerShape(4.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "이걸로 초대해 보세요",
                style = Caption,
                color = White
            )
        }

        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "초대링크 공유하기",
            style = Body4,
            color = White
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun InviteLinkButtonPreview() {

    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        InviteLinkButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { Toast.makeText(context, "안녕하신교~!", Toast.LENGTH_SHORT).show() }
        )
    }
}
