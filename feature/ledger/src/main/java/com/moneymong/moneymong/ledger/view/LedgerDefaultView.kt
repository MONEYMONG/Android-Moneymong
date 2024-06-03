package com.moneymong.moneymong.ledger.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.common.ui.noRippleClickable
import com.moneymong.moneymong.common.ui.toWonFormat
import com.moneymong.moneymong.design_system.R.drawable
import com.moneymong.moneymong.design_system.component.chip.MDSChip
import com.moneymong.moneymong.design_system.loading.LoadingScreen
import com.moneymong.moneymong.design_system.theme.Body2
import com.moneymong.moneymong.design_system.theme.Body3
import com.moneymong.moneymong.design_system.theme.Gray01
import com.moneymong.moneymong.design_system.theme.Gray06
import com.moneymong.moneymong.design_system.theme.Gray07
import com.moneymong.moneymong.design_system.theme.Gray10
import com.moneymong.moneymong.design_system.theme.Heading5
import com.moneymong.moneymong.design_system.theme.White
import com.moneymong.moneymong.ledger.view.item.LedgerTransactionItem
import com.moneymong.moneymong.ledger.view.onboarding.LedgerOnboarding
import com.moneymong.moneymong.ledger.view.onboarding.OnboardingComponentState
import com.moneymong.moneymong.model.ledger.LedgerDetail
import java.time.LocalDate
import java.time.format.DateTimeFormatter

enum class LedgerTransactionType(
    val type: String,
    val description: String,
    val imgRes: Int
) {
    전체(
        type = "ALL",
        description = "장부 기록이 없어요",
        imgRes = drawable.img_expenditure_empty
    ),
    지출(
        type = "EXPENSE",
        description = "지출 기록이 없어요",
        imgRes = drawable.img_expenditure_empty
    ),
    수입(
        type = "INCOME",
        description = "수입 기록이 없어요",
        imgRes = drawable.img_expenditure_empty
    )
}

@Composable
fun LedgerDefaultView(
    modifier: Modifier = Modifier,
    totalBalance: Int,
    ledgerDetails: List<LedgerDetail>,
    transactionType: LedgerTransactionType,
    startDate: LocalDate,
    endDate: LocalDate,
    hasTransaction: Boolean,
    isLoading: Boolean,
    isExistLedger: Boolean,
    isStaff: Boolean,
    onChangeTransactionType: (LedgerTransactionType) -> Unit,
    onClickPeriod: () -> Unit,
    onClickTransactionItem: (Int) -> Unit
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val lazyColumnState = rememberLazyListState()
    val chips = listOf(
        LedgerTransactionType.전체,
        LedgerTransactionType.지출,
        LedgerTransactionType.수입
    )

    var dateRowState by remember { mutableStateOf(OnboardingComponentState()) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(White),
        state = lazyColumnState
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "이만큼 남았어요",
                        style = Body3,
                        color = Gray07
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = totalBalance.toString().toWonFormat(true),
                        style = Heading5,
                        color = Gray10
                    )
                }
                Image(
                    modifier = Modifier.size(width = 154.dp, height = 110.dp),
                    painter = painterResource(id = drawable.img_ledger_write),
                    contentDescription = null
                )
            }
            LedgerDefaultDateRow(
                modifier = Modifier.onGloballyPositioned {
                    dateRowState = OnboardingComponentState(
                        offset = it.localToRoot(Offset.Zero),
                        size = it.size
                    )
                },
                startDate = startDate,
                endDate = endDate,
                onClickPeriod = onClickPeriod,
            )
            Spacer(modifier = Modifier.height(20.dp))
            MDSChip(
                modifier = Modifier.padding(start = 20.dp),
                tabs = chips.map { it.name },
                selectedTabIndex = selectedTabIndex,
                onChangeSelectedTabIndex = {
                    selectedTabIndex = it
                    onChangeTransactionType(chips[it])
                }
            )
            Spacer(modifier = Modifier.height(6.dp))
        }
        if (isLoading) {
            item {
                Spacer(modifier = Modifier.height(121.dp))
                LoadingScreen(modifier = Modifier.fillMaxSize())
            }
        } else {
            if (!isExistLedger && isStaff) {
                item {
                    Spacer(modifier = Modifier.height(75.dp))
                    LedgerStaffEmptyView()
                }
            } else {
                if (hasTransaction) {
                    itemsIndexed(ledgerDetails) { index, item ->
                        LedgerTransactionItem(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            ledgerDetail = item,
                            onClickTransactionItem = onClickTransactionItem
                        )
                    }
                } else {
                    item {
                        Spacer(modifier = Modifier.height(121.dp))
                        LedgerTransactionEmptyView(
                            text = transactionType.description,
                            image = transactionType.imgRes
                        )
                    }
                }
            }
        }
    }
    LedgerOnboarding(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Gray10.copy(alpha = 0.7f)),
        isStaff = isStaff,
        dateComponent = dateRowState,
        addComponent = OnboardingComponentState(),
        onDismiss = {}
    )
}

@Composable
internal fun LedgerDefaultDateRow(
    modifier: Modifier = Modifier,
    startDate: LocalDate,
    endDate: LocalDate,
    onClickPeriod: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Gray01)
            .noRippleClickable { onClickPeriod() },
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "${startDate.format(DateTimeFormatter.ofPattern("yyyy년 M월"))} ~ ${
                    endDate.format(
                        DateTimeFormatter.ofPattern("yyyy년 M월")
                    )
                }",
                style = Body2,
                color = Gray06
            )
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = drawable.ic_chevron_bottom),
                contentDescription = null,
                tint = Gray06
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LedgerDefaultPreview() {
    LedgerDefaultView(
        totalBalance = 123123,
        ledgerDetails = emptyList(),
        transactionType = LedgerTransactionType.전체,
        startDate = LocalDate.now(),
        endDate = LocalDate.now(),
        hasTransaction = false,
        isLoading = false,
        isExistLedger = false,
        isStaff = false,
        onChangeTransactionType = {},
        onClickPeriod = {},
        onClickTransactionItem = {}
    )
}