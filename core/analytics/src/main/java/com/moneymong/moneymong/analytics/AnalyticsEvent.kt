package com.moneymong.moneymong.analytics

data class AnalyticsEvent(
    val type: String,
    val extras: List<Param> = emptyList(),
) {
    data class Param(
        val key: String,
        val value: String,
    )

    companion object {
        const val SCREEN_VIEW = "screen_view"
        const val SCREEN_NAME = "screen_name"
    }
}