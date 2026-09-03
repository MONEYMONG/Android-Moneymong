package com.moneymong.moneymong.invite

import android.net.Uri

internal object InviteDeepLinkParser {

    private const val INVITE_LINK_HOST = "dev.moneymong.site"

    private const val SCHEME = "https"
    private const val PATH = "/invite"
    private const val QUERY_CODE = "code"
    private val CodeRegex = Regex("^\\d{6}$")


    fun parse(uri: Uri?): String? {
        if (uri == null) return null
        if (uri.isHierarchical.not()) return null
        if (uri.scheme != SCHEME) return null
        if (uri.host != INVITE_LINK_HOST) return null
        if (uri.path != PATH) return null

        val code = uri.getQueryParameter(QUERY_CODE) ?: return null
        return if (CodeRegex.matches(code)) code else null
    }
}