package com.moneymong.moneymong.ledger.view.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
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
            LedgerDefaultDateRow(
                modifier = Modifier.offset {
                    IntOffset(
                        x = dateComponent.offset.x.toInt(),
                        y = dateComponent.offset.y.toInt()
                    )
                },
                currentDate = currentDate,
                onAddMonthFromCurrentDate = {}
            )
        }
    }
}