package com.example.member.share

import com.example.member.BuildConfig

internal data class InviteLinkShareData(
    val subject: String,
    val chooserTitle: String,
    val message: String,
) {
    companion object {
        private val baseUrl =
            if (BuildConfig.DEBUG) "https://dev.moneymong.site" else "https://prod.moneymong.site"
        private const val INVITE_LINK_PATH = "/invite"

        fun from(invitationCode: String): InviteLinkShareData {
            val inviteUrl = "$baseUrl$INVITE_LINK_PATH?code=$invitationCode"

            return InviteLinkShareData(
                subject = "머니몽 장부 초대",
                chooserTitle = "초대 링크 공유",
                message = "머니몽 장부에 초대받았어요!\n" +
                        "아래 링크를 눌러 장부에 참여해 주세요.\n" +
                        inviteUrl,
            )
        }
    }
}
