package com.moneymong.moneymong.feature.agency.search.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import com.moneymong.moneymong.common.ui.noRippleClickable
import com.moneymong.moneymong.design_system.theme.Gray10
import com.moneymong.moneymong.design_system.theme.Heading1
import com.moneymong.moneymong.design_system.R as MDSR

@Composable
internal fun AgencySearchTopBar(
    modifier: Modifier = Modifier,
    onSearchIconClick: () -> Unit,
    visibleSearchIcon: Boolean,
) {
    val animationSpec = tween<Float>(
        durationMillis = 300, easing = FastOutSlowInEasing
    )
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 16.dp),
            text = "소속 찾기",
            color = Gray10,
            style = Heading1
        )
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.CenterEnd),
            visible = visibleSearchIcon,
            enter = fadeIn(animationSpec = animationSpec),
            exit = fadeOut(animationSpec = animationSpec)
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .noRippleClickable { onSearchIconClick() },
                imageVector = ImageVector.vectorResource(id = MDSR.drawable.ic_search),
                contentDescription = "검색",
            )
        }
    }
}

@Preview
@Composable
private fun AgencySearchTopBarPreview() {
    AgencySearchTopBar(
        onSearchIconClick = {},
        visibleSearchIcon = true
    )
}