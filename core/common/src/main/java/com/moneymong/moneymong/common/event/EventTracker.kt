package com.moneymong.moneymong.common.event

import android.os.Bundle
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import javax.inject.Inject

class EventTracker
    @Inject
    constructor() {
        private lateinit var firebaseAnalytics: FirebaseAnalytics
        private var initialized: Boolean = false

        fun initialize() {
            firebaseAnalytics = Firebase.analytics
            initialized = true
        }

        fun logEvent(
            event: Event,
            param: Bundle? = null,
        ) {
            check(initialized) { "logEvent를 호출하기 전 초기화가 필요합니다." }

            firebaseAnalytics.logEvent(event.eventName, param)
        }
    }
