package com.moneymong.moneymong.ledger.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.theme.MMHorizontalSpacing
import com.moneymong.moneymong.ledger.view.item.LedgerAgencySelectItem
import com.moneymong.moneymong.model.agency.MyAgencyResponse

@Composable
fun LedgerAgencySelectBottomSheet(
    modifier: Modifier = Modifier,
    currentAgencyId: Int,
    agencyList: List<MyAgencyResponse>,
    onClickItem: (agencyId: Int) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(vertical = 24.dp, horizontal = MMHorizontalSpacing),
        verticalArrangement = Arrangement.spacedBy(space = 12.dp)
    ) {
        agencyList.forEachIndexed { index, item ->
            LedgerAgencySelectItem(
                agencyResponse = item,
                currentAgency = currentAgencyId == item.id,
                onClick = onClickItem
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LedgerAgencySelectBottomSheetPreview() {
    LedgerAgencySelectBottomSheet(
        currentAgencyId = 0,
        agencyList = listOf(MyAgencyResponse(0, "asddfkjfdsafhadskfjahdfkjldashfkdaslsdahfjk", 1, ""))
    ) {}
}