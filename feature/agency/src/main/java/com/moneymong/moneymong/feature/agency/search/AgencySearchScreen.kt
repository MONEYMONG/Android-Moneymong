package com.moneymong.moneymong.feature.agency.search

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.moneymong.moneymong.design_system.R
import com.moneymong.moneymong.design_system.component.button.MDSFloatingActionButton
import com.moneymong.moneymong.design_system.component.tooltip.MDSToolTip
import com.moneymong.moneymong.design_system.component.tooltip.MDSToolTipPosition
import com.moneymong.moneymong.design_system.error.ErrorDialog
import com.moneymong.moneymong.design_system.theme.Gray01
import com.moneymong.moneymong.design_system.theme.MMHorizontalSpacing
import com.moneymong.moneymong.design_system.theme.Red03
import com.moneymong.moneymong.feature.agency.search.component.AgencySearchTopBar
import com.moneymong.moneymong.feature.agency.search.component.searchbar.AgencySearchBar
import com.moneymong.moneymong.ui.pxToDp
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

    BackHandler(enabled = state.visibleSearchBar) {
        viewModel.toggleVisibilitySearchBar()
    }

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
                    modifier = Modifier
                        .offset { IntOffset(x = 0, y = offsetY) }
                        .padding(bottom = offsetY.pxToDp),
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
