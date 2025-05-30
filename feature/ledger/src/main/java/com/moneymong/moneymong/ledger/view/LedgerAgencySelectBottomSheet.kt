package com.moneymong.moneymong.ledger.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.component.button.MDSButton
import com.moneymong.moneymong.design_system.component.button.MDSButtonSize
import com.moneymong.moneymong.design_system.component.button.MDSButtonType
import com.moneymong.moneymong.design_system.theme.MMHorizontalSpacing
import com.moneymong.moneymong.ledger.view.item.LedgerAgencySelectItem
import com.moneymong.moneymong.model.agency.MyAgencyResponse
import com.moneymong.moneymong.ui.pxToDp
import com.moneymong.moneymong.design_system.R as MDSR

@Composable
internal fun LedgerSelectBottomSheet(
    modifier: Modifier = Modifier,
    currentAgencyId: Int,
    agencyList: List<MyAgencyResponse>,
    onClickItem: (agencyId: Int) -> Unit,
    onClickInvitation: () -> Unit,
    onClickCreateLedger: () -> Unit
) {
    Column(
        modifier = modifier.padding(horizontal = MMHorizontalSpacing)
    ) {
        if (agencyList.isNotEmpty()) {
            Spacer(modifier = Modifier.height(20.dp))
        }

        LedgerAgencySelectItems(
            currentAgencyId = currentAgencyId,
            agencyList = agencyList,
            onClickItem = onClickItem
        )
        Spacer(modifier = Modifier.height(16.dp))
        MDSButton(
            modifier = Modifier.fillMaxWidth(),
            text = "초대코드 입력하기",
            type = MDSButtonType.THIRDARY,
            size = MDSButtonSize.LARGE,
            iconResource = MDSR.drawable.ic_pencil,
            onClick = onClickInvitation,
        )
        Spacer(modifier = Modifier.height(12.dp))
        MDSButton(
            modifier = Modifier.fillMaxWidth(),
            text = "새로운 장부 만들기",
            type = MDSButtonType.PRIMARY,
            size = MDSButtonSize.LARGE,
            iconResource = MDSR.drawable.ic_plus_outline,
            onClick = onClickCreateLedger,
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun LedgerAgencySelectItems(
    currentAgencyId: Int,
    agencyList: List<MyAgencyResponse>,
    onClickItem: (agencyId: Int) -> Unit,
) {
    var itemHeight by remember { mutableIntStateOf(0) }
    val itemSpace = 12.dp
    val maxVisibleItemCount = 3

    Column(
        modifier = Modifier
            .height(
                if (itemHeight > 0 && agencyList.size > maxVisibleItemCount) {
                    (itemHeight.pxToDp * maxVisibleItemCount) + itemSpace * (maxVisibleItemCount - 1)
                } else {
                    Dp.Unspecified
                }
            )
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(space = itemSpace),
    ) {
        agencyList.forEach { item ->
            LedgerAgencySelectItem(
                modifier = Modifier.onGloballyPositioned {
                    if (itemHeight == 0) {
                        itemHeight = it.size.height
                    }
                },
                agencyResponse = item,
                currentAgency = currentAgencyId == item.id,
                onClick = onClickItem
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LedgerSelectBottomSheetPreview() {
    LedgerSelectBottomSheet(
        currentAgencyId = 0,
        agencyList = listOf(
            MyAgencyResponse(
                id = 0,
                name = "국민은행",
                headCount = 0,
                type = "은행"
            ),
            MyAgencyResponse(
                id = 0,
                name = "국민은행",
                headCount = 0,
                type = "은행"
            ),
            MyAgencyResponse(
                id = 0,
                name = "국민은행",
                headCount = 0,
                type = "은행"
            ),
            MyAgencyResponse(
                id = 0,
                name = "국민은행",
                headCount = 0,
                type = "은행"
            )
        ),
        onClickCreateLedger = {},
        onClickInvitation = {},
        onClickItem = {}
    )
}