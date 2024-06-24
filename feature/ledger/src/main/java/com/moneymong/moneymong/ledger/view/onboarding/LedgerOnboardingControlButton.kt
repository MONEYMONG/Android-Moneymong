package com.moneymong.moneymong.ledger.view.onboarding

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.common.ui.noRippleClickable
import com.moneymong.moneymong.design_system.R
import com.moneymong.moneymong.design_system.theme.Heading1
import com.moneymong.moneymong.design_system.theme.White

@Composable
internal fun LedgerOnboardingNextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {
    Row(
        modifier = modifier.noRippleClickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = Heading1,
            color = White
        )
        Spacer(modifier = Modifier.width(4.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_right),
            contentDescription = "Next",
            tint = White
        )
    }
}

@Composable
internal fun LedgerOnboardingPreviousButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier.noRippleClickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_left),
            contentDescription = "Previous",
            tint = White
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "이전",
            style = Heading1,
            color = White
        )
    }
}