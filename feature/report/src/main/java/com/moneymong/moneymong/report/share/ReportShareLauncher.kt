package com.moneymong.moneymong.report.share

import android.content.ClipData
import android.content.Context
import android.content.Intent
import com.moneymong.moneymong.report.ReportUiData
import java.time.YearMonth

internal object ReportShareLauncher {
    suspend fun launch(
        context: Context,
        yearMonth: YearMonth,
        reportData: ReportUiData
    ) {
        val shareData = ReportShareData.from(
            yearMonth = yearMonth,
            reportData = reportData
        )
        val imageUri = ReportShareImageRenderer.render(
            context = context,
            shareData = shareData
        )
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, imageUri)
            clipData = ClipData.newUri(
                context.contentResolver,
                shareData.fileName,
                imageUri
            )
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(
            Intent.createChooser(
                sendIntent,
                "${shareData.monthText} 레포트 공유"
            )
        )
    }
}
