package com.moneymong.moneymong.feature.agency.join.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.theme.Blue04
import com.moneymong.moneymong.design_system.theme.Body2
import com.moneymong.moneymong.design_system.theme.Red02
import com.moneymong.moneymong.design_system.theme.White
import com.moneymong.moneymong.feature.agency.join.component.invitecode.InviteCodeField


internal const val CODE_MAX_SIZE = 6

@Composable
fun AgencyInviteCodeView(
    modifier: Modifier = Modifier,
    isError: Boolean,
    inputCode: String,
    onValueChanged: (String) -> Unit,
    checkInviteCode: () -> Unit,
) {

    val value by rememberUpdatedState(newValue = inputCode)
    val allNumbersEntered by remember { derivedStateOf { value.length == CODE_MAX_SIZE } }

    LaunchedEffect(key1 = allNumbersEntered) {
        if (allNumbersEntered) {
            checkInviteCode()
        }
    }

    Column(
        modifier = modifier.background(White)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(White),
            text = "초대코드",
            color = if (!isError) Blue04 else Red02,
            style = Body2
        )
        Spacer(modifier = Modifier.height(8.dp))
        InviteCodeField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChanged,
            isError = isError
        )
    }
}