package com.moneymong.moneymong.ocr_detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.google.gson.Gson
import com.moneymong.moneymong.model.ocr.DocumentResponse
import com.moneymong.moneymong.ocr_detail.OCRDetailScreen

const val ocrDetailRoute = "ocrdetail_route?document={document}"

fun NavController.navigateOCRDetail(
    navOptions: NavOptions? = null,
    document: String
) {
    this.navigate(ocrDetailRoute.replace("{document}", document), navOptions)
}

fun NavGraphBuilder.ocrDetailScreen(
    navigateToLedger: (ledgerPostSuccess: Boolean) -> Unit,
    popBackStack: () -> Unit,
) {
    composable(route = ocrDetailRoute) { backStackEntry ->
        val match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z~!@#$%^&*()_+|<>?:{}]"
        val documentJson = backStackEntry.arguments?.getString("document")
        val documentResponse =
            documentJson?.let { Gson().fromJson(it.replace(match, ""), DocumentResponse::class.java) }

        OCRDetailScreen(
            document = documentResponse,
            navigateToLedger = navigateToLedger,
            popBackStack = popBackStack
        )
    }
}