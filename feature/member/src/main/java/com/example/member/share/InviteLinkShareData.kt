package com.example.member.share

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

internal data class InviteLinkShareData(
    val subject: String,
    val chooserTitle: String,
    val intentUri: String,
    val message: String,
) {
    companion object {
        private const val PLAY_STORE_PACKAGE_NAME = "com.moneymong.moneymong.live"
        private val InvitationCodeRegex = Regex("^[0-9]{6}$")

        fun from(
            invitationCode: String,
            appPackageName: String,
        ): InviteLinkShareData? {
            if (!InvitationCodeRegex.matches(invitationCode)) return null

            val playStoreUrl =
                "https://play.google.com/store/apps/details?id=$PLAY_STORE_PACKAGE_NAME"
            val encodedFallbackUrl = URLEncoder.encode(
                playStoreUrl,
                StandardCharsets.UTF_8.name(),
            )
            val intentUri = buildString {
                append("intent://invite?code=")
                append(invitationCode)
                append("#Intent;scheme=moneymong;package=")
                append(appPackageName)
                append(";S.browser_fallback_url=")
                append(encodedFallbackUrl)
                append(";end")
            }

            return InviteLinkShareData(
                subject = "머니몽 장부 초대",
                chooserTitle = "초대 링크 공유",
                intentUri = intentUri,
                message = "머니몽 장부에 초대받았어요!\n" +
                    "아래 링크를 눌러 장부에 참여해 주세요.\n" +
                    intentUri,
            )
        }
    }
}
