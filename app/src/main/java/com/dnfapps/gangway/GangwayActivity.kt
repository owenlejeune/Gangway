package com.dnfapps.gangway

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable

abstract class GangwayActivity: ComponentActivity() {

    protected abstract val pages: List<GangwayPage>

    protected val showSkipButton: Boolean = true
    protected val showPageIndicators: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProvideTheme {
                GangwayContainer(
                    pages = pages,
                    onFinish = {},
                    onSkipRequest = {},
                    showSkipButton = showSkipButton,
                    showPageIndicators = showPageIndicators,
                    colors = provideColors()
                )
            }
        }
    }

    @Composable
    abstract fun ProvideTheme(context: @Composable () -> Unit)

    @Composable
    protected open fun provideColors(): GangwayColors = GangwayDefaults.colors()

}