package com.example.member.share

import android.content.Context
import android.content.Intent

internal object InviteLinkShareLauncher {
    fun launch(
        context: Context,
        invitationCode: String,
    ) {
        val shareData = InviteLinkShareData.from(
            invitationCode = invitationCode,
            appPackageName = context.packageName,
        ) ?: return

        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, shareData.subject)
            putExtra(Intent.EXTRA_TEXT, shareData.message)
        }

        context.startActivity(
            Intent.createChooser(sendIntent, shareData.chooserTitle)
        )
    }
}
