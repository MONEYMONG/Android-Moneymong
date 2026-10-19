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

        fun from(invitationCode: String, agencyId: Int, agencyName: String): InviteLinkShareData {
            val inviteUrl = "$BASE_URL$INVITE_LINK_PATH?code=$invitationCode&agencyId=$agencyId"

            return InviteLinkShareData(
                subject = "머니몽 장부 초대",
                chooserTitle = "초대 링크 공유",
                message = "\"$agencyName\"에서 초대가 도착했어요!\n" +
                        "머니몽에서 함께 장부를 채워보세요.\n" +
                        "아래 링크를 누르면 초대받은 장부에 바로 참여할 수 있어요.\n" +
                        inviteUrl,
            )
        }
    }
}
