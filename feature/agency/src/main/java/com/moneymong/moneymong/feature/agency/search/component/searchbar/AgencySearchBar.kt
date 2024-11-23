package com.moneymong.moneymong.feature.agency.search.component.searchbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    onSearch: () -> Unit,
    onCancel: () -> Unit,
    onClear: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AgencySearchTextField(
            modifier = Modifier.weight(1f),
            state = state,
            onSearch = onSearch,
            onClear = onClear
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

@Preview
@Composable
private fun SearchBarPreview() {
    MMTheme {
        AgencySearchBar(
            state = rememberTextFieldState(),
            onSearch = {},
            onClear = {},
            onCancel = {},
        )
    }
}