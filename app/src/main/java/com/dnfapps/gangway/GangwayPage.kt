package com.dnfapps.gangway

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable

abstract class GangwayPage(
    val content: @Composable BoxScope.(setNextButtonEnabled: (Boolean) -> Unit) -> Unit,
    val nextButtonInitiallyEnabled: Boolean = true
)