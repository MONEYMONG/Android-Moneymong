package com.moneymong.moneymong.report.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.component.tab.MDSTabRow
import com.moneymong.moneymong.design_system.theme.Blue01
import com.moneymong.moneymong.design_system.theme.Blue04
import com.moneymong.moneymong.design_system.theme.Body3
import com.moneymong.moneymong.design_system.theme.Caption
import com.moneymong.moneymong.design_system.theme.Gray01
import com.moneymong.moneymong.design_system.theme.Gray04
import com.moneymong.moneymong.design_system.theme.Gray07
import com.moneymong.moneymong.design_system.theme.Gray10
import com.moneymong.moneymong.design_system.theme.Heading1
import com.moneymong.moneymong.design_system.theme.Heading4
import com.moneymong.moneymong.design_system.theme.SkyBlue01
import com.moneymong.moneymong.report.model.AmountType
import com.moneymong.moneymong.report.model.CategoryReport
import com.moneymong.moneymong.report.model.CategoryReportItem
import com.moneymong.moneymong.report.model.toCategoryReportItemsWithSort
import com.moneymong.moneymong.ui.toWonFormat

@Composable
internal fun CategoryReportView(
    modifier: Modifier = Modifier,
    selectMonth: Int,
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
            month = selectMonth,
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