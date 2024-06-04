package com.moneymong.moneymong.ledger.view.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun LedgerOnboardingAddPage(
    modifier: Modifier = Modifier,
    addComponent: OnboardingComponentState,
    onClickNext: () -> Unit,
    onClickPrevious: () -> Unit
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(all = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LedgerOnboardingPreviousButton(
                onClick = onClickPrevious
            )
            LedgerOnboardingNextButton(
                onClick = onClickNext,
                text = "확인"
            )
        }
    }
}