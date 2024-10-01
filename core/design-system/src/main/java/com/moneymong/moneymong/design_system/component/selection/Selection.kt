package com.moneymong.moneymong.design_system.component.selection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.theme.Body3

@Composable
fun MDSSelection(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    isSelected: Boolean = false,
    type: MDSSelectionType = MDSSelectionType.PRIMARY,
    onClick: () -> Unit = {}
) {

    val backgroundColor = selectionBackgroundColor(enabled, isSelected, type)
    val contentColor = selectionContentColor(enabled, isSelected, type)
    val borderColor = selectionBorderColor(enabled, isSelected, type)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color = backgroundColor)
            .clickable(enabled = enabled) { onClick() }
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = text,
            color = contentColor,
            style = Body3,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MDSSelectionPreview() {
    var selectedType by remember { mutableIntStateOf(1) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MDSSelection(
                modifier = Modifier.weight(1f),
                text = "동아리",
                isSelected = selectedType == 1,
                onClick = { selectedType = 1 }
            )
            MDSSelection(
                modifier = Modifier.weight(1f),
                text = "나는 Secondary",
                isSelected = selectedType == 2,
                type = MDSSelectionType.SECONDARY,
                onClick = { selectedType = 2 }
            )
            MDSSelection(
                modifier = Modifier.weight(1f),
                text = "나는 disabled",
                enabled = false,
            )
        }
    }
}