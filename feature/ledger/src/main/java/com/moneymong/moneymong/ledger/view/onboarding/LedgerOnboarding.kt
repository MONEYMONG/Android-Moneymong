package com.moneymong.moneymong.ledger.view.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import java.time.LocalDate


internal data class OnboardingComponentState(
    val offset: Offset = Offset(0f, 0f),
    val size: IntSize = IntSize.Zero
)

internal enum class LedgerOnboardingPage {
    DATE,
    ADD
}

@Composable
internal fun LedgerOnboarding(
    modifier: Modifier = Modifier,
    isStaff: Boolean,
    dateComponent: OnboardingComponentState,
    addComponent: OnboardingComponentState = OnboardingComponentState(),
    onDismiss: () -> Unit
) {
    var currentPage by remember { mutableStateOf(LedgerOnboardingPage.DATE) }
    val currentDate = LocalDate.now()

    Popup(
        properties = PopupProperties(focusable = true),
        onDismissRequest = { onDismiss() },
    ) {
        when (currentPage) {
            LedgerOnboardingPage.DATE -> {
                LedgerOnboardingDatePage(
                    modifier = modifier,
                    dateComponent = dateComponent,
                    currentDate = currentDate,
                    onClickNext = {
                        currentPage = LedgerOnboardingPage.ADD
                    }
                )
            }

            LedgerOnboardingPage.ADD -> {
                LedgerOnboardingAddPage(
                    modifier = modifier,
                    addComponent = addComponent,
                    onClickNext = {
                        onDismiss()
                    },
                    onClickPrevious = {
                        currentPage = LedgerOnboardingPage.DATE
                    }
                )
            }
        }
    }
}