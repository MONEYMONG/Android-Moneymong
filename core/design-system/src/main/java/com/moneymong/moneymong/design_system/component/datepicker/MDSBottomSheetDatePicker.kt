package com.moneymong.moneymong.design_system.component.datepicker

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.R
import com.moneymong.moneymong.design_system.component.button.MDSButton
import com.moneymong.moneymong.design_system.component.button.MDSButtonSize
import com.moneymong.moneymong.design_system.component.button.MDSButtonType
import com.moneymong.moneymong.design_system.component.snackbar.MDSSnackbarHost
import com.moneymong.moneymong.design_system.theme.Blue01
import com.moneymong.moneymong.design_system.theme.Blue03
import com.moneymong.moneymong.design_system.theme.Blue04
import com.moneymong.moneymong.design_system.theme.Body3
import com.moneymong.moneymong.design_system.theme.Gray02
import com.moneymong.moneymong.design_system.theme.Gray03
import com.moneymong.moneymong.design_system.theme.Gray05
import com.moneymong.moneymong.design_system.theme.Gray09
import com.moneymong.moneymong.design_system.theme.Heading2
import com.moneymong.moneymong.design_system.theme.Red01
import com.moneymong.moneymong.design_system.theme.Red02
import com.moneymong.moneymong.design_system.theme.Red03
import com.moneymong.moneymong.design_system.theme.White
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate

enum class MDSDateType(val description: String) {
    START(description = "시작일"),
    END(description = "종료일")
}

@Composable
fun MDSWheelDatePicker(
    modifier: Modifier = Modifier,
    startDate: LocalDate = LocalDate.now().minusMonths(6),
    endDate: LocalDate = LocalDate.now(),
    yearRange: IntProgression = IntProgression.fromClosedRange(LocalDate.now().year + 1, 2017, -1), // empty string 표시를 위해 1씩 추가함
    confirmDateChange: (startDate: LocalDate, endDate: LocalDate) -> Unit,
    confirmValidValue: (Boolean) -> Unit = {},
    onDismissRequest: () -> Unit,
) {
    var snappedStartYear by remember { mutableIntStateOf(startDate.year) }
    var snappedStartMonth by remember { mutableIntStateOf(startDate.monthValue) }
    var snappedEndYear by remember { mutableIntStateOf(endDate.year) }
    var snappedEndMonth by remember { mutableIntStateOf(endDate.monthValue) }
    var dateType: MDSDateType by remember { mutableStateOf(MDSDateType.END) }
    var isValidValue by remember { mutableStateOf(true) }
    val months by remember { mutableStateOf((0..13).toList()) }
    val years by remember { mutableStateOf(yearRange.toList()) }
    var startYearIndex by remember { mutableIntStateOf(years.indexOf(endDate.year)) }
    var startMonthIndex by remember { mutableIntStateOf(months.indexOf(endDate.monthValue)) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val onChangeDateType: (MDSDateType) -> Unit = {
        dateType = it
    }

    val updateYear: (Int) -> Unit = {
        when (dateType) {
            MDSDateType.START -> snappedStartYear = it
            MDSDateType.END -> snappedEndYear = it
        }
    }

    val updateMonth: (Int) -> Unit = {
        when (dateType) {
            MDSDateType.START -> snappedStartMonth = it
            MDSDateType.END -> snappedEndMonth = it
        }
    }

    LaunchedEffect(dateType) {
        when (dateType) {
            MDSDateType.START -> {
                startYearIndex = years.indexOf(snappedStartYear)
                startMonthIndex = months.indexOf(snappedStartMonth)
            }

            MDSDateType.END -> {
                startYearIndex = years.indexOf(snappedEndYear)
                startMonthIndex = months.indexOf(snappedEndMonth)
            }
        }
    }

    LaunchedEffect(
        snappedStartYear,
        snappedStartMonth,
        snappedEndYear,
        snappedEndMonth
    ) {
        isValidValue = if (snappedStartYear < snappedEndYear) {
            true
        } else {
            snappedStartMonth <= snappedEndMonth
        }

        confirmValidValue(isValidValue)
    }

    LaunchedEffect(isValidValue) {
        if (!isValidValue) {
            scope.launch {
                snackbarHostState.currentSnackbarData?.dismiss()
                snackbarHostState.showSnackbar(
                    message = "올바른 범위로 기간을 설정해주세요!",
                    withDismissAction = true,
                    actionLabel = ""
                )
            }
        } else {
            scope.launch {
                snackbarHostState.currentSnackbarData?.dismiss()
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    modifier = Modifier
                        .clickable { onDismissRequest() }
                        .align(Alignment.CenterEnd),
                    painter = painterResource(id = R.drawable.ic_close_default),
                    contentDescription = null,
                    tint = Gray05
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MDSDatePickerDateView(
                    dateType = MDSDateType.START,
                    year = snappedStartYear,
                    month = snappedStartMonth,
                    selected = dateType == MDSDateType.START,
                    isValidValue = isValidValue,
                    onClick = onChangeDateType
                )
                MDSDatePickerDateView(
                    dateType = MDSDateType.END,
                    year = snappedEndYear,
                    month = snappedEndMonth,
                    selected = dateType == MDSDateType.END,
                    isValidValue = isValidValue,
                    onClick = onChangeDateType
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Gray02
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                WheelPicker(
                    items = years,
                    startIndex = startYearIndex,
                    confirmDateChange = updateYear
                ) { year, color ->
                    Text(
                        modifier = Modifier.width(64.dp),
                        text = if (year == yearRange.first || year == yearRange.last) "" else "${year}년",
                        style = Heading2,
                        color = color,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.width(40.dp))
                WheelPicker(
                    items = months,
                    startIndex = startMonthIndex,
                    confirmDateChange = updateMonth
                ) { month, color ->
                    Text(
                        modifier = Modifier.width(64.dp),
                        text = if (month == months.first() || month == months.last()) "" else "${month}월",
                        style = Heading2,
                        color = color,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            MDSButton(
                modifier = Modifier.fillMaxWidth(),
                type = MDSButtonType.PRIMARY,
                size = MDSButtonSize.LARGE,
                text = "완료",
                enabled = isValidValue,
                onClick = {
                    confirmDateChange(
                        LocalDate.of(snappedStartYear, snappedStartMonth, 1),
                        LocalDate.of(snappedEndYear, snappedEndMonth, 1)
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        MDSSnackbarHost(
            modifier = Modifier
                .padding(start = 20.dp, bottom = 100.dp, end = 20.dp)
                .align(Alignment.BottomCenter),
            hostState = snackbarHostState
        )
    }
}

@Composable
internal fun MDSDatePickerDateView(
    modifier: Modifier = Modifier,
    dateType: MDSDateType,
    selected: Boolean,
    year: Int,
    month: Int,
    isValidValue: Boolean,
    onClick: (MDSDateType) -> Unit
) {
    Box(
        modifier = modifier
            .background(
                color = if (!isValidValue && selected) Red01 else if (selected) Blue01 else White,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick(dateType) }
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 12.dp, top = 10.dp, end = 19.dp, bottom = 12.dp)
        ) {
            Text(
                text = dateType.description,
                style = Body3,
                color = if (!isValidValue && selected) Red02 else Blue03
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${year}년 ${month}월",
                style = Heading2,
                color = if (!isValidValue && selected) Red03 else Blue04
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun <T> WheelPicker(
    modifier: Modifier = Modifier,
    state: DatePickerState = rememberMDSDatePickerState(),
    size: DpSize = DpSize(width = 64.dp, height = 140.dp),
    items: List<T>,
    startIndex: Int = 0,
    visibleItemsCount: Int = 3,
    confirmDateChange: (value: T) -> Unit = {},
    content: @Composable LazyItemScope.(item: T, color: Color) -> Unit
) {
    val lazyListState =
        rememberLazyListState(startIndex - 1) // empty string을 위해 리스트 처음과 끝 값을 제외함
    val snappingLayout = remember(lazyListState) { SnapLayoutInfoProvider(lazyListState) }
    val snapFlingBehavior = rememberSnapFlingBehavior(snappingLayout)

    val visibleItemsMiddle by remember { derivedStateOf { visibleItemsCount / 2 } }

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemIndex }
            .map { index -> index + visibleItemsMiddle }
            .distinctUntilChanged()
            .collect { item ->
                state.currentIndex = item
                confirmDateChange(items[item])
            }
    }

    LaunchedEffect(startIndex) {
        lazyListState.scrollToItem(startIndex - 1)
    }

    LazyColumn(
        modifier = modifier
            .width(size.width)
            .height(size.height),
        verticalArrangement = Arrangement.spacedBy(28.dp),
        state = lazyListState,
        flingBehavior = snapFlingBehavior
    ) {
        items(items.size) { index ->
            content(items[index], state.calculateItemColor(index))
        }
    }
}

@Composable
fun rememberMDSDatePickerState() = remember { DatePickerState() }

class DatePickerState() {
    var currentIndex by mutableIntStateOf(0)

    fun calculateItemColor(itemIndex: Int) =
        if (currentIndex == itemIndex) {
            Gray09
        } else {
            Gray03
        }
}