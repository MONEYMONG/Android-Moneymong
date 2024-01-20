package com.moneymong.moneymong.ledgerdetail

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.moneymong.moneymong.common.ui.DottedShape
import com.moneymong.moneymong.common.ui.noRippleClickable
import com.moneymong.moneymong.common.ui.toWonFormat
import com.moneymong.moneymong.design_system.R
import com.moneymong.moneymong.design_system.component.button.MDSButton
import com.moneymong.moneymong.design_system.component.button.MDSButtonSize
import com.moneymong.moneymong.design_system.component.button.MDSButtonType
import com.moneymong.moneymong.design_system.component.modal.MDSModal
import com.moneymong.moneymong.design_system.component.textfield.MDSTextField
import com.moneymong.moneymong.design_system.component.textfield.util.MDSTextFieldIcons
import com.moneymong.moneymong.design_system.component.textfield.util.PriceType
import com.moneymong.moneymong.design_system.component.textfield.visualtransformation.DateVisualTransformation
import com.moneymong.moneymong.design_system.component.textfield.visualtransformation.PriceVisualTransformation
import com.moneymong.moneymong.design_system.component.textfield.visualtransformation.TimeVisualTransformation
import com.moneymong.moneymong.design_system.theme.Blue01
import com.moneymong.moneymong.design_system.theme.Blue03
import com.moneymong.moneymong.design_system.theme.Blue04
import com.moneymong.moneymong.design_system.theme.Body2
import com.moneymong.moneymong.design_system.theme.Body3
import com.moneymong.moneymong.design_system.theme.Gray01
import com.moneymong.moneymong.design_system.theme.Gray03
import com.moneymong.moneymong.design_system.theme.Gray06
import com.moneymong.moneymong.design_system.theme.Gray10
import com.moneymong.moneymong.design_system.theme.MMHorizontalSpacing
import com.moneymong.moneymong.design_system.theme.White
import com.moneymong.moneymong.ledgerdetail.view.LedgerDetailTopbarView
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LedgerDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: LedgerDetailViewModel = hiltViewModel(),
    ledgerTransactionId: Int,
    popBackStack: () -> Unit
) {
    val state = viewModel.collectAsState().value
    val verticalScrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    viewModel.collectSideEffect {
        when (it) {
            is LedgerDetailSideEffect.LedgerDetailEdit -> {
                viewModel.onChangeEditMode(true)
                verticalScrollState.scrollTo(0)
            }

            is LedgerDetailSideEffect.LedgerDetailEditDone -> {
                viewModel.onChangeEditMode(false)
                // TODO
            }

            is LedgerDetailSideEffect.LedgerDetailFetchTransactionDetail -> {
                viewModel.fetchLedgerTransactionDetail(it.detailId)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.eventEmit(
            LedgerDetailSideEffect.LedgerDetailFetchTransactionDetail(
                ledgerTransactionId
            )
        )
    }

    if (false) { // TODO
        MDSModal(
            icon = R.drawable.ic_warning_filled, // TODO
            title = "사진을 삭제하시겠습니까?", // TODO
            description = "삭제된 사진은 되돌릴 수 없습니다", // TODO
            negativeBtnText = "취소",
            positiveBtnText = "확인",
            onClickNegative = { /*TODO*/ },
            onClickPositive = { /*TODO*/ })
    }

    Scaffold(
        topBar = {
            LedgerDetailTopbarView(
                useEditMode = state.useEditMode,
                enabledDone = state.enabled,
                onClickPrev = popBackStack,
                onClickDelete = { /*TODO*/ },
                onClickDone = { viewModel.eventEmit(LedgerDetailSideEffect.LedgerDetailEditDone) }
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(verticalScrollState)
                .background(Gray01)
                .padding(it)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = MMHorizontalSpacing)
                    .clip(RoundedCornerShape(16.dp))
                    .border(width = 1.dp, color = Blue03, shape = RoundedCornerShape(16.dp))
                    .background(White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(White)
                        .padding(vertical = 28.dp, horizontal = 16.dp)
                ) {
                    if (state.useEditMode) {
                        var isStoreNameFilled by remember { mutableStateOf(false) }
                        MDSTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusChanged { isStoreNameFilled = !it.isFocused },
                            value = state.storeNameValue,
                            onValueChange = viewModel::onChangeStoreNameValue,
                            title = "수입·지출 출처",
                            placeholder = "",
                            isFilled = isStoreNameFilled,
                            isError = state.isStoreNameError,
                            helperText = "20자 이내로 입력해주세요",
                            maxCount = 20,
                            icon = MDSTextFieldIcons.Clear,
                            singleLine = true,
                            onIconClick = { viewModel.onChangeStoreNameValue(TextFieldValue()) }
                        )
                    } else {
                        Text(
                            text = "수입·지출 출처",
                            style = Body2,
                            color = Gray06
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = state.ledgerTransactionDetail?.storeInfo.orEmpty(),
                            style = Body3,
                            color = Gray10
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Gray03, shape = DottedShape(8.dp))
                    )
                    if (state.useEditMode) {
                        var isTotalPriceFilled by remember { mutableStateOf(false) }
                        MDSTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusChanged { isTotalPriceFilled = !it.isFocused },
                            value = state.totalPriceValue,
                            onValueChange = viewModel::onChangeTotalPriceValue,
                            title = "지출 금액",
                            placeholder = "",
                            isFilled = isTotalPriceFilled,
                            isError = state.isTotalPriceError,
                            helperText = "999,999,999원 이내로 입력해주세요",
                            onIconClick = { viewModel.onChangeTotalPriceValue(TextFieldValue()) },
                            singleLine = true,
                            icon = MDSTextFieldIcons.Clear,
                            visualTransformation = PriceVisualTransformation(type = PriceType.None),
                            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    } else {
                        Text(
                            text = "지출 금액",
                            style = Body2,
                            color = Gray06
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${state.totalPrice}원",
                            style = Body3,
                            color = Gray10
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Gray03, shape = DottedShape(8.dp))
                    )
                    if (state.useEditMode) {
                        var isPaymentDateFilled by remember { mutableStateOf(false) }
                        MDSTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusChanged { isPaymentDateFilled = !it.isFocused },
                            value = state.paymentDateValue,
                            onValueChange = viewModel::onChangePaymentDateValue,
                            title = "날짜",
                            placeholder = "2024/01/01",
                            isFilled = isPaymentDateFilled,
                            isError = state.isPaymentDateError,
                            helperText = "올바른 날짜를 입력해주세요",
                            onIconClick = { viewModel.onChangePaymentDateValue(TextFieldValue()) },
                            singleLine = true,
                            icon = MDSTextFieldIcons.Clear,
                            visualTransformation = DateVisualTransformation(),
                            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    } else {
                        Text(
                            text = "날짜",
                            style = Body2,
                            color = Gray06
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = state.formattedDate,
                            style = Body3,
                            color = Gray10
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Gray03, shape = DottedShape(8.dp))
                    )
                    if (state.useEditMode) {
                        var isPaymentTimeFilled by remember { mutableStateOf(false) }
                        MDSTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusChanged { isPaymentTimeFilled = !it.isFocused },
                            value = state.paymentTimeValue,
                            onValueChange = viewModel::onChangePaymentTimeValue,
                            title = "시간",
                            placeholder = "00:00:00",
                            isFilled = isPaymentTimeFilled,
                            isError = state.isPaymentTimeError,
                            helperText = "올바른 시간을 입력해주세요",
                            onIconClick = { viewModel.onChangePaymentTimeValue(TextFieldValue()) },
                            singleLine = true,
                            icon = MDSTextFieldIcons.Clear,
                            visualTransformation = TimeVisualTransformation(),
                            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    } else {
                        Text(
                            text = "시간",
                            style = Body2,
                            color = Gray06
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = state.formattedTime,
                            style = Body3,
                            color = Gray10
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Gray03, shape = DottedShape(8.dp))
                    )
                    if (state.useEditMode) {
                        var isMemoFilled by remember { mutableStateOf(false) }
                        MDSTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusChanged { isMemoFilled = !it.isFocused },
                            value = state.memoValue,
                            onValueChange = { viewModel.onChangeMemoValue(it) },
                            title = "메모",
                            placeholder = "",
                            isFilled = isMemoFilled,
                            isError = state.isMemoError,
                            helperText = "300자 이내로 입력해주세요",
                            maxCount = 300,
                            singleLine = false,
                            icon = MDSTextFieldIcons.Clear,
                            onIconClick = { viewModel.onChangeMemoValue(TextFieldValue()) },
                            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                        )
                    } else {
                        Text(
                            text = "메모",
                            style = Body2,
                            color = Gray06
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = state.ledgerTransactionDetail?.description.orEmpty(),
                            style = Body3,
                            color = Gray10
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Gray03, shape = DottedShape(8.dp))
                    )
                    Text(
                        text = "영수증 (최대12장)",
                        style = Body2,
                        color = Gray06
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyVerticalGrid(
                        modifier = modifier
                            .fillMaxSize()
                            .heightIn(max = 324.dp)
                            .background(White),
                        columns = GridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        val receiptImages = state.ledgerTransactionDetail?.receiptImageUrls.orEmpty()
                        itemsIndexed(items = receiptImages) { index, item ->
                            Box(
                                modifier = Modifier
                                    .height(120.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            ) {
                                GlideImage(
                                    modifier = Modifier.fillMaxSize(),
                                    model = item.receiptImageUrl,
                                    contentDescription = null,
                                    contentScale = ContentScale.FillWidth
                                )
                                if (state.useEditMode) {
                                    Icon(
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .noRippleClickable {  }
                                            .padding(5.dp),
                                        painter = painterResource(id = R.drawable.ic_close_filled),
                                        contentDescription = null,
                                        tint = Color.Unspecified
                                    )
                                }
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Gray03, shape = DottedShape(8.dp))
                    )
                    Text(
                        text = "증빙자료 (최대12장)",
                        style = Body2,
                        color = Gray06
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyVerticalGrid(
                        modifier = modifier
                            .fillMaxSize()
                            .heightIn(max = 324.dp)
                            .background(White),
                        columns = GridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        val documentImages = state.ledgerTransactionDetail?.documentImageUrls.orEmpty()
                        itemsIndexed(items = documentImages) { index, item ->
                            Box(
                                modifier = Modifier
                                    .height(120.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            ) {
                                GlideImage(
                                    modifier = Modifier.fillMaxSize(),
                                    model = item.documentImageUrl,
                                    contentDescription = null,
                                    contentScale = ContentScale.FillWidth
                                )
                                if (state.useEditMode) {
                                    Icon(
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .noRippleClickable {  }
                                            .padding(5.dp),
                                        painter = painterResource(id = R.drawable.ic_close_filled),
                                        contentDescription = null,
                                        tint = Color.Unspecified
                                    )
                                }
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Gray03, shape = DottedShape(8.dp))
                    )
                    Text(
                        text = "작성자",
                        style = Body2,
                        color = Gray06
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = state.ledgerTransactionDetail?.authorName.orEmpty(),
                        style = Body3,
                        color = Gray10
                    )
                }
            }
            if (state.useEditMode) {
                MDSButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp, horizontal = 20.dp),
                    text = "완료하기",
                    enabled = state.enabled,
                    size = MDSButtonSize.MEDIUM,
                    type = MDSButtonType.PRIMARY,
                    onClick = { viewModel.eventEmit(LedgerDetailSideEffect.LedgerDetailEditDone) }
                )
            } else {
                MDSButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp, horizontal = 20.dp),
                    text = "수정하기",
                    size = MDSButtonSize.MEDIUM,
                    type = MDSButtonType.PRIMARY,
                    onClick = { viewModel.eventEmit(LedgerDetailSideEffect.LedgerDetailEdit) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LedgerDetailScreenPreview() {
    LedgerDetailScreen(
        ledgerTransactionId = 0,
        popBackStack = {}
    )
}