package com.moneymong.moneymong.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.moneymong.moneymong.design_system.theme.MMHorizontalSpacing
import com.moneymong.moneymong.report.component.ReportTopBar


@Composable
fun ReportRoute(
    modifier: Modifier = Modifier,
    viewModel: ReportViewModel = hiltViewModel()
) {
    ReportScreen(modifier = modifier)
}


@Composable
private fun ReportScreen(
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        ReportTopBar { /* todo */ }
        ReportSummary(modifier = Modifier.padding(horizontal = MMHorizontalSpacing))
        ReportContent()
    }
}

@Composable
private fun ReportSummary(
    modifier: Modifier = Modifier,
    balance: Int = 50000, // todo
    income: Int = 100000, // todo
    expense: Int = 100000 // todo
) {

}

@Composable
private fun ReportContent(
    modifier: Modifier = Modifier
) {

}

@Preview
@Composable
private fun ReportScreenPreview() {
    ReportScreen()
}