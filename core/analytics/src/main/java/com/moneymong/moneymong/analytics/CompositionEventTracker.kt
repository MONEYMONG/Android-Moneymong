package com.moneymong.moneymong.analytics

import androidx.compose.runtime.staticCompositionLocalOf

val LocalAnalyticsTracker = staticCompositionLocalOf<AnalyticsTracker> {
    NoOpAnalyticsTracker()
}
