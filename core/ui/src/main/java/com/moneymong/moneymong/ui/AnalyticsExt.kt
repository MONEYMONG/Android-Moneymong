package com.moneymong.moneymong.ui

import com.moneymong.moneymong.analytics.AnalyticsEvent
import com.moneymong.moneymong.analytics.AnalyticsTracker

fun AnalyticsTracker.logClicked(eventName: String) {
    logEvent(
        event = AnalyticsEvent(
            type = eventName,
            extras = emptyList(),
        )
    )
}