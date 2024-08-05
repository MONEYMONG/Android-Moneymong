package com.moneymong.moneymong.ledger.view.onboarding.popup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalView

@Composable
internal fun LedgerOnboardingPopup(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    val parentComposition = rememberCompositionContext()
    val currentContent by rememberUpdatedState(content)
    val popupLayout = remember {
        LedgerOnboardingPopupLayout(
            composeView = view
        ).apply {
            setContent(parentComposition) {
                currentContent()
            }
        }
    }

    DisposableEffect(key1 = popupLayout) {
        popupLayout.show()

        onDispose {
            popupLayout.disposeComposition()
            popupLayout.dismiss()
        }
    }
}



