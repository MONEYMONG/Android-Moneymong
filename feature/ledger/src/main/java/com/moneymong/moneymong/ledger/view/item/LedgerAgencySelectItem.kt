package com.moneymong.moneymong.ledger.view.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.R.*
import com.moneymong.moneymong.design_system.theme.Blue01
import com.moneymong.moneymong.design_system.theme.Blue03
import com.moneymong.moneymong.design_system.theme.Blue04
import com.moneymong.moneymong.design_system.theme.Body2
import com.moneymong.moneymong.design_system.theme.Body3
import com.moneymong.moneymong.design_system.theme.Gray02
import com.moneymong.moneymong.design_system.theme.Gray03
import com.moneymong.moneymong.design_system.theme.Gray05
import com.moneymong.moneymong.design_system.theme.Gray08
import com.moneymong.moneymong.design_system.theme.Gray09
import com.moneymong.moneymong.design_system.theme.MMHorizontalSpacing
import com.moneymong.moneymong.design_system.theme.SkyBlue01
import com.moneymong.moneymong.design_system.theme.White
import com.moneymong.moneymong.model.agency.MyAgencyResponse
import java.text.DecimalFormat

@Composable
fun LedgerAgencySelectItem(
    modifier: Modifier = Modifier,
    agencyResponse: MyAgencyResponse,
    currentAgency: Boolean,
    onClick: (agencyId: Int) -> Unit
) {
    val backgroundColor = if (currentAgency) Blue01 else White
    val borderColor = if (currentAgency) Blue04 else Gray02
    val textColor = if (currentAgency) Blue04 else Gray08
    val leftIconColor = if (currentAgency) Blue03 else SkyBlue01
    val rightIconColor = if (currentAgency) Blue04 else Gray03

    val memberFormat = DecimalFormat("00")

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick(agencyResponse.id) }
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(16.dp)) // TODO
            .background(color = backgroundColor)
            .padding(vertical = 12.dp, horizontal = MMHorizontalSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(100))
                .background(leftIconColor),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = drawable.ic_study),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = agencyResponse.name,
                style = Body3,
                color = textColor,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "멤버수 ${memberFormat.format(agencyResponse.headCount)}",
                style = Body2,
                color = Gray05
            )
        }
        Spacer(modifier = Modifier.width(9.dp))
        Icon(
            painter = painterResource(id = drawable.ic_check),
            contentDescription = null,
            tint = rightIconColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LedgerStaffSelectItemPreview() {
    LedgerAgencySelectItem(
        currentAgency = false,
        agencyResponse = MyAgencyResponse(
            0,
            "dasfakjdsfhasdlkjfhsadlkfsahdlfkashflkjadsfkjfsdsfgs",
            0,
            ""
        )
    ) {}
}