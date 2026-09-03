package com.moneymong.moneymong.invite

import android.net.Uri


internal object InviteDeepLinkParser {

    private const val INVITE_LINK_HOST = "dev.moneymong.site"
    private const val QUERY_CODE = "code"
    private const val QUERY_AGENCY_ID = "agencyId"

    private const val SCHEME = "https"
    private const val PATH = "/invite"
    private val CodeRegex = Regex("^\\d{6}$")


    fun parse(uri: Uri?): InviteDeepLink? {
        if (uri == null) return null
        if (uri.isHierarchical.not()) return null
        if (uri.scheme != SCHEME) return null
        if (uri.host != INVITE_LINK_HOST) return null
        if (uri.path != PATH) return null

        val code = uri.getQueryParameter(QUERY_CODE) ?: return null
        if (CodeRegex.matches(code).not()) return null

        val agencyId = uri.getQueryParameter(QUERY_AGENCY_ID)?.toIntOrNull() ?: return null
        if (agencyId <= 0) return null

        return InviteDeepLink(code = code, agencyId = agencyId)
    }
}