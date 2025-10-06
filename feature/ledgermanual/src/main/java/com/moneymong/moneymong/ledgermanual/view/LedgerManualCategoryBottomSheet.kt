package com.moneymong.moneymong.ledgermanual.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.theme.MMHorizontalSpacing
import com.moneymong.moneymong.design_system.R
import com.moneymong.moneymong.design_system.component.bottomSheet.MDSBottomSheet
import com.moneymong.moneymong.design_system.component.tag.MDSOutlineTag
import com.moneymong.moneymong.design_system.theme.Blue04
import com.moneymong.moneymong.design_system.theme.Body2
import com.moneymong.moneymong.design_system.theme.Body3
import com.moneymong.moneymong.design_system.theme.Gray05
import com.moneymong.moneymong.design_system.theme.Gray10
import com.moneymong.moneymong.design_system.theme.Heading4
import com.moneymong.moneymong.design_system.theme.White
import com.moneymong.moneymong.ui.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LedgerManualCategoryBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    categories: List<String>, // TODO API response
    onDismissRequest: () -> Unit
) {
    MDSBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
    ) {
        LedgerManualCategoryBottomSheetContent(
            categories = categories,
            onDismissRequest = onDismissRequest,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LedgerManualCategoryBottomSheetContent(
    modifier: Modifier = Modifier,
    categories: List<String>,
    onDismissRequest: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(448.dp)
            .background(White)
            .padding(horizontal = MMHorizontalSpacing, vertical = 20.dp),
    ) {
        Icon(
            modifier = Modifier
                .align(alignment = Alignment.End)
                .noRippleClickable(onDismissRequest),
            painter = painterResource(R.drawable.ic_close_default),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                style = Heading4,
                color = Gray10,
                text = "카테고리",
            )
            Text(
                style = Body3,
                color = Blue04,
                text = "추가",
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            style = Body2,
            color = Gray05,
            text = "원하는 카테고리를 마음대로 만들 수 있어요",
        )
        Spacer(modifier = Modifier.height(16.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            categories.forEach {
                MDSOutlineTag(
                    text = it,
                    iconResource = R.drawable.ic_close_default,
                    onClick = {},
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LedgerManualCategoryBottomSheetContentPreview() {
    val categories = listOf("testTooLongTextOverFlow", "test")

    LedgerManualCategoryBottomSheetContent(categories = categories) {}
}