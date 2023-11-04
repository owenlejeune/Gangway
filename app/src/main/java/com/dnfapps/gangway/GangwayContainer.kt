package com.dnfapps.gangway

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GangwayContainer(
    pages: List<GangwayPage>,
    onFinish: () -> Unit,
    onSkipRequest: () -> Unit,
    showSkipButton: Boolean = GangwayDefaults.showSkipButton,
    showPageIndicators: Boolean = GangwayDefaults.showPageIndicators,
    colors: GangwayColors = GangwayDefaults.colors()
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.background(color = colors.containerColor)
    ) {
        val isFirstPage = pagerState.currentPage == 0
        val isLastPage = pagerState.currentPage == pages.size - 1

        if (!isFirstPage) {
            IconButton(
                modifier = Modifier.padding(12.dp),
                onClick = {
                    coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = colors.backButtonColor
                )
            }
        }

        val nextButtonEnabled = remember { mutableStateOf(pages[pagerState.currentPage].nextButtonInitiallyEnabled) }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            userScrollEnabled = false,
            count = pages.size
        ) { pageIndex ->
            Box(modifier = Modifier.padding(start = 12.dp, end = 24.dp)) {
                pages[pageIndex].content(this) { nextButtonEnabled.value = it }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 12.dp)
        ) {
            if (showSkipButton) {
                Button(
                    onClick = onSkipRequest,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.skipButtonColor,
                        contentColor = colors.skipTextColor
                    )
                ) {
                    Text(text = stringResource(R.string.skip))
                }
            }

            if (showPageIndicators) {
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier.padding(16.dp),
                    activeColor = colors.pageIndicatorColor
                )
            }

            Button(
                shape = CircleShape,
                enabled = nextButtonEnabled.value,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.nextButtonColor,
                    contentColor = colors.nextContentColor
                ),
                onClick = {
                    if (isLastPage) {
                      onFinish()
                    } else {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                }
            ) {
                if (!isLastPage) {
                    Text(text = stringResource(R.string.start))
                } else {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = stringResource(R.string.next)
                    )
                }
            }
        }
    }
}

object GangwayDefaults {
    var showSkipButton: Boolean = true

    var showPageIndicators: Boolean = true

    @Composable
    fun colors(
        containerColor: Color = MaterialTheme.colorScheme.background,
        backButtonColor: Color = MaterialTheme.colorScheme.onBackground,
        pageIndicatorColor: Color = MaterialTheme.colorScheme.primary,
        skipButtonColor: Color = MaterialTheme.colorScheme.primary,
        skipTextColor: Color = MaterialTheme.colorScheme.onPrimary,
        nextButtonColor: Color = MaterialTheme.colorScheme.primary,
        nextContentColor: Color = MaterialTheme.colorScheme.onPrimary
    ): GangwayColors = GangwayColors(
        containerColor = containerColor,
        backButtonColor = backButtonColor,
        pageIndicatorColor = pageIndicatorColor,
        skipButtonColor = skipButtonColor,
        skipTextColor = skipTextColor,
        nextButtonColor = nextButtonColor,
        nextContentColor = nextContentColor
    )
}

class GangwayColors(
    val containerColor: Color,
    val backButtonColor: Color,
    val pageIndicatorColor: Color,
    val skipButtonColor: Color,
    val skipTextColor: Color,
    val nextButtonColor: Color,
    val nextContentColor: Color
)