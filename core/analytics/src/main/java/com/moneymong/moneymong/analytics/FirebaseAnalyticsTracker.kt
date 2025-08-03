package com.moneymong.moneymong.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import javax.inject.Inject

class FirebaseAnalyticsTracker @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) : AnalyticsTracker {
    override fun logEvent(event: AnalyticsEvent) {
        firebaseAnalytics.logEvent(event.type) {
            for (extra in event.extras) {
                param(
                    key = extra.key.take(MAX_KEY_TAKE_COUNT),
                    value = extra.value.take(MAX_VALUE_TAKE_COUNT),
                )
            }
        }
    }

    companion object {
        private const val MAX_KEY_TAKE_COUNT = 40
        private const val MAX_VALUE_TAKE_COUNT = 100
    }
}