package com.moneymong.moneymong.feature.agency.search.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.theme.Gray10
import com.moneymong.moneymong.design_system.theme.Heading1
import com.moneymong.moneymong.design_system.R as MDSR

@Composable
internal fun AgencySearchTopBar(
    modifier: Modifier = Modifier,
    onSearchIconClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "소속 찾기",
            color = Gray10,
            style = Heading1
        )
        Icon(
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterEnd),
            imageVector = ImageVector.vectorResource(id = MDSR.drawable.ic_search),
            contentDescription = "검색",
        )
    }
}

@Preview
@Composable
private fun AgencySearchTopBarPreview() {
    AgencySearchTopBar(onSearchIconClick = {})
}