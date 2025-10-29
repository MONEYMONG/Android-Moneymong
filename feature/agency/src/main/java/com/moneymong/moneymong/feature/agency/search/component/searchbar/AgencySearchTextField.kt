package com.moneymong.moneymong.feature.agency.search.component.searchbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.ui.noRippleClickable
import com.moneymong.moneymong.design_system.theme.Blue04
import com.moneymong.moneymong.design_system.theme.Body2
import com.moneymong.moneymong.design_system.theme.Gray04
import com.moneymong.moneymong.design_system.theme.Gray05
import com.moneymong.moneymong.design_system.theme.Gray09
import com.moneymong.moneymong.design_system.theme.MMTheme
import com.moneymong.moneymong.design_system.theme.White
import com.moneymong.moneymong.design_system.R as MDSR

@Composable
internal fun AgencySearchTextField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    onSearch: () -> Unit,
    onClear: () -> Unit,
) {

    BasicTextField(
        modifier = modifier,
        state = state,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        onKeyboardAction = { onSearch() },
        decorator = { innerTextField ->
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = White)
                    .border(width = 1.dp, color = Blue04, shape = RoundedCornerShape(8.dp))
                    .padding(vertical = 10.dp, horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box {
                    innerTextField()
                    if (state.text.isEmpty()) {
                        Text(
                            text = "소속을 검색해 보세요",
                            color = Gray05,
                            style = Body2
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .noRippleClickable { onClear() },
                    imageVector = ImageVector.vectorResource(id = MDSR.drawable.ic_close_default),
                    contentDescription = "Clear Search Text",
                    tint = Gray04,
                )
            }
        },
        textStyle = Body2.copy(color = Gray09)
    )
}

@Preview
@Composable
private fun SearchTextFieldPreview() {
    MMTheme {
        AgencySearchTextField(
            state = rememberTextFieldState(),
            onSearch = { },
            onClear = { }
        )
    }
}