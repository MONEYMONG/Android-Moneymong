package com.moneymong.moneymong.feature.agency.search.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.theme.Body4
import com.moneymong.moneymong.design_system.theme.White
import com.moneymong.moneymong.design_system.R
import com.moneymong.moneymong.design_system.theme.Blue04
import com.moneymong.moneymong.design_system.theme.Body2
import com.moneymong.moneymong.design_system.theme.SkyBlue01

@Composable
fun AgencyFeedbackItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val colorStops = arrayOf(
        0.0f to Color(0xFF9181F6),
        0.31f to Color(0xFF5562FF),
        0.67f to Color(0xFFC7C2FF),
        1f to Color(0xFFC4EAFF)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Brush.horizontalGradient(colorStops = colorStops))
            .clickable { onClick() }
            .padding(horizontal = 18.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "머니몽에게 의견 제안하기",
            style = Body4,
            color = White
        )
        Spacer(modifier = Modifier.width(2.dp))
        Image(
            modifier = Modifier.size(36.dp),
            painter = painterResource(id = R.drawable.img_agency_feedback),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(2.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(100.dp))
                .background(SkyBlue01)
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                text = "스벅 기프티콘",
                style = Body2,
                color = Blue04
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AgencyFeedbackItemPreview() {
    AgencyFeedbackItem {
    }
}