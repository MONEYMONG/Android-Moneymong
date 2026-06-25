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
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.component.tab.MDSTabRow
import com.moneymong.moneymong.design_system.theme.Blue03
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
            val topCategoryReportItems = categoryReportItems.take(3)
            val maxPercent = topCategoryReportItems.maxOfOrNull { it.percent } ?: 0
            val stickColors = listOf(Blue04, Blue03, SkyBlue01)

            topCategoryReportItems.forEachIndexed { idx, categoryReportItem ->
                CategoryReportStick(
                    modifier = Modifier
                        .weight(1f)
                        .alignBy(CategoryReportStickBottomLine),
                    name = categoryReportItem.name,
                    amount = categoryReportItem.amount,
                    percent = categoryReportItem.percent,
                    maxPercent = maxPercent,
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

private val CategoryReportStickBottomLine = HorizontalAlignmentLine { old, new ->
    minOf(old, new)
}


@Composable
private fun CategoryReportStick(
    modifier: Modifier,
    name: String,
    amount: Long,
    percent: Int,
    maxPercent: Int,
    color: Color
) {
    val minHeight = 20
    val maxHeight = 174
    val amountBottomSpacing = 9.dp
    val labelTopSpacing = 8.dp
    var targetHeight: Float by remember { mutableFloatStateOf(value = 0f) }
    val animatedHeight by animateFloatAsState(targetValue = targetHeight)

    LaunchedEffect(key1 = percent, key2 = maxPercent) {
        targetHeight = calculateCategoryReportStickHeight(
            percent = percent,
            maxPercent = maxPercent,
            minHeight = minHeight,
            maxHeight = maxHeight
        )
    }

    Layout(
        modifier = modifier,
        content = {
            Text(text = "${amount.toString().toWonFormat()}원", color = Gray10, style = Heading1)
            Box(
                modifier = Modifier
                    .width(44.dp)
                    .height(animatedHeight.dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .background(color = color)
            )
            Text(text = name, color = Gray10, style = Body3)
            Text(text = "${percent}%", color = Gray04, style = Caption)
        }
    ) { measurables, constraints ->
        val amountBottomSpacingPx = amountBottomSpacing.roundToPx()
        val labelTopSpacingPx = labelTopSpacing.roundToPx()
        val childConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        val amountPlaceable = measurables[0].measure(childConstraints)
        val stickPlaceable = measurables[1].measure(Constraints())
        val namePlaceable = measurables[2].measure(childConstraints)
        val percentPlaceable = measurables[3].measure(childConstraints)

        val width = constraints.maxWidth
        val stickBottomY = amountPlaceable.height + amountBottomSpacingPx + stickPlaceable.height
        val height = stickBottomY + labelTopSpacingPx + namePlaceable.height + percentPlaceable.height

        layout(
            width = width,
            height = height,
            alignmentLines = mapOf(CategoryReportStickBottomLine to stickBottomY)
        ) {
            amountPlaceable.placeRelative(
                x = (width - amountPlaceable.width) / 2,
                y = 0
            )
            stickPlaceable.placeRelative(
                x = (width - stickPlaceable.width) / 2,
                y = amountPlaceable.height + amountBottomSpacingPx
            )
            namePlaceable.placeRelative(
                x = (width - namePlaceable.width) / 2,
                y = stickBottomY + labelTopSpacingPx
            )
            percentPlaceable.placeRelative(
                x = (width - percentPlaceable.width) / 2,
                y = stickBottomY + labelTopSpacingPx + namePlaceable.height
            )
        }
    }
}

internal fun calculateCategoryReportStickHeight(
    percent: Int,
    maxPercent: Int,
    minHeight: Int,
    maxHeight: Int
): Float {
    if (maxPercent <= 0) return minHeight.toFloat()

    val ratio = percent.toFloat() / maxPercent
    return minHeight + (maxHeight - minHeight) * ratio.coerceIn(0f, 1f)
}

@Preview(
    name = "Category Report Stick - Long Name Font 2.0x",
    showBackground = true,
    fontScale = 2.0f
)
@Composable
private fun CategoryReportContentLongNamePreview() {
    CategoryReportContent(
        month = 6,
        amountType = AmountType.EXPENSE,
        categoryReportItems = listOf(
            CategoryReportItem(
                name = "엄청나게긴카테고리이름입니다",
                amount = 12000,
                percent = 10
            ),
            CategoryReportItem(
                name = "교통비",
                amount = 240_0000000000000L,
                percent = 33
            ),
            CategoryReportItem(
                name = "식비",
                amount = 60_000L,
                percent = 13
            )
        )
    )
}
