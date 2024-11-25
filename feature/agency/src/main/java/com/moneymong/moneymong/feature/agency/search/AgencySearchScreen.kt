package com.moneymong.moneymong.feature.agency.search

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.moneymong.moneymong.common.error.MoneyMongError
import com.moneymong.moneymong.design_system.R
import com.moneymong.moneymong.design_system.component.button.MDSFloatingActionButton
import com.moneymong.moneymong.design_system.component.indicator.LoadingItem
import com.moneymong.moneymong.design_system.component.indicator.LoadingScreen
import com.moneymong.moneymong.design_system.component.tooltip.MDSToolTip
import com.moneymong.moneymong.design_system.component.tooltip.MDSToolTipPosition
import com.moneymong.moneymong.design_system.error.ErrorDialog
import com.moneymong.moneymong.design_system.error.ErrorItem
import com.moneymong.moneymong.design_system.error.ErrorScreen
import com.moneymong.moneymong.design_system.theme.Body3
import com.moneymong.moneymong.design_system.theme.Gray01
import com.moneymong.moneymong.design_system.theme.Gray07
import com.moneymong.moneymong.design_system.theme.MMHorizontalSpacing
import com.moneymong.moneymong.design_system.theme.Red03
import com.moneymong.moneymong.feature.agency.search.component.AgencySearchTopBar
import com.moneymong.moneymong.feature.agency.search.component.searchbar.AgencySearchBar
import com.moneymong.moneymong.feature.agency.search.item.AgencyFeedbackItem
import com.moneymong.moneymong.feature.agency.search.item.AgencyItem
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun AgencySearchScreen(
    modifier: Modifier = Modifier,
    viewModel: AgencySearchViewModel = hiltViewModel(),
    navigateToRegister: (isUniversityStudent: Boolean) -> Unit,
    navigateAgencyJoin: (agencyId: Long) -> Unit
) {
    val state by viewModel.collectAsState()
    val context = LocalContext.current
    val pagingItems = viewModel.agencies.collectAsLazyPagingItems()

    viewModel.collectSideEffect {
        when (it) {
            is AgencySearchSideEffect.NavigateToRegister -> {
                navigateToRegister(it.isUniversityStudent)
            }

            is AgencySearchSideEffect.NavigateToAgencyJoin -> {
                navigateAgencyJoin(it.agencyId)
            }

            is AgencySearchSideEffect.FollowAsk -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://asked.kr/moneymong"))
                context.startActivity(intent)
            }
        }
    }

    if (state.visibleWarningDialog) {
        ErrorDialog(
            message = "이미 가입한 소속입니다",
            description = "장부 페이지에서 가입한 소속을 확인해보세요",
            onConfirm = {
                viewModel.changeVisibleWarningDialog(false)
            }
        )
    }

    var searchBarHeight by remember { mutableIntStateOf(0) }
    val offsetY by animateIntAsState(
        targetValue = if (state.visibleSearchBar) searchBarHeight else 0,
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearEasing
        ),
        label = "Content Offset Y"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Gray01)
            .padding(horizontal = MMHorizontalSpacing)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AgencySearchTopBar(
                onSearchIconClick = viewModel::toggleVisibilitySearchBar,
                visibleSearchIcon = state.visibleSearchBar.not()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(modifier = Modifier.fillMaxSize()) {
                AgencySearchBar(
                    modifier = Modifier.onSizeChanged { searchBarHeight = it.height },
                    state = state.searchTextFieldState,
                    visible = state.visibleSearchBar,
                    onSearch = viewModel::searchAgency,
                    onClear = viewModel::clearSearchTextField,
                    onCancel = viewModel::toggleVisibilitySearchBar,
                )
                AgencySearchContentView(
                    modifier = Modifier.offset { IntOffset(x = 0, y = offsetY) },
                    pagingItems = pagingItems,
                    searchedAgencies = state.searchedAgencies,
                    onClickItem = { agencyId ->
                        if (agencyId in state.joinedAgenciesIds) {
                            viewModel.changeVisibleWarningDialog(true)
                        } else {
                            viewModel.navigateToJoin(agencyId)
                        }
                    },
                    onClickFeedbackItem = viewModel::onClickAskFeedback,
                    isLoading = state.isLoading,
                    isError = state.isError,
                    errorMessage = state.errorMessage,
                    onRetry = viewModel::getInitialData,
                )
            }
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 20.dp),
            horizontalAlignment = Alignment.End
        ) {
            if (pagingItems.itemCount == 0 && pagingItems.loadState.refresh is LoadState.NotLoading) {
                MDSToolTip(
                    text = "소속이 없다면 등록해보세요",
                    position = MDSToolTipPosition.Right
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            MDSFloatingActionButton(
                onClick = viewModel::navigateToRegister,
                iconResource = R.drawable.ic_plus_default,
                containerColor = Red03
            )
        }
    }
}

@Composable
private fun AgencySearchContentView(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<Agency>,
    searchedAgencies: List<Agency>,
    onClickItem: (agencyId: Long) -> Unit,
    onClickFeedbackItem: () -> Unit,
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

        searchedAgencies.isNotEmpty() ->
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
        contentPadding = PaddingValues(bottom = 4.dp)
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
        contentPadding = PaddingValues(bottom = 4.dp)
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