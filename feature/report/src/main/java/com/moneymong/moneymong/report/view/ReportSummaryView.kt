package com.moneymong.moneymong.report.view

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.theme.Blue04
import com.moneymong.moneymong.design_system.theme.Body2
import com.moneymong.moneymong.design_system.theme.Gray01
import com.moneymong.moneymong.design_system.theme.Gray06
import com.moneymong.moneymong.design_system.theme.Gray10
import com.moneymong.moneymong.design_system.theme.Heading1
import com.moneymong.moneymong.design_system.theme.Heading5
import com.moneymong.moneymong.design_system.theme.White
import com.moneymong.moneymong.report.model.AmountType
import com.moneymong.moneymong.ui.toWonFormat
import com.moneymong.moneymong.design_system.R as MDSR

@Composable
internal fun ReportSummary(
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
                        append(balance.toString().toWonFormat())
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
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SummaryItem(modifier = Modifier.weight(1f), amount = income, type = AmountType.INCOME)
            SummaryItem(modifier = Modifier.weight(1f), amount = expense, type = AmountType.EXPENSE)
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
            text = "총 ${type.label}",
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