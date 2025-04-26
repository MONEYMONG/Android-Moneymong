package com.moneymong.moneymong.feature.agency.register.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.component.selection.MDSSelection
import com.moneymong.moneymong.design_system.component.textfield.MDSTextField
import com.moneymong.moneymong.design_system.component.textfield.util.MDSTextFieldIcons
import com.moneymong.moneymong.design_system.component.textfield.util.withRequiredMark
import com.moneymong.moneymong.design_system.theme.Body2
import com.moneymong.moneymong.design_system.theme.Body3
import com.moneymong.moneymong.design_system.theme.Gray05
import com.moneymong.moneymong.design_system.theme.Gray06
import com.moneymong.moneymong.design_system.theme.Gray10
import com.moneymong.moneymong.design_system.theme.Heading5
import com.moneymong.moneymong.feature.agency.search.AgencyType

@Composable
internal fun AgencyResisterContentView(
    modifier: Modifier = Modifier,
    agencyType: AgencyType?,
    onAgencyTypeChange: (AgencyType) -> Unit,
    agencyName: TextFieldValue,
    onAgencyNameChange: (TextFieldValue) -> Unit,
    changeNameTextFieldIsError: (Boolean) -> Unit,
    registrableClubOrCouncil: Boolean
) {
    Column(modifier = modifier) {
        TitleView()
        Spacer(modifier = Modifier.height(28.dp))
        InputNameView(
            agencyName = agencyName,
            onAgencyNameChange = onAgencyNameChange,
            changeIsError = changeNameTextFieldIsError
        )
    }
}


@Composable
private fun TitleView() {
    Text(
        text = "장부 생성하기",
        color = Gray10,
        style = Heading5
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = "사용할 장부는 언제든지 추가로 만들 수 있어요",
        color = Gray05,
        style = Body3
    )
    Spacer(modifier = Modifier.height(16.dp))
}


@Composable
private fun SelectTypeView(
    agencyType: AgencyType?,
    onAgencyTypeChange: (AgencyType) -> Unit,
    registrableClubOrCouncil: Boolean
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "소속 유형",
            color = Gray06,
            style = Body2
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MDSSelection(
                modifier = Modifier.weight(1f),
                text = AgencyType.CLUB.text,
                enabled = registrableClubOrCouncil,
                isSelected = agencyType == AgencyType.CLUB,
                onClick = { onAgencyTypeChange(AgencyType.CLUB) }
            )
            MDSSelection(
                modifier = Modifier.weight(1f),
                text = AgencyType.COUNCIL.text,
                enabled = registrableClubOrCouncil,
                isSelected = agencyType == AgencyType.COUNCIL,
                onClick = { onAgencyTypeChange(AgencyType.COUNCIL) }
            )
            MDSSelection(
                modifier = Modifier.weight(1f),
                text = AgencyType.GENERAL.text,
                isSelected = agencyType == AgencyType.GENERAL || registrableClubOrCouncil.not(),
                onClick = { onAgencyTypeChange(AgencyType.GENERAL) }
            )
        }
    }
}


@Composable
private fun InputNameView(
    agencyName: TextFieldValue,
    onAgencyNameChange: (TextFieldValue) -> Unit,
    changeIsError: (Boolean) -> Unit
) {
    fun validate(text: String, maxCount: Int) = text.length > maxCount

    val agencyNameValue by rememberUpdatedState(newValue = agencyName)
    val maxCount = 20
    var isFilled by remember { mutableStateOf(false) }
    val isError by remember {
        derivedStateOf {
            validate(
                text = agencyNameValue.text,
                maxCount = maxCount
            )
        }
    }

    LaunchedEffect(key1 = isError) {
        changeIsError(isError)
    }

    val focusManager = LocalFocusManager.current
    fun filterText(text: String) = text.filter { it.isLetterOrDigit() || it == ' ' }

    MDSTextField(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { isFilled = !it.isFocused },
        value = agencyNameValue,
        onValueChange = { onAgencyNameChange(it.copy(text = filterText(it.text))) },
        title = withRequiredMark("장부"),
        placeholder = "소속 이름을 입력해주세요",
        isFilled = isFilled,
        isError = isError,
        helperText = "${maxCount}자 이하로 입력해주세요",
        maxCount = maxCount,
        singleLine = true,
        icon = MDSTextFieldIcons.Clear,
        onIconClick = { onAgencyNameChange(agencyName.copy(text = "")) },
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
        })
    )
}


@Preview(showBackground = true)
@Composable
private fun AgencyResisterContentViewPreview() {
    AgencyResisterContentView(
        agencyType = AgencyType.GENERAL,
        onAgencyTypeChange = {},
        agencyName = TextFieldValue("동아리"),
        onAgencyNameChange = {},
        changeNameTextFieldIsError = {},
        registrableClubOrCouncil = true
    )
}