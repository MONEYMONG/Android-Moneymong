package com.moneymong.moneymong.report.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.theme.Black
import com.moneymong.moneymong.design_system.theme.Blue04
import com.moneymong.moneymong.design_system.theme.Body2
import com.moneymong.moneymong.design_system.theme.Gray01
import com.moneymong.moneymong.design_system.theme.Gray06
import com.moneymong.moneymong.design_system.theme.Gray10
import com.moneymong.moneymong.design_system.theme.Heading3
import com.moneymong.moneymong.design_system.theme.Heading5
import com.moneymong.moneymong.design_system.theme.Red03
import com.moneymong.moneymong.report.model.AmountType
import com.moneymong.moneymong.ui.noRippleClickable
import com.moneymong.moneymong.ui.toWonFormat
import java.time.YearMonth
import com.moneymong.moneymong.design_system.R as MDSR

@Composable
internal fun ReportMonthly(
    modifier: Modifier = Modifier,
    yearMonth: YearMonth,
    monthlyIncome: Long,
    monthlyExpense: Long,
    monthlyIncomePercent: Int,
    monthlyExpensePercent: Int,
    isLoading: Boolean,
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
        if (isLoading.not()) {
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
            color = if (type == AmountType.INCOME) Blue04 else Red03,
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
            text = "총 ${type.label}의 $monthlyPercent%를 차지",
            color = Gray06,
            style = Body2
        )
    }
}
