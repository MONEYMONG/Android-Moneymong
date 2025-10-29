package com.moneymong.moneymong.analytics

class NoOpAnalyticsTracker : AnalyticsTracker {
    override fun logEvent(event: AnalyticsEvent) = Unit
}