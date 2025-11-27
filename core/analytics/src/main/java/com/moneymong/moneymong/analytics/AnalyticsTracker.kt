package com.moneymong.moneymong.analytics

interface AnalyticsTracker {
    fun logEvent(event: AnalyticsEvent)
}