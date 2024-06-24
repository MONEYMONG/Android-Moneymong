package com.moneymong.moneymong.ledger.view.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.R
import com.moneymong.moneymong.design_system.theme.Blue04
import com.moneymong.moneymong.design_system.theme.Body3
import com.moneymong.moneymong.design_system.theme.Gray01

internal enum class VerticalArrowPosition {
    TOP,
    BOTTOM,
}

internal enum class HorizontalArrowPosition {
    START,
    CENTER,
    END,
}

@Composable
internal fun LedgerOnboardingToolTip(
    modifier: Modifier = Modifier,
    text: String,
    verticalArrowPosition: VerticalArrowPosition,
    horizontalArrowPosition: HorizontalArrowPosition,
) {
    val horizontalSpacing = 18.dp
    val horizontalAlignment = when (horizontalArrowPosition) {
        HorizontalArrowPosition.START -> Alignment.Start
        HorizontalArrowPosition.END -> Alignment.End
        HorizontalArrowPosition.CENTER -> Alignment.CenterHorizontally
    }

    val arrowIcon: @Composable ColumnScope.(Modifier) -> Unit = { arrowModifier ->
        Icon(
            modifier = arrowModifier
                .align(horizontalAlignment)
                .padding(horizontal = horizontalSpacing),
            painter = painterResource(id = R.drawable.ic_polygon),
            contentDescription = "Arrow",
            tint = Gray01
        )
    }

    Column(modifier = modifier) {
        if (verticalArrowPosition == VerticalArrowPosition.TOP) {
            arrowIcon(Modifier.rotate(degrees = 180f))
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(size = 8.dp))
                .background(color = Gray01),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp),
                text = text,
                style = Body3,
                color = Blue04
            )
        }

        if (verticalArrowPosition == VerticalArrowPosition.BOTTOM) {
            arrowIcon(Modifier)
        }
    }
}