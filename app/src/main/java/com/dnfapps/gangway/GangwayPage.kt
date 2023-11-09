package com.dnfapps.gangway

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

abstract class GangwayPage(
    val content: @Composable BoxScope.(setNextButtonEnabled: (Boolean) -> Unit) -> Unit,
    val nextButtonInitiallyEnabled: Boolean = true
)

@Composable
fun BoxScope.GangwayPage(
    @DrawableRes icon: Int,
    iconContentDescription: String? = null,
    title: String? = null,
    description: String? = null
) {
    Column(
        modifier = Modifier.align(Alignment.Center),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = iconContentDescription,
            modifier = Modifier.size(62.dp)
        )
        title?.let {
            Text(
                text = title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
        description?.let {
            Text(
                text = description,
                textAlign = TextAlign.Center
            )
        }
    }
}