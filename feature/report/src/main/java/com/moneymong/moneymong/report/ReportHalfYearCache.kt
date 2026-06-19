package com.moneymong.moneymong.report

import com.moneymong.moneymong.model.ledger.LedgerReportResponse
import java.time.YearMonth

internal data class ReportHalfYear(
    val year: Int,
    val half: Int
)

internal data class ReportHalfYearRange(
    val half: ReportHalfYear,
    val startYear: Int,
    val startMonth: Int,
    val endYear: Int,
    val endMonth: Int
)

internal fun YearMonth.toReportHalfYearRange(): ReportHalfYearRange {
    val isFirstHalf = monthValue <= 6
    val half = if (isFirstHalf) 1 else 2
    val startMonth = if (isFirstHalf) 1 else 7
    val endMonth = if (isFirstHalf) 6 else 12

    return ReportHalfYearRange(
        half = ReportHalfYear(year = year, half = half),
        startYear = year,
        startMonth = startMonth,
        endYear = year,
        endMonth = endMonth
    )
}

internal fun YearMonth.canMoveToNextReportMonth(
    currentYearMonth: YearMonth = YearMonth.now()
): Boolean = this.plusMonths(1) <= currentYearMonth

internal class ReportHalfYearCache {
    private val reports = mutableMapOf<ReportHalfYear, Map<YearMonth, ReportUiData>>()

    fun put(
        key: ReportHalfYear,
        response: LedgerReportResponse
    ) {
        reports[key] = response.monthly.associate { monthly ->
            YearMonth.of(monthly.year, monthly.month) to response.toUiData(monthly)
        }
    }

    fun get(yearMonth: YearMonth): ReportUiData? {
        val key = yearMonth.toReportHalfYearRange().half
        return reports[key]?.get(yearMonth)
    }
}
