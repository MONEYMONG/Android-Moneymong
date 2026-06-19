package com.moneymong.moneymong.report.share

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.net.Uri
import android.text.TextPaint
import android.text.TextUtils
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.text.DecimalFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.abs
import androidx.core.graphics.createBitmap

internal object ReportShareImageRenderer {
    suspend fun render(
        context: Context,
        shareData: ReportShareData
    ): Uri {
        val bitmap = withContext(Dispatchers.Default) {
            ReportShareCardRenderer(shareData).render()
        }
        val imageFile = withContext(Dispatchers.IO) {
            val directory = File(context.cacheDir, "report_share").apply { mkdirs() }
            File(directory, shareData.fileName).also { file ->
                FileOutputStream(file).use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                }
            }
        }

        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            imageFile
        )
    }
}

private class ReportShareCardRenderer(
    private val shareData: ReportShareData
) {
    private val amountFormat = DecimalFormat("#,###")
    private val width = 1080
    private val horizontalPadding = 64f
    private val cardRadius = 32f
    private val sectionGap = 32f
    private val headerHeight = 166f
    private val summaryCardHeight = 328f
    private val monthlyCardHeight = 300f
    private val rowHeight = 118f
    private val emptyRowHeight = 84f

    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Gray10
    }
    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    fun render(): Bitmap {
        val height = calculateHeight()
        val bitmap = createBitmap(width, height)
        val canvas = Canvas(bitmap)

        canvas.drawColor(Gray01)

        var y = 72f
        y = drawHeader(canvas, y)
        y += sectionGap
        y = drawSummaryCard(canvas, y)
        y += sectionGap
        y = drawMonthlyCard(canvas, y)
        y += sectionGap
        y = drawMemberSection(canvas, y)
        y += sectionGap
        y = drawCategorySection(canvas, y)
        drawFooter(canvas, y + 48f)

        return bitmap
    }

    private fun calculateHeight(): Int {
        val memberHeight = sectionHeight(shareData.members.size)
        val categoryHeight = sectionHeight(shareData.categories.size)
        return (
            72f +
                headerHeight +
                sectionGap +
                summaryCardHeight +
                sectionGap +
                monthlyCardHeight +
                sectionGap +
                memberHeight +
                sectionGap +
                categoryHeight +
                132f
            ).toInt()
    }

    private fun sectionHeight(itemCount: Int): Float {
        val contentHeight = if (itemCount == 0) emptyRowHeight else itemCount * rowHeight
        return 54f + 20f + contentHeight + 40f
    }

    private fun drawHeader(canvas: Canvas, top: Float): Float {
        drawText(
            canvas = canvas,
            text = shareData.title,
            x = horizontalPadding,
            baseline = top + 42f,
            size = 42f,
            color = Gray10,
            bold = true
        )
        drawText(
            canvas = canvas,
            text = shareData.agencyName.ifBlank { "장부" },
            x = horizontalPadding,
            baseline = top + 94f,
            size = 28f,
            color = Gray10,
            bold = true
        )
        drawText(
            canvas = canvas,
            text = shareData.monthText,
            x = horizontalPadding,
            baseline = top + 134f,
            size = 26f,
            color = Gray06
        )
        return top + headerHeight
    }

    private fun drawSummaryCard(canvas: Canvas, top: Float): Float {
        drawCard(canvas, top, summaryCardHeight, White)

        val left = horizontalPadding + 40f
        drawText(canvas, "장부 요약", left, top + 62f, 30f, Gray06, bold = true)
        drawText(canvas, "남은 금액", left, top + 116f, 28f, Gray06)
        drawText(
            canvas = canvas,
            text = formatWon(shareData.totalBalance),
            x = left,
            baseline = top + 176f,
            size = 48f,
            color = Blue04,
            bold = true
        )

        val tileTop = top + 210f
        val tileWidth = (width - horizontalPadding * 2 - 40f * 2 - 20f) / 2f
        drawAmountTile(canvas, left, tileTop, tileWidth, "총 수입", formatWon(shareData.totalIncome, "+"), Blue04)
        drawAmountTile(canvas, left + tileWidth + 20f, tileTop, tileWidth, "총 지출", formatWon(shareData.totalExpense, "-"), Red03)

        return top + summaryCardHeight
    }

    private fun drawMonthlyCard(canvas: Canvas, top: Float): Float {
        drawCard(canvas, top, monthlyCardHeight, White)

        val left = horizontalPadding + 40f
        drawText(canvas, "${shareData.monthText} 요약", left, top + 62f, 30f, Gray10, bold = true)

        val netColor = if (shareData.monthlyBalance >= 0) Blue04 else Red03
        drawText(canvas, "월 순수익", left, top + 118f, 28f, Gray06)
        drawText(
            canvas = canvas,
            text = formatSignedWon(shareData.monthlyBalance),
            x = left,
            baseline = top + 174f,
            size = 42f,
            color = netColor,
            bold = true
        )

        val tileTop = top + 206f
        val tileWidth = (width - horizontalPadding * 2 - 40f * 2 - 20f) / 2f
        drawAmountTile(
            canvas = canvas,
            left = left,
            top = tileTop,
            width = tileWidth,
            label = "월 수입 ${shareData.monthlyIncomePercent}%",
            amount = formatWon(shareData.monthlyIncome, "+"),
            amountColor = Blue04
        )
        drawAmountTile(
            canvas = canvas,
            left = left + tileWidth + 20f,
            top = tileTop,
            width = tileWidth,
            label = "월 지출 ${shareData.monthlyExpensePercent}%",
            amount = formatWon(shareData.monthlyExpense, "-"),
            amountColor = Red03
        )

        return top + monthlyCardHeight
    }

    private fun drawMemberSection(canvas: Canvas, top: Float): Float {
        return drawSection(
            canvas = canvas,
            top = top,
            title = "멤버별 내역",
            emptyText = "멤버별 내역이 없어요.",
            items = shareData.members,
            drawItem = { itemTop, member -> drawReportRow(canvas, itemTop, member.name, member.income, member.expense, member.incomePercent, member.expensePercent) }
        )
    }

    private fun drawCategorySection(canvas: Canvas, top: Float): Float {
        return drawSection(
            canvas = canvas,
            top = top,
            title = "카테고리별 내역",
            emptyText = "카테고리별 내역이 없어요.",
            items = shareData.categories,
            drawItem = { itemTop, category -> drawReportRow(canvas, itemTop, category.name, category.income, category.expense, category.incomePercent, category.expensePercent) }
        )
    }

    private fun <T> drawSection(
        canvas: Canvas,
        top: Float,
        title: String,
        emptyText: String,
        items: List<T>,
        drawItem: (Float, T) -> Unit
    ): Float {
        drawText(canvas, title, horizontalPadding, top + 38f, 34f, Gray10, bold = true)

        val cardTop = top + 74f
        val cardHeight = if (items.isEmpty()) emptyRowHeight + 40f else items.size * rowHeight + 40f
        drawCard(canvas, cardTop, cardHeight, White)

        if (items.isEmpty()) {
            drawText(canvas, emptyText, horizontalPadding + 40f, cardTop + 74f, 28f, Gray06)
        } else {
            var itemTop = cardTop + 20f
            items.forEach { item ->
                drawItem(itemTop, item)
                itemTop += rowHeight
            }
        }

        return cardTop + cardHeight
    }

    private fun drawReportRow(
        canvas: Canvas,
        top: Float,
        name: String,
        income: Long,
        expense: Long,
        incomePercent: Int,
        expensePercent: Int
    ) {
        val left = horizontalPadding + 40f
        val right = width - horizontalPadding - 40f
        val nameMaxWidth = right - left - 320f
        drawText(canvas, ellipsize(name, nameMaxWidth), left, top + 42f, 30f, Gray10, bold = true)

        drawText(canvas, "${formatWon(income, "+")} · ${incomePercent}%", left, top + 88f, 26f, Blue04)
        val expenseText = "${formatWon(expense, "-")} · ${expensePercent}%"
        drawText(
            canvas = canvas,
            text = expenseText,
            x = right,
            baseline = top + 88f,
            size = 26f,
            color = Red03,
            align = Paint.Align.RIGHT
        )
    }

    private fun drawAmountTile(
        canvas: Canvas,
        left: Float,
        top: Float,
        width: Float,
        label: String,
        amount: String,
        amountColor: Int
    ) {
        fillPaint.color = Gray01
        canvas.drawRoundRect(RectF(left, top, left + width, top + 82f), 20f, 20f, fillPaint)
        drawText(canvas, label, left + 24f, top + 32f, 24f, Gray06)
        drawText(canvas, amount, left + 24f, top + 66f, 26f, amountColor, bold = true)
    }

    private fun drawFooter(canvas: Canvas, top: Float) {
        drawText(
            canvas = canvas,
            text = "Moneymong",
            x = width / 2f,
            baseline = top,
            size = 26f,
            color = Gray05,
            align = Paint.Align.CENTER,
            bold = true
        )
    }

    private fun drawCard(
        canvas: Canvas,
        top: Float,
        height: Float,
        color: Int
    ) {
        fillPaint.color = color
        canvas.drawRoundRect(
            RectF(horizontalPadding, top, width - horizontalPadding, top + height),
            cardRadius,
            cardRadius,
            fillPaint
        )
    }

    private fun drawText(
        canvas: Canvas,
        text: String,
        x: Float,
        baseline: Float,
        size: Float,
        color: Int,
        align: Paint.Align = Paint.Align.LEFT,
        bold: Boolean = false
    ) {
        textPaint.textSize = size
        textPaint.color = color
        textPaint.textAlign = align
        textPaint.typeface = if (bold) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
        canvas.drawText(text, x, baseline, textPaint)
    }

    private fun ellipsize(
        text: String,
        maxWidth: Float
    ): String {
        textPaint.textSize = 30f
        textPaint.typeface = Typeface.DEFAULT_BOLD
        return TextUtils.ellipsize(text, textPaint, maxWidth, TextUtils.TruncateAt.END).toString()
    }

    private fun formatWon(
        amount: Long,
        symbol: String? = null
    ): String {
        val prefix = symbol.orEmpty()
        return "$prefix${amountFormat.format(abs(amount))}원"
    }

    private fun formatSignedWon(amount: Long): String {
        val symbol = if (amount >= 0) "+" else "-"
        return formatWon(amount, symbol)
    }

    private companion object {
        val White = Color.WHITE
        val Gray10 = Color.rgb(15, 17, 20)
        val Gray06 = Color.rgb(73, 85, 106)
        val Gray05 = Color.rgb(119, 133, 158)
        val Gray01 = Color.rgb(246, 248, 252)
        val Blue04 = Color.rgb(85, 98, 255)
        val Red03 = Color.rgb(255, 84, 115)
    }
}
