package com.moneymong.moneymong.feature.agency.join.component.invitecode

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.R
import com.moneymong.moneymong.design_system.theme.Black
import com.moneymong.moneymong.design_system.theme.Heading1
import com.moneymong.moneymong.feature.agency.join.component.CODE_MAX_SIZE

@Composable
internal fun InviteCodeField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false
) {
    val focusManager = LocalFocusManager.current

    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                val containerModifier = Modifier
                    .aspectRatio(45 / 72f)
                    .weight(1f)

                if (isError) {
                    value.forEach { char ->
                        InviteNumberFieldContainer(
                            modifier = containerModifier,
                            text = char,
                            state = InviteCodeFieldState.ERROR
                        )
                    }
                } else {
                    value.forEach { char ->
                        InviteNumberFieldContainer(
                            modifier = containerModifier,
                            text = char,
                            state = InviteCodeFieldState.FILLED
                        )
                    }
                    if (value.length < CODE_MAX_SIZE) {
                        InviteNumberFieldContainer(
                            modifier = containerModifier,
                            text = ' ',
                            state = InviteCodeFieldState.FOCUSED
                        )
                    }
                    repeat(CODE_MAX_SIZE - value.length - 1) {
                        InviteNumberFieldContainer(
                            modifier = containerModifier,
                            text = ' ',
                            state = InviteCodeFieldState.EMPTY
                        )
                    }
                }
            }
        }
    )
}


@Composable
private fun InviteNumberFieldContainer(
    modifier: Modifier = Modifier,
    text: Char,
    state: InviteCodeFieldState
) {

    val baseContainer: @Composable (Modifier, Color, Color) -> Unit =
        { containerModifier, backgroundColor, borderColor ->
            Box(
                modifier = containerModifier
                    .clip(RoundedCornerShape(size = 8.dp))
                    .background(color = backgroundColor)
                    .run {
                        border(
                            width = 1.dp,
                            color = borderColor,
                            shape = RoundedCornerShape(8.dp)
                        )

                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text.toString(),
                    color = Black,
                    style = Heading1,
                )
            }
        }


    when (state) {
        is InviteCodeFieldState.FILLED -> {
            Image(
                modifier = modifier,
                painter = painterResource(id = R.drawable.img_code),
                contentDescription = null
            )
        }

        is InviteCodeFieldState.EMPTY -> {
            baseContainer(
                modifier,
                state.backgroundColor,
                state.borderColor
            )
        }

        is InviteCodeFieldState.FOCUSED -> {
            baseContainer(
                modifier,
                state.backgroundColor,
                state.borderColor
            )
        }

        is InviteCodeFieldState.ERROR -> {
            baseContainer(
                modifier,
                state.backgroundColor,
                state.borderColor
            )
        }
    }
}