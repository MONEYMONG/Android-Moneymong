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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.component.tag.MDSTag
import com.moneymong.moneymong.design_system.theme.Blue04
import com.moneymong.moneymong.design_system.theme.Body3
import com.moneymong.moneymong.design_system.theme.Gray01
import com.moneymong.moneymong.design_system.theme.Gray05
import com.moneymong.moneymong.design_system.theme.Gray06
import com.moneymong.moneymong.design_system.theme.Gray10
import com.moneymong.moneymong.design_system.theme.Heading1
import com.moneymong.moneymong.design_system.theme.Heading4
import com.moneymong.moneymong.design_system.theme.Red01
import com.moneymong.moneymong.design_system.theme.Red03
import com.moneymong.moneymong.design_system.theme.SkyBlue01
import com.moneymong.moneymong.design_system.theme.White
import com.moneymong.moneymong.report.model.AmountType
import com.moneymong.moneymong.report.model.MemberReport
import com.moneymong.moneymong.ui.toWonFormat
import com.moneymong.moneymong.design_system.R as MDSR

@Composable
internal fun MemberReportView(
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
                painter = painterResource(MDSR.drawable.img_profile),
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