package com.moneymong.moneymong.ledger.view.onboarding

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import com.moneymong.moneymong.common.ui.SystemBarColorController
import com.moneymong.moneymong.design_system.theme.Gray10
import com.moneymong.moneymong.ledger.view.onboarding.popup.LedgerOnboardingPopup
import java.time.LocalDate


internal data class OnboardingComponentState(
    val offset: Offset = Offset.Zero,
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
    addComponent: OnboardingComponentState,
    onDismiss: () -> Unit
) {


    DisposableEffect(key1 = Unit) {
        SystemBarColorController.setSystemBarColors(
            color = Gray10.copy(alpha = 0.7f),
        )

        onDispose {
            SystemBarColorController.initialSystemBarColors()
        }
    }

    var currentPage by remember { mutableStateOf(LedgerOnboardingPage.DATE) }
    val currentDate = LocalDate.now()

    BackHandler {
        when (currentPage) {
            LedgerOnboardingPage.DATE -> {
                onDismiss()
            }

            LedgerOnboardingPage.ADD -> {
                currentPage = LedgerOnboardingPage.DATE
            }
        }
    }

    LedgerOnboardingPopup {
        when (currentPage) {
            LedgerOnboardingPage.DATE -> {
                LedgerOnboardingDatePage(
                    modifier = modifier,
                    dateComponent = dateComponent,
                    currentDate = currentDate,
                    onClickNext = {
                        if (isStaff) {
                            currentPage = LedgerOnboardingPage.ADD
                        } else {
                            onDismiss()
                        }
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