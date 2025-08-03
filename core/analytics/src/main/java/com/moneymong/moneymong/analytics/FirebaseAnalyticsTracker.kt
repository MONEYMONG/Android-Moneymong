package com.moneymong.moneymong.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import javax.inject.Inject

class FirebaseAnalyticsTracker @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) : AnalyticsTracker {

//    private var initialized: Boolean = false
//
//    fun initialize() {
//        firebaseAnalytics = Firebase.analytics
//        initialized = true
//    }

//    fun logEvent(event: AnalyticsEvent, param: Bundle? = null) {
//        check(initialized) { "logEvent를 호출하기 전 초기화가 필요합니다." }
//
//        firebaseAnalytics.logEvent(event.eventName, param)
//    }

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