package com.moneymong.moneymong.feature.agency.search.component.searchbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.common.ui.noRippleClickable
import com.moneymong.moneymong.design_system.theme.Body2
import com.moneymong.moneymong.design_system.theme.Gray08
import com.moneymong.moneymong.design_system.theme.MMTheme

@Composable
internal fun AgencySearchBar(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    visible: Boolean,
    onSearch: () -> Unit,
    onCancel: () -> Unit,
    onClear: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val animationSpec = tween<Float>(
        durationMillis = 300,
        easing = FastOutSlowInEasing
    )

    LaunchedEffect(key1 = visible) {
        if (visible) {
            focusRequester.requestFocus()
        }
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = fadeIn(animationSpec = animationSpec),
        exit = fadeOut(animationSpec = animationSpec)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AgencySearchTextField(
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                state = state,
                onSearch = {
                    onSearch()
                    focusManager.clearFocus()
                },
                onClear = {
                    onClear()
                    focusRequester.requestFocus()
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                modifier = Modifier.noRippleClickable { onCancel() },
                text = "취소",
                style = Body2,
                color = Gray08
            )
        }
    }
}

@Preview
@Composable
private fun SearchBarPreview() {
    MMTheme {
        AgencySearchBar(
            state = rememberTextFieldState(),
            visible = true,
            onSearch = {},
            onClear = {},
            onCancel = {},
        )
    }
}