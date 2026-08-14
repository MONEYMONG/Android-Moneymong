package com.example.member.share

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class InviteLinkShareDataTest {

    @Test
    fun `6자리 초대 코드로 intent 링크와 공유 문구를 만든다`() {
        val shareData = InviteLinkShareData.from(
            invitationCode = "123456",
            appPackageName = "com.moneymong.moneymong.tb",
        )

        requireNotNull(shareData)
        assertEquals("머니몽 장부 초대", shareData.subject)
        assertEquals("초대 링크 공유", shareData.chooserTitle)
        assertEquals(
            "intent://invite?code=123456" +
                "#Intent;scheme=moneymong;" +
                "package=com.moneymong.moneymong.tb;" +
                "S.browser_fallback_url=https%3A%2F%2Fplay.google.com%2Fstore%2Fapps%2Fdetails%3Fid%3Dcom.moneymong.moneymong.live;" +
                "end",
            shareData.intentUri,
        )
        assertEquals(
            "머니몽 장부에 초대받았어요!\n" +
                "아래 링크를 눌러 장부에 참여해 주세요.\n" +
                shareData.intentUri,
            shareData.message,
        )
    }

    @Test
    fun `초대 코드가 6자리 숫자가 아니면 공유 데이터를 만들지 않는다`() {
        assertNull(
            InviteLinkShareData.from(
                invitationCode = "MONEY1",
                appPackageName = "com.moneymong.moneymong.live",
            )
        )
        assertNull(
            InviteLinkShareData.from(
                invitationCode = "12345",
                appPackageName = "com.moneymong.moneymong.live",
            )
        )
    }
}
