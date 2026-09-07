package com.example.member.share

import com.example.member.BuildConfig

internal data class InviteLinkShareData(
    val subject: String,
    val chooserTitle: String,
    val message: String,
) {
    companion object {
        private const val BASE_URL = "https://${BuildConfig.INVITE_LINK_HOST}"
        private const val INVITE_LINK_PATH = "/invite"

        fun from(invitationCode: String, agencyId: Int): InviteLinkShareData {
            val inviteUrl = "$BASE_URL$INVITE_LINK_PATH?code=$invitationCode&agencyId=$agencyId"

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
