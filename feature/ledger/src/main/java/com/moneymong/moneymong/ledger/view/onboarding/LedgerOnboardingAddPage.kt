package com.moneymong.moneymong.ledger.view.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.R
import com.moneymong.moneymong.design_system.component.button.MDSFloatingActionButton
import com.moneymong.moneymong.design_system.theme.Mint03
import com.moneymong.moneymong.ui.pxToDp

@Composable
internal fun LedgerOnboardingAddPage(
    modifier: Modifier = Modifier,
    addComponent: OnboardingComponentState,
    onClickNext: () -> Unit,
    onClickPrevious: () -> Unit
) {
    Box(modifier = modifier) {
        LedgerOnboardingToolTip(
            modifier = Modifier.layout { measurable, constraints ->
                val placeable = measurable.measure(constraints)
                layout(placeable.width, placeable.height) {
                    val tooltipX =
                        addComponent.offset.x.toInt() + addComponent.size.width - placeable.width
                    val tooltipY =
                        addComponent.offset.y.toInt() - placeable.height - 10.dp.roundToPx()
                    placeable.placeRelative(
                        x = tooltipX,
                        y = tooltipY
                    )
                }
            },
            text = "장부 내역을 등록해보세요!",
            verticalArrowPosition = VerticalArrowPosition.BOTTOM,
            horizontalArrowPosition = HorizontalArrowPosition.END
        )
        MDSFloatingActionButton(
            modifier = Modifier
                .size(width = addComponent.size.width.pxToDp, addComponent.size.height.pxToDp)
                .offset {
                    IntOffset(
                        x = addComponent.offset.x.toInt(),
                        y = addComponent.offset.y.toInt()
                    )
                },
            iconResource = R.drawable.ic_plus_default,
            containerColor = Mint03,
            onClick = {}
        )
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