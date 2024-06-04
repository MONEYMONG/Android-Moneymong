package com.moneymong.moneymong.ledger.view.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.ledger.view.LedgerDefaultDateRow
import java.time.LocalDate

@Composable
internal fun LedgerOnboardingDatePage(
    modifier: Modifier = Modifier,
    dateComponent: OnboardingComponentState,
    currentDate: LocalDate,
    onClickNext: () -> Unit
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset {
                    IntOffset(
                        x = dateComponent.offset.x.toInt(),
                        y = dateComponent.offset.y.toInt()
                    )
                },
        ) {
            LedgerDefaultDateRow(
                currentDate = currentDate,
                onAddMonthFromCurrentDate = {}
            )
            Spacer(modifier = Modifier.height(10.dp))
            LedgerOnboardingToolTip(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "장부 열람 기간을 설정할 수 있어요!",
                verticalArrowPosition = VerticalArrowPosition.TOP,
                horizontalArrowPosition = HorizontalArrowPosition.CENTER
            )
        }
        LedgerOnboardingNextButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
            onClick = onClickNext,
            text = "확인"
        )
    }
}