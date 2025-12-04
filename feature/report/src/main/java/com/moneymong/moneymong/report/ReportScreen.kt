package com.moneymong.moneymong.report

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moneymong.moneymong.design_system.component.tab.MDSTabRow
import com.moneymong.moneymong.design_system.component.tag.MDSTag
import com.moneymong.moneymong.design_system.theme.Black
import com.moneymong.moneymong.design_system.theme.Blue01
import com.moneymong.moneymong.design_system.theme.Blue04
import com.moneymong.moneymong.design_system.theme.Body2
import com.moneymong.moneymong.design_system.theme.Body3
import com.moneymong.moneymong.design_system.theme.Caption
import com.moneymong.moneymong.design_system.theme.Gray01
import com.moneymong.moneymong.design_system.theme.Gray04
import com.moneymong.moneymong.design_system.theme.Gray05
import com.moneymong.moneymong.design_system.theme.Gray06
import com.moneymong.moneymong.design_system.theme.Gray07
import com.moneymong.moneymong.design_system.theme.Gray10
import com.moneymong.moneymong.design_system.theme.Heading1
import com.moneymong.moneymong.design_system.theme.Heading3
import com.moneymong.moneymong.design_system.theme.Heading4
import com.moneymong.moneymong.design_system.theme.Heading5
import com.moneymong.moneymong.design_system.theme.MMHorizontalSpacing
import com.moneymong.moneymong.design_system.theme.Red01
import com.moneymong.moneymong.design_system.theme.Red03
import com.moneymong.moneymong.design_system.theme.SkyBlue01
import com.moneymong.moneymong.design_system.theme.White
import com.moneymong.moneymong.report.component.ReportTopBar
import com.moneymong.moneymong.report.model.AmountType
import com.moneymong.moneymong.report.model.CategoryAmountItem
import com.moneymong.moneymong.report.model.MemberAmount
import com.moneymong.moneymong.report.model.mockCategoryAmounts
import com.moneymong.moneymong.report.model.mockMemberAmounts
import com.moneymong.moneymong.ui.toWonFormat
import com.moneymong.moneymong.design_system.R as MDSR


@Composable
fun ReportRoute(
    modifier: Modifier = Modifier,
    viewModel: ReportViewModel = hiltViewModel()
) {
    ReportScreen(modifier = modifier)
}


@Composable
private fun ReportScreen(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .background(color = Gray01)
    ) {
        ReportTopBar(modifier = Modifier.fillMaxWidth()) { /* todo */ }
        ReportSummary(modifier = Modifier.padding(horizontal = MMHorizontalSpacing))
        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .background(color = White)
                .padding(horizontal = MMHorizontalSpacing)
        ) {
            ReportContent()
            Spacer(modifier = Modifier.height(32.dp))
            MemberReport()
            Spacer(modifier = Modifier.height(32.dp))
            CategoryReport()
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun ReportSummary(
    modifier: Modifier = Modifier,
    balance: Int = 50000, // todo
    income: Int = 100000, // todo
    expense: Int = 100000 // todo
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = White)
            .padding(vertical = 20.dp, horizontal = 24.dp),
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.weight(1f),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Blue04)) {
                        append("$balance")
                    }
                    append("원\n남아 있어요!")
                },
                color = Gray10,
                style = Heading5
            )
            Image(
                modifier = Modifier.size(80.dp),
                painter = painterResource(MDSR.drawable.img_record),
                contentDescription = "레포트 이미지"
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SummaryItem(amount = income)
            SummaryItem(amount = expense)
        }
    }
}

@Composable
private fun SummaryItem(
    modifier: Modifier = Modifier,
    amount: Int,
    // type: AmountType
) {
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .background(color = Gray01)
            .padding(vertical = 12.dp, horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "총 수입",
            color = Gray06,
            style = Body2
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "+${amount.toString().toWonFormat()}원",
            color = Gray10,
            style = Heading1
        )
    }
}

@Composable
private fun ReportContent(
    modifier: Modifier = Modifier,
    yearMonth: Pair<Int, Int> = Pair(2025, 6),
    monthlyIncome: Int = 500000,
    monthlyExpense: Int = 400000,
    monthlyIncomePercent: Float = 70.2f,
    monthlyExpensePercent: Float = 85.3f
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = MDSR.drawable.ic_chevron_left),
                contentDescription = "이전 달 레포트 확인하기",
                tint = Gray06
            )
            Text(
                text = "${yearMonth.first}. ${yearMonth.second}",
                color = Black,
                style = Heading5
            )
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = MDSR.drawable.ic_chevron_right),
                contentDescription = "다음 달 레포트 확인하기",
                tint = Gray06
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MonthlyItem(
                modifier = Modifier.weight(1f),
                month = yearMonth.second,
                monthlyAmount = monthlyIncome,
                monthlyPercent = monthlyIncomePercent.toInt()
            )
            MonthlyItem(
                modifier = Modifier.weight(1f),
                month = yearMonth.second,
                monthlyAmount = monthlyExpense,
                monthlyPercent = monthlyExpensePercent.toInt()
            )
        }
    }
}

@Composable
private fun MonthlyItem(
    modifier: Modifier = Modifier,
    month: Int,
    monthlyAmount: Int,
    monthlyPercent: Int,
    // type: AmountType
) {
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(size = 12.dp))
            .background(color = Gray01)
            .padding(vertical = 12.dp, horizontal = 16.dp),
    ) {
        Text(
            text = "${month}월 수입",
            color = Blue04,
            style = Body2
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "+${monthlyAmount.toString().toWonFormat()}원",
            color = Gray10,
            style = Heading3
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "총 수입이 $monthlyPercent%를 차지",
            color = Gray06,
            style = Body2
        )
    }
}


@Composable
private fun MemberReport(
    modifier: Modifier = Modifier,
    memberAmounts: List<MemberAmount> = mockMemberAmounts
) {
    Column(modifier = modifier) {
        Text(
            text = "멤버별로 얼마나 쓰고 있을까?",
            color = Gray10,
            style = Heading4
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(size = 20.dp))
                .background(color = Gray01)
                .padding(all = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            memberAmounts.forEach { memberAmount ->
                MemberItem(memberAmount = memberAmount)
            }
        }
    }
}

@Composable
private fun MemberItem(
    modifier: Modifier = Modifier,
    memberAmount: MemberAmount
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier
                    .background(color = SkyBlue01, shape = CircleShape)
                    .padding(4.dp)
                    .size(32.dp),
                painter = painterResource(MDSR.drawable.img_auditor),
                contentDescription = "멤버 아이콘",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = memberAmount.name,
                color = Gray10,
                style = Heading1
            )
        }

        AmountType.entries.forEach { amountType ->
            val label: String = amountType.label
            val tagBackgroundColor: Color = if (amountType == AmountType.INCOME) Blue04 else Red03
            val tagContentColor: Color = if (amountType == AmountType.INCOME) White else Red01
            val amount = if (amountType == AmountType.INCOME) memberAmount.income else memberAmount.expense
            val percent = if (amountType == AmountType.INCOME) memberAmount.incomePercent else memberAmount.expensePercent

            Row {
                Text(text = label, color = Gray05, style = Body3)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "${amount.toString().toWonFormat()}원", color = Gray06, style = Body3)
                Spacer(modifier = Modifier.width(8.dp))
                MDSTag(
                    text = "${percent}%",
                    backgroundColor = tagBackgroundColor,
                    contentColor = tagContentColor
                )
            }
        }
    }
}

@Composable
private fun CategoryReport(
    modifier: Modifier = Modifier
) {
    var categoryAmountType: AmountType by remember { mutableStateOf(AmountType.EXPENSE) }
    val categoryAmountTypes = remember { AmountType.entries.reversed() }

    Column(modifier = modifier) {
        Text(
            text = "카테고리별 이만큼 사용하고 있어요",
            color = Gray10,
            style = Heading4
        )
        Spacer(modifier = Modifier.height(8.dp))
        MDSTabRow(
            tabs = categoryAmountTypes.map { it.label },
            selectedTabIndex = categoryAmountTypes.indexOf(categoryAmountType),
            onChangeSelectedTabIndex = { categoryAmountType = categoryAmountTypes[it] }
        )
        Spacer(modifier = Modifier.height(20.dp))
        CategoryReportContent(amountType = categoryAmountType)
    }
}


@Composable
private fun CategoryReportContent(
    modifier: Modifier = Modifier,
    month: Int = 12,
    amountType: AmountType,
    categoryAmountItems: List<CategoryAmountItem> = mockCategoryAmounts
        .map { it.toCategoryAmountItem(type = amountType) }
        .sortedByDescending { it.amount }
) {
    val extraCategoryVisibleOffset = 3

    Column(modifier = modifier) {
        Text(
            text = buildAnnotatedString {
                append("${month}월 동안\n")
                withStyle(style = SpanStyle(color = Blue04)) {
                    append(categoryAmountItems.first().name)
                }
                append("에서 ${amountType.label}이 가장 많아요")
            },
            color = Gray10,
            style = Heading1
        )
        Spacer(modifier = Modifier.height(28.dp))

        Row(
            modifier = Modifier.padding(horizontal = 33.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            val stickColors = listOf(Blue04, Blue01, SkyBlue01)

            categoryAmountItems.take(3).forEachIndexed { idx, categoryAmountItem ->
                CategoryAmountStick(
                    modifier = Modifier.weight(1f),
                    name = categoryAmountItem.name,
                    amount = categoryAmountItem.amount,
                    percent = categoryAmountItem.percent,
                    color = stickColors[idx]
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (categoryAmountItems.size > extraCategoryVisibleOffset) {
            Column(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(size = 20.dp))
                    .background(color = Gray01)
                    .padding(all = 20.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                categoryAmountItems.drop(3).forEach { categoryAmountItem ->
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = categoryAmountItem.name,
                                color = Gray07,
                                style = Heading1
                            )
                            Text(
                                text = "${categoryAmountItem.amount.toString().toWonFormat()}원",
                                color = Gray07,
                                style = Heading1
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "${categoryAmountItem.percent}%",
                            color = Gray04,
                            style = Body3
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun CategoryAmountStick(
    modifier: Modifier,
    name: String,
    amount: Int,
    percent: Float,
    color: Color
) {
    val minHeight = 20
    val maxHeight = 174
    var targetHeight: Float by remember { mutableFloatStateOf(value = 0f) }
    val animatedHeight by animateFloatAsState(targetValue = targetHeight)

    LaunchedEffect(key1 = percent) {
        targetHeight = minHeight + (maxHeight - minHeight) * (percent / 100)
    }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "${amount.toString().toWonFormat()}원", color = Gray10, style = Heading1)
        Spacer(modifier = Modifier.height(9.dp))
        Box(
            modifier = Modifier
                .width(44.dp)
                .height(animatedHeight.dp)
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .background(color = color)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = name, color = Gray10, style = Body3)
        Text(text = "${percent}%", color = Gray04, style = Caption)
    }

}

@Preview
@Composable
private fun ReportScreenPreview() {
    ReportScreen()
}