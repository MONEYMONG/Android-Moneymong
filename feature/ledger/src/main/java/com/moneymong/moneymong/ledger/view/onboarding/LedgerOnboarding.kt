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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.moneymong.moneymong.ledger.view.LedgerDefaultDateRow
import java.time.LocalDate


internal data class OnboardingComponentState(
    val offset: Offset = Offset(0f, 0f),
    val size: IntSize = IntSize.Zero
)

@Composable
internal fun LedgerOnboarding(
    modifier: Modifier = Modifier,
    isStaff: Boolean,
    dateComponent: OnboardingComponentState,
    addComponent: OnboardingComponentState = OnboardingComponentState()
) {

    val currentDate = LocalDate.now()

    Popup {
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
                onClick = { /*TODO*/ },
                text = "확인"
            )
        }
    }
}