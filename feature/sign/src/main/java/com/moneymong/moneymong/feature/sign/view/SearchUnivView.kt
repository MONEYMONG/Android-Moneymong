package com.moneymong.moneymong.feature.sign.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.component.textfield.MDSTextField
import com.moneymong.moneymong.design_system.component.textfield.util.MDSTextFieldIcons
import com.moneymong.moneymong.design_system.theme.Body4
import com.moneymong.moneymong.design_system.theme.Gray05
import com.moneymong.moneymong.design_system.theme.White
import com.moneymong.moneymong.feature.sign.item.UnivItem
import com.moneymong.moneymong.model.sign.UniversitiesResponse
import com.moneymong.moneymong.model.sign.University
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class)
@Composable
fun SearchUnivView(
    modifier: Modifier = Modifier,
    isFilled: Boolean,
    isFilledChanged: (Boolean) -> Unit,
    isListVisible: Boolean,
    isListVisibleChanged: (Boolean) -> Unit,
    isItemSelectedChanged: (Boolean) -> Unit,
    onClick: (String) -> Unit,
    onChange: (TextFieldValue) -> Unit,
    onSearchIconClicked: (String) -> Unit,
    isItemSelected: Boolean,
    textValue: TextFieldValue,
    universityResponse: UniversitiesResponse?,
    value: TextFieldValue,
    isButtonVisibleChanged: (Boolean) -> Unit,
    selectedUniv: String,
    changeButtonCornerShape: (Dp) -> Unit,
    changeEditTextFocus: (Boolean) -> Unit
) {

    val focusManager = LocalFocusManager.current

    val debouncePeriod = 300L
    val queryState = remember { MutableStateFlow("") }

    LaunchedEffect(Unit) {
        queryState
            .debounce(debouncePeriod)
            .collect { query ->
                Log.d("query", query)
                if (query.isEmpty() && value.text.isNotEmpty()) {
                    onSearchIconClicked(value.text)
                } else {
                    onSearchIconClicked(query)
                }
                isFilledChanged(false)
            }
    }


    Column(
        modifier = modifier.background(White)
    ) {
        MDSTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    changeEditTextFocus(focusState.isFocused)
                    if (focusState.isFocused) {
                        isButtonVisibleChanged(false)
                        changeButtonCornerShape(0.dp)
                    }
                },
            value = value,
            onValueChange = {
                isListVisibleChanged(true)
                onChange(it)
                queryState.value = it.text
            },
            title = "대학교",
            placeholder = "ex) 머니대학교",
            isFilled = isFilled,
            isError = false,
            maxCount = null,
            singleLine = true,
            icon = MDSTextFieldIcons.Search,
            onIconClick = {
                if (value.text.isEmpty()) {
                    isListVisibleChanged(false)
                } else {
                    onSearchIconClicked(textValue.toString())
                    isFilledChanged(true)
                    isListVisibleChanged(true)
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(
                onDone = {
                    isFilledChanged(true)
                    changeButtonCornerShape(10.dp)
                    focusManager.clearFocus()
                    changeEditTextFocus(false)
                }
            )
        )

        if (isListVisible) {
            if (universityResponse?.universities?.isNotEmpty() == true) {
                isButtonVisibleChanged(false)
                UnivList(
                    isItemSelected = isItemSelected,
                    isItemSelectedChanged = isItemSelectedChanged,
                    univs = universityResponse.universities,
                    onClick = onClick,
                    isButtonVisibleChanged = isButtonVisibleChanged,
                    selectedUniv = selectedUniv
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 28.dp)
                ) {
                    Text(
                        text = "검색결과가 없습니다",
                        style = Body4,
                        color = Gray05
                    )
                }
            }
        }
    }
}


@Composable
fun UnivList(
    isItemSelected: Boolean,
    isItemSelectedChanged: (Boolean) -> Unit,
    univs: List<University>,
    onClick: (String) -> Unit,
    isButtonVisibleChanged: (Boolean) -> Unit,
    selectedUniv: String
) {
    LazyColumn {
        items(univs) { univ ->
            UnivItem(
                isItemSelected = isItemSelected,
                isItemSelectedChanged = isItemSelectedChanged,
                univs = univ,
                onClick = onClick,
                isButtonVisibleChanged = isButtonVisibleChanged,
                selectedUniv = selectedUniv
            )
        }
    }
}
