package com.moneymong.moneymong.feature.agency.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.moneymong.moneymong.common.error.MoneyMongError
import com.moneymong.moneymong.design_system.R
import com.moneymong.moneymong.design_system.component.indicator.LoadingItem
import com.moneymong.moneymong.design_system.component.indicator.LoadingScreen
import com.moneymong.moneymong.design_system.error.ErrorItem
import com.moneymong.moneymong.design_system.error.ErrorScreen
import com.moneymong.moneymong.design_system.theme.Body3
import com.moneymong.moneymong.design_system.theme.Gray07
import com.moneymong.moneymong.feature.agency.search.item.AgencyFeedbackItem
import com.moneymong.moneymong.feature.agency.search.item.AgencyItem

@Composable
internal fun AgencySearchContentView(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<Agency>,
    searchedAgencies: List<Agency>,
    onClickItem: (agencyId: Long) -> Unit,
    onClickFeedbackItem: () -> Unit,
    isSearched: Boolean,
    isLoading: Boolean,
    isError: Boolean,
    errorMessage: String,
    onRetry: () -> Unit,
) {
    val contentLoading = pagingItems.loadState.refresh is LoadState.Loading || isLoading
    val contentError = pagingItems.loadState.refresh is LoadState.Error || isError
    val contentErrorMessage = errorMessage.ifEmpty {
        (pagingItems.loadState.refresh as? LoadState.Error)?.error?.message
            ?: MoneyMongError.UnExpectedError.message
    }

    when {
        contentLoading -> LoadingScreen(modifier = modifier.fillMaxSize())

        contentError ->
            ErrorScreen(
                modifier = modifier.fillMaxSize(),
                message = contentErrorMessage,
                onRetry = {
                    pagingItems.retry()
                    onRetry()
                },
            )

        isSearched ->
            ContentViewWithAgencies(
                modifier = modifier,
                agencies = searchedAgencies,
                onClickItem = onClickItem,
                onClickFeedbackItem = onClickFeedbackItem
            )

        pagingItems.itemCount == 0 ->
            ContentViewWithoutAgencies(
                modifier = modifier,
                pagingItems = pagingItems,
                onClickFeedbackItem = onClickFeedbackItem
            )


        else ->
            ContentViewWithAgencies(
                modifier = modifier,
                pagingItems = pagingItems,
                onClickItem = onClickItem,
                onClickFeedbackItem = onClickFeedbackItem
            )
    }
}


@Composable
private fun ContentViewWithAgencies(
    modifier: Modifier = Modifier,
    agencies: List<Agency>,
    onClickItem: (agencyId: Long) -> Unit,
    onClickFeedbackItem: () -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 6.dp)
    ) {
        item {
            AgencyFeedbackItem(onClick = onClickFeedbackItem)
        }
        items(count = agencies.size, key = { agencies[it].id }) {
            AgencyItem(
                agency = agencies[it],
                onClick = { onClickItem(agencies[it].id) }
            )
        }
    }
}

@Composable
private fun ContentViewWithAgencies(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<Agency>,
    onClickItem: (agencyId: Long) -> Unit,
    onClickFeedbackItem: () -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 6.dp)
    ) {
        item {
            AgencyFeedbackItem(
                onClick = onClickFeedbackItem
            )
        }
        items(
            count = pagingItems.itemCount, key = pagingItems.itemKey { it.id }
        ) {
            pagingItems[it]?.let { agency ->
                AgencyItem(
                    agency = agency,
                    onClick = { onClickItem(agency.id) }
                )
            }
        }

        when (pagingItems.loadState.source.append) {
            is LoadState.Loading -> {
                item {
                    LoadingItem(modifier = Modifier.fillMaxWidth())
                }
            }

            is LoadState.Error -> {
                val e = pagingItems.loadState.source.append as LoadState.Error
                item {
                    ErrorItem(
                        modifier = Modifier.fillMaxWidth(),
                        message = "${e.error.message}",
                        onRetry = pagingItems::retry
                    )
                }
            }

            is LoadState.NotLoading -> Unit
        }
    }
}

@Composable
private fun ContentViewWithoutAgencies(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<Agency>,
    onClickFeedbackItem: () -> Unit
) {

    when (pagingItems.loadState.refresh) {
        is LoadState.Loading -> {
            LoadingScreen(modifier = modifier.fillMaxSize())
        }

        is LoadState.Error -> {
            val e = pagingItems.loadState.refresh as LoadState.Error
            ErrorScreen(
                modifier = modifier.fillMaxSize(),
                message = "${e.error.message}",
                onRetry = pagingItems::retry
            )
        }

        is LoadState.NotLoading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                AgencyFeedbackItem(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 10.dp),
                    onClick = onClickFeedbackItem
                )
                Column(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 4.dp,
                        alignment = Alignment.CenterVertically
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(size = 80.dp),
                        painter = painterResource(id = R.drawable.img_agency),
                        contentDescription = "agency image",
                    )
                    Text(
                        text = "아직 등록된 소속이 없어요\n하단 버튼을 통해 등록해보세요",
                        textAlign = TextAlign.Center,
                        color = Gray07,
                        style = Body3
                    )
                }
            }
        }
    }
}