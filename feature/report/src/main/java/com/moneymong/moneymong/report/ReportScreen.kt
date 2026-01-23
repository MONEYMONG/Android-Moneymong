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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moneymong.moneymong.design_system.component.tab.MDSTabRow
import com.moneymong.moneymong.design_system.component.tag.MDSTag
import com.moneymong.moneymong.design_system.error.ErrorScreen
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
import com.moneymong.moneymong.report.model.CategoryReport
import com.moneymong.moneymong.report.model.CategoryReportItem
import com.moneymong.moneymong.report.model.MemberReport
import com.moneymong.moneymong.report.model.toCategoryReportItemsWithSort
import com.moneymong.moneymong.ui.noRippleClickable
import com.moneymong.moneymong.ui.toWonFormat
import java.time.YearMonth
import com.moneymong.moneymong.design_system.R as MDSR


@Composable
fun ReportRoute(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    viewModel: ReportViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    when {
        uiState.errorMessage != null -> {
            ErrorScreen(
                modifier = modifier,
                message = uiState.errorMessage,
                onRetry = viewModel::fetchReport
            )
        }

        else -> {
            ReportScreen(
                modifier = modifier,
                navigateUp = navigateUp,
                selectYearMonth = uiState.selectYearMonth,
                reportData = uiState.reportData,
                updateReportToPreviousMonth = viewModel::updateReportToPreviousMonth,
                updateReportToNextMonth = viewModel::updateReportToNextMonth
            )
        }
    }
}


@Composable
private fun ReportScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    selectYearMonth: YearMonth,
    reportData: ReportUiData,
    updateReportToPreviousMonth: () -> Unit,
    updateReportToNextMonth: () -> Unit
) {

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .background(color = Gray01)
    ) {
        ReportTopBar(
            modifier = Modifier.fillMaxWidth(),
            onClose = navigateUp
        )
        ReportSummary(
            modifier = Modifier.padding(horizontal = MMHorizontalSpacing),
            balance = reportData.totalReport.balance,
            income = reportData.totalReport.income,
            expense = reportData.totalReport.expense
        )
        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .background(color = White)
                .padding(horizontal = MMHorizontalSpacing)
        ) {
            ReportContent(
                yearMonth = selectYearMonth,
                monthlyIncome = reportData.monthlyReport.income,
                monthlyExpense = reportData.monthlyReport.expense,
                monthlyIncomePercent = reportData.monthlyReport.incomePercent,
                monthlyExpensePercent = reportData.monthlyReport.expensePercent,
                updateToPreviousMonth = updateReportToPreviousMonth,
                updateToNextMonth = updateReportToNextMonth
            )
            Spacer(modifier = Modifier.height(32.dp))
            if (reportData.memberReports.isNotEmpty()) {
                MemberReport(memberReports = reportData.memberReports)
                Spacer(modifier = Modifier.height(32.dp))
            }
            if (reportData.categoryReports.isNotEmpty()) {
                CategoryReport(
                    selectMont = selectYearMonth.monthValue,
                    categoryReports = reportData.categoryReports
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun ReportSummary(
    modifier: Modifier = Modifier,
    balance: Long,
    income: Long,
    expense: Long
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = White)
            .padding(vertical = 20.dp, horizontal = 24.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
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
            SummaryItem(amount = income, type = AmountType.INCOME)
            SummaryItem(amount = expense, type = AmountType.EXPENSE)
        }
    }
}

@Composable
private fun SummaryItem(
    modifier: Modifier = Modifier,
    amount: Long,
    type: AmountType
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
            text = "${type.symbol}${amount.toString().toWonFormat()}원",
            color = Gray10,
            style = Heading1
        )
    }
}

@Composable
private fun ReportContent(
    modifier: Modifier = Modifier,
    yearMonth: YearMonth,
    monthlyIncome: Long,
    monthlyExpense: Long,
    monthlyIncomePercent: Int,
    monthlyExpensePercent: Int,
    updateToPreviousMonth: () -> Unit,
    updateToNextMonth: () -> Unit
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
                modifier = Modifier
                    .size(20.dp)
                    .noRippleClickable(updateToPreviousMonth),
                painter = painterResource(id = MDSR.drawable.ic_chevron_left),
                contentDescription = "이전 달 레포트 확인하기",
                tint = Gray06
            )
            Text(
                text = "${yearMonth.year}. ${yearMonth.monthValue}",
                color = Black,
                style = Heading5
            )
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .noRippleClickable(updateToNextMonth),
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
                month = yearMonth.monthValue,
                monthlyAmount = monthlyIncome,
                monthlyPercent = monthlyIncomePercent,
                type = AmountType.INCOME
            )
            MonthlyItem(
                modifier = Modifier.weight(1f),
                month = yearMonth.monthValue,
                monthlyAmount = monthlyExpense,
                monthlyPercent = monthlyExpensePercent,
                type = AmountType.EXPENSE
            )
        }
    }
}

@Composable
private fun MonthlyItem(
    modifier: Modifier = Modifier,
    month: Int,
    monthlyAmount: Long,
    monthlyPercent: Int,
    type: AmountType
) {
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(size = 12.dp))
            .background(color = Gray01)
            .padding(vertical = 12.dp, horizontal = 16.dp),
    ) {
        Text(
            text = "${month}월 ${type.label}",
            color = Blue04,
            style = Body2
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "${type.symbol}${monthlyAmount.toString().toWonFormat()}원",
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
    memberReports: List<MemberReport>
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
            memberReports.forEach { memberReport ->
                MemberItem(memberReport = memberReport)
            }
        }
    }
}

@Composable
private fun MemberItem(
    modifier: Modifier = Modifier,
    memberReport: MemberReport
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
                text = memberReport.name,
                color = Gray10,
                style = Heading1
            )
        }

        AmountType.entries.forEach { amountType ->
            val label: String = amountType.label
            val tagBackgroundColor: Color = if (amountType == AmountType.INCOME) Blue04 else Red03
            val tagContentColor: Color = if (amountType == AmountType.INCOME) White else Red01
            val amount =
                if (amountType == AmountType.INCOME) memberReport.income else memberReport.expense
            val percent =
                if (amountType == AmountType.INCOME) memberReport.incomePercent else memberReport.expensePercent

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
    modifier: Modifier = Modifier,
    selectMont: Int,
    categoryReports: List<CategoryReport>
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
        CategoryReportContent(
            month = selectMont,
            amountType = categoryAmountType,
            categoryReportItems = categoryReports.toCategoryReportItemsWithSort(type = categoryAmountType)
        )
    }
}


@Composable
private fun CategoryReportContent(
    modifier: Modifier = Modifier,
    month: Int,
    amountType: AmountType,
    categoryReportItems: List<CategoryReportItem>
) {

    val extraCategoryVisibleOffset = 3

    Column(modifier = modifier) {
        Text(
            text = buildAnnotatedString {
                append("${month}월 동안\n")
                withStyle(style = SpanStyle(color = Blue04)) {
                    append(categoryReportItems.first().name)
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

            categoryReportItems.take(3).forEachIndexed { idx, categoryReportItem ->
                CategoryReportStick(
                    modifier = Modifier.weight(1f),
                    name = categoryReportItem.name,
                    amount = categoryReportItem.amount,
                    percent = categoryReportItem.percent,
                    color = stickColors[idx]
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (categoryReportItems.size > extraCategoryVisibleOffset) {
            Column(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(size = 20.dp))
                    .background(color = Gray01)
                    .padding(all = 20.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                categoryReportItems.drop(3).forEach { categoryReportItem ->
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = categoryReportItem.name,
                                color = Gray07,
                                style = Heading1
                            )
                            Text(
                                text = "${categoryReportItem.amount.toString().toWonFormat()}원",
                                color = Gray07,
                                style = Heading1
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "${categoryReportItem.percent}%",
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
private fun CategoryReportStick(
    modifier: Modifier,
    name: String,
    amount: Long,
    percent: Int,
    color: Color
) {
    val minHeight = 20
    val maxHeight = 174
    var targetHeight: Float by remember { mutableFloatStateOf(value = 0f) }
    val animatedHeight by animateFloatAsState(targetValue = targetHeight)

    LaunchedEffect(key1 = percent) {
        targetHeight = minHeight + (maxHeight - minHeight) * (percent.toFloat() / 100)
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
    ReportScreen(
        navigateUp = {},
        selectYearMonth = YearMonth.now(),
        reportData = ReportUiData.Empty,
        updateReportToPreviousMonth = {},
        updateReportToNextMonth = {}
    )
}