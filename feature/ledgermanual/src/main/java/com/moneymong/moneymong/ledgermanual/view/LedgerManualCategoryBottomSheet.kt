package com.moneymong.moneymong.ledgermanual.view

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.theme.MMHorizontalSpacing
import com.moneymong.moneymong.design_system.R
import com.moneymong.moneymong.design_system.component.bottomSheet.MDSBottomSheet
import com.moneymong.moneymong.design_system.component.button.MDSButton
import com.moneymong.moneymong.design_system.component.button.MDSButtonSize
import com.moneymong.moneymong.design_system.component.button.MDSButtonType
import com.moneymong.moneymong.design_system.component.tag.MDSOutlineTag
import com.moneymong.moneymong.design_system.component.textfield.MDSTextField
import com.moneymong.moneymong.design_system.component.textfield.util.MDSTextFieldIcons
import com.moneymong.moneymong.design_system.theme.Black
import com.moneymong.moneymong.design_system.theme.Blue04
import com.moneymong.moneymong.design_system.theme.Body2
import com.moneymong.moneymong.design_system.theme.Body3
import com.moneymong.moneymong.design_system.theme.Gray05
import com.moneymong.moneymong.design_system.theme.Gray07
import com.moneymong.moneymong.design_system.theme.Heading1
import com.moneymong.moneymong.design_system.theme.Heading4
import com.moneymong.moneymong.design_system.theme.White
import com.moneymong.moneymong.model.agency.CategoryResponse
import com.moneymong.moneymong.ui.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LedgerManualCategoryBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    categories: List<CategoryResponse>?,
    categoryValue: TextFieldValue,
    isSystemCategoryError: Boolean,
    onDismissRequest: () -> Unit,
    onChangeCategoryValue: (TextFieldValue) -> Unit,
    onCategoryCreate: () -> Unit,
) {
    var sheetType by remember { mutableStateOf(LedgerManualBottomSheetType.LIST) }

    MDSBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
    ) {
        AnimatedContent(
            targetState = sheetType,
            transitionSpec = {
                when (targetState) {
                    LedgerManualBottomSheetType.CREATE -> {
                        slideInHorizontally { fullWidth -> fullWidth }
                            .togetherWith(slideOutHorizontally { fullWidth -> fullWidth / -3 })
                    }

                    LedgerManualBottomSheetType.LIST -> {
                        slideInHorizontally { fullWidth -> -fullWidth }
                            .togetherWith(slideOutHorizontally { fullWidth -> fullWidth / 3 })
                    }
                }
            }
        ) { targetState ->
            when (targetState) {
                LedgerManualBottomSheetType.LIST -> {
                    LedgerManualCategoryBottomSheetContent(
                        categories = categories,
                        onDismissRequest = onDismissRequest,
                        onClickCreate = { sheetType = LedgerManualBottomSheetType.CREATE }
                    )
                }

                LedgerManualBottomSheetType.CREATE -> {
                    LedgerManualCategoryCreateBottomSheetContent(
                        textFieldValue = categoryValue,
                        isSystemCategoryError = isSystemCategoryError,
                        categories = categories,
                        onValueChange = onChangeCategoryValue,
                        onClickRegister = onCategoryCreate,
                        onPrev = { sheetType = LedgerManualBottomSheetType.LIST }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LedgerManualCategoryBottomSheetContent(
    modifier: Modifier = Modifier,
    categories: List<CategoryResponse>?,
    onDismissRequest: () -> Unit,
    onClickCreate: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(448.dp)
            .background(White)
            .padding(horizontal = MMHorizontalSpacing, vertical = 20.dp),
    ) {
        Icon(
            modifier = Modifier
                .align(alignment = Alignment.End)
                .noRippleClickable(onDismissRequest),
            painter = painterResource(R.drawable.ic_close_default),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                style = Heading4,
                color = Black,
                text = "카테고리",
            )
            Text(
                modifier = Modifier.noRippleClickable(onClickCreate),
                style = Body3,
                color = Blue04,
                text = "추가",
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            style = Body2,
            color = Gray05,
            text = "원하는 카테고리를 마음대로 만들 수 있어요",
        )
        Spacer(modifier = Modifier.height(16.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            categories?.forEach {
                MDSOutlineTag(
                    text = it.name,
                    iconResource = R.drawable.ic_close_default,
                    onClick = {},
                )
            }
        }
    }
}

@Composable
fun LedgerManualCategoryCreateBottomSheetContent(
    modifier: Modifier = Modifier,
    textFieldValue: TextFieldValue,
    isSystemCategoryError: Boolean,
    categories: List<CategoryResponse>?,
    onValueChange: (TextFieldValue) -> Unit,
    onClickRegister: () -> Unit,
    onPrev: () -> Unit,
) {
    val maxCount = 10
    var isFilled by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current
    val isExists = categories?.any { it.name == textFieldValue.text } ?: false
    val helperText by remember(isSystemCategoryError, isExists) {
        derivedStateOf {
            when {
                isSystemCategoryError -> "사용할 수 없는 카테고리 이름이에요"
                isExists -> "이미 있는 카테고리에요"
                else -> ""
            }
        }
    }

    BackHandler {
        onPrev()
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .padding(horizontal = MMHorizontalSpacing, vertical = 20.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .noRippleClickable {
                            keyboard?.hide()
                            onPrev()
                        },
                    painter = painterResource(R.drawable.ic_chevron_left),
                    contentDescription = null,
                    tint = Gray07,
                )
                Text(
                    text = "카테고리 생성",
                    style = Heading1,
                    color = Black,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            MDSTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { isFilled = !it.isFocused }
                    .focusRequester(focusRequester),
                placeholder = "카테고리를 입력해주세요",
                value = textFieldValue,
                onValueChange = onValueChange,
                title = "",
                isFilled = isFilled,
                isError = isSystemCategoryError || isExists,
                singleLine = true,
                helperText = helperText,
                icon = MDSTextFieldIcons.Clear,
                onIconClick = { onValueChange(TextFieldValue("")) },
                maxCount = maxCount
            )
        }
        val enabled = textFieldValue.text.isNotBlank() && (!isSystemCategoryError && !isExists)
        MDSButton(
            modifier = Modifier.fillMaxWidth(),
            text = "등록",
            type = MDSButtonType.PRIMARY,
            size = MDSButtonSize.LARGE,
            cornerShape = 0.dp,
            enabled = enabled,
            onClick = onClickRegister,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LedgerManualCategoryBottomSheetContentPreview() {
    val categories = listOf(CategoryResponse("testTooLongTextOverFlow"), CategoryResponse("test"))

    LedgerManualCategoryBottomSheetContent(
        categories = categories,
        onDismissRequest = {},
    ) {}
}

@Preview(showBackground = true)
@Composable
fun LedgerManualCategoryCreateBottomSheetContentPreview() {
    LedgerManualCategoryCreateBottomSheetContent(
        textFieldValue = TextFieldValue(),
        isSystemCategoryError = false,
        categories = emptyList(),
        onValueChange = {},
        onClickRegister = {}
    ) {}
}