package com.moneymong.moneymong.ledger

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavOptions
import com.example.member.MemberScreen
import com.moneymong.moneymong.common.event.Event
import com.moneymong.moneymong.common.ui.plus
import com.moneymong.moneymong.design_system.R
import com.moneymong.moneymong.design_system.component.bottomSheet.MDSBottomSheet
import com.moneymong.moneymong.design_system.component.button.FABIconSize
import com.moneymong.moneymong.design_system.component.button.MDSFloatingActionButton
import com.moneymong.moneymong.design_system.component.datepicker.MDSWheelDatePicker
import com.moneymong.moneymong.design_system.component.indicator.MDSRefreshIndicator
import com.moneymong.moneymong.design_system.component.snackbar.MDSSnackbarHost
import com.moneymong.moneymong.design_system.error.ErrorDialog
import com.moneymong.moneymong.design_system.theme.MMHorizontalSpacing
import com.moneymong.moneymong.design_system.theme.Mint02
import com.moneymong.moneymong.design_system.theme.Mint03
import com.moneymong.moneymong.design_system.theme.White
import com.moneymong.moneymong.ledger.view.LedgerAgencyEmptyView
import com.moneymong.moneymong.ledger.view.LedgerAgencySelectBottomSheet
import com.moneymong.moneymong.ledger.view.LedgerDefaultView
import com.moneymong.moneymong.ledger.view.LedgerTab
import com.moneymong.moneymong.ledger.view.LedgerTabRowView
import com.moneymong.moneymong.ledger.view.LedgerTopbarView
import com.moneymong.moneymong.ledger.view.onboarding.OnboardingComponentState
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class
)
@Composable
fun LedgerScreen(
    modifier: Modifier = Modifier,
    viewModel: LedgerViewModel = hiltViewModel(),
    padding: PaddingValues,
    navigateToAgencyRegister: () -> Unit,
    navigateToOCR: (NavOptions?) -> Unit,
    navigateToLedgerDetail: (NavOptions?, Int, Boolean) -> Unit,
    navigateToLedgerManual: (NavOptions?) -> Unit
) {
    val state = viewModel.collectAsState().value
    var expandableFab by remember { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(if (expandableFab) 45f else 0f, label = "")
    val tabs = listOf(LedgerTab.Ledger, LedgerTab.Member)
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var addFABState by remember { mutableStateOf(OnboardingComponentState()) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = viewModel::fetchLedgerTransactionList
    )

    LaunchedEffect(Unit) {
        viewModel.fetchMyAgencyList()
        viewModel.fetchAgencyMemberList()
        viewModel.fetchAgencyExistLedger()
    }

    viewModel.collectSideEffect {
        when (it) {
            is LedgerSideEffect.LedgerNavigateToLedgerDetail -> {
                navigateToLedgerDetail(null, it.id, state.isStaff)
            }

            is LedgerSideEffect.LedgerNavigateToOCR -> {
                navigateToOCR(null)
            }

            is LedgerSideEffect.LedgerNavigateToLedgerManual -> {
                navigateToLedgerManual(null)
            }

            is LedgerSideEffect.LedgerOpenSheet -> {
                viewModel.onChangeSheetState(true)
            }

            is LedgerSideEffect.LedgerCloseSheet -> {
                sheetState.hide()
                viewModel.onChangeSheetState(false)
            }

            is LedgerSideEffect.LedgerFetchRetry -> {
                viewModel.fetchDefaultInfo()
                viewModel.fetchMyAgencyList()
                viewModel.fetchAgencyMemberList()
                viewModel.fetchAgencyExistLedger()
                viewModel.fetchLedgerTransactionList()
            }

            is LedgerSideEffect.LedgerSelectedAgencyChange -> {
                viewModel.reFetchLedgerData(it.agencyId)
                viewModel.eventEmit(LedgerSideEffect.LedgerCloseSheet)
            }

            is LedgerSideEffect.LedgerVisibleSnackbar -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = it.message,
                        withDismissAction = it.withDismissAction,
                        actionLabel = ""
                    )
                }
            }
        }
    }

    if (state.visibleError) {
        ErrorDialog(
            message = state.errorMessage
        ) {
            viewModel.onChangeVisibleErrorDialog(false)
        }
    }

    Scaffold(
        modifier = Modifier.pullRefresh(pullRefreshState),
        topBar = {
            LedgerTopbarView(
                modifier = Modifier.background(White),
                header = state.currentAgency?.name ?: "장부",
                icon = R.drawable.ic_chevron_bottom,
                visibleArrow = state.agencyList.isNotEmpty(),
                onClickDownArrow = viewModel::onClickAgencyChange
            )
        },
        snackbarHost = {
            MDSSnackbarHost(
                modifier = Modifier.padding(
                    start = MMHorizontalSpacing,
                    bottom = 12.dp + padding.calculateBottomPadding(),
                    end = MMHorizontalSpacing
                ),
                hostState = snackbarHostState
            )
        }
    ) {
        if (state.showBottomSheet) {
            MDSBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { viewModel.eventEmit(LedgerSideEffect.LedgerCloseSheet) },
                content = {
                    when (state.sheetType) {
                        LedgerSheetType.Agency -> {
                            LedgerAgencySelectBottomSheet(
                                currentAgencyId = state.agencyId,
                                agencyList = state.agencyList,
                                onClickItem = {
                                    viewModel.eventEmit(
                                        LedgerSideEffect.LedgerSelectedAgencyChange(
                                            it
                                        )
                                    )
                                }
                            )
                        }

                        LedgerSheetType.DatePicker -> {
                            MDSWheelDatePicker(
                                startDate = state.startDate,
                                endDate = state.endDate,
                                confirmDateChange = viewModel::onClickDateChange,
                                onDismissRequest = { viewModel.eventEmit(LedgerSideEffect.LedgerCloseSheet) }
                            )
                        }
                    }
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it + padding)
        ) {
            if (!state.existAgency) {
                LedgerAgencyEmptyView(onClickAgencyRegister = navigateToAgencyRegister)
            } else {
                LedgerTabRowView(
                    tabs = tabs,
                    selectedTabIndex = pagerState.currentPage,
                    onScrollToPage = {
                        coroutineScope.launch { pagerState.animateScrollToPage(it) }
                    }
                )
                HorizontalPager(state = pagerState) { index ->
                    if (tabs[index] == LedgerTab.Ledger) {
                        Box(modifier = modifier.fillMaxSize()) {
                            LedgerDefaultView(
                                totalBalance = state.ledgerTransaction?.totalBalance
                                    ?: 0,
                                ledgerDetails = state.filterTransactionList,
                                transactionType = state.transactionType,
                                startDate = state.startDate,
                                endDate = state.endDate,
                                hasTransaction = state.hasTransaction,
                                isLoading = state.isLoading,
                                isExistLedger = state.isExistLedger,
                                isStaff = state.isStaff,
                                onChangeTransactionType = viewModel::onChangeTransactionType,
                                onClickPeriod = viewModel::onClickPeriod,
                                onClickTransactionItem = {
                                    viewModel.eventEmit(
                                        LedgerSideEffect.LedgerNavigateToLedgerDetail(
                                            it
                                        )
                                    )
                                },
                                addFABState = addFABState,
                                visibleOnboarding = state.visibleOnboarding,
                                onDismissOnboarding = viewModel::onDismissOnboarding
                            )
                            if (state.isStaff) {
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(end = 20.dp, bottom = 20.dp),
                                    horizontalAlignment = Alignment.End
                                ) {
                                    AnimatedVisibility(
                                        visible = expandableFab,
                                        enter = slideInVertically(
                                            initialOffsetY = { fullHeight -> fullHeight },
                                            animationSpec = tween(
                                                durationMillis = 250,
                                                easing = LinearOutSlowInEasing
                                            )
                                        ),
                                        exit = slideOutVertically(
                                            targetOffsetY = { fullHeight -> fullHeight },
                                            animationSpec = tween(
                                                durationMillis = 150,
                                                easing = FastOutLinearInEasing
                                            )
                                        )
                                    ) {
                                        MDSFloatingActionButton(
                                            iconResource = R.drawable.ic_scan,
                                            iconSize = FABIconSize(
                                                width = 30.dp,
                                                height = 24.dp
                                            ),
                                            containerColor = Mint03,
                                            onClick = viewModel::onClickLedgerRegisterOCR
                                        )
                                    }
                                    if (expandableFab) Spacer(modifier = Modifier.height(10.dp))
                                    AnimatedVisibility(
                                        visible = expandableFab,
                                        enter = slideInVertically(
                                            initialOffsetY = { fullHeight -> fullHeight },
                                            animationSpec = tween(
                                                durationMillis = 150,
                                                easing = LinearOutSlowInEasing
                                            )
                                        ),
                                        exit = slideOutVertically(
                                            targetOffsetY = { fullHeight -> fullHeight },
                                            animationSpec = tween(
                                                durationMillis = 250,
                                                easing = FastOutLinearInEasing
                                            )
                                        )
                                    ) {
                                        MDSFloatingActionButton(
                                            iconResource = R.drawable.ic_pencil,
                                            containerColor = Mint03,
                                            onClick = viewModel::onClickLedgerRegisterManual
                                        )
                                    }
                                    if (expandableFab) Spacer(modifier = Modifier.height(10.dp))
                                    val containerColor =
                                        if (expandableFab) Mint02 else Mint03
                                    MDSFloatingActionButton(
                                        modifier = Modifier
                                            .rotate(rotationAngle)
                                            .onGloballyPositioned { layoutCoordinates ->
                                                addFABState = OnboardingComponentState(
                                                    offset = layoutCoordinates.localToRoot(Offset.Zero),
                                                    size = layoutCoordinates.size
                                                )
                                            },
                                        iconResource = R.drawable.ic_plus_default,
                                        containerColor = containerColor,
                                        onClick = {
                                            viewModel.eventTracker.logEvent(Event.PLUS_CLICK)
                                            expandableFab = !expandableFab
                                        }
                                    )
                                }
                            }
                        }
                    } else {
                        Box(modifier = modifier.fillMaxSize()) {
                            MemberScreen(
                                agencyId = state.agencyId,
                                agencyList = state.agencyList,
                                onClickItem = {
                                    viewModel.eventEmit(
                                        LedgerSideEffect.LedgerSelectedAgencyChange(
                                            it
                                        )
                                    )
                                },
                                changeAgencyList = { viewModel.changeAgencyList(it) })
                        }
                    }
                }
            }
        }
        MDSRefreshIndicator(
            pullRefreshState = pullRefreshState,
            isRefreshing = state.isRefreshing
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LedgerScreenPreview() {
    LedgerScreen(
        padding = PaddingValues(),
        navigateToAgencyRegister = {},
        navigateToOCR = {},
        navigateToLedgerDetail = { navOptions, i, b -> },
        navigateToLedgerManual = {}
    )
}