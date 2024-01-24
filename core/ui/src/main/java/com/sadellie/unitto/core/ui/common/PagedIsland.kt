/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.sadellie.unitto.core.ui.common

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * [HorizontalPager] with a background and a page indicator.
 *
 * @param modifier [Modifier] that will be applied to the surround [Column].
 * @param pagerState [PagerState] that will passed [HorizontalPager].
 * @param backgroundColor [Color] for background.
 * @param pageIndicatorAlignment [Alignment.Horizontal] for page indicator.
 * @param onClick Called on all clicks, even if the page didn't change.
 * @param pageContent Page content. Use passed `currentPage` value.
 */
@Composable
fun PagedIsland(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    backgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    pageIndicatorAlignment: Alignment.Horizontal = Alignment.End,
    onClick: () -> Unit = {},
    pageContent: @Composable (currentPage: Int) -> Unit,
) {
    val contentColor = MaterialTheme.colorScheme.contentColorFor(backgroundColor)
    val disabledContentColor = contentColor.copy(alpha = 0.5f)
    val corScope = rememberCoroutineScope()

    ProvideColor(color = contentColor) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(32.dp))
                .clickable {
                    onClick()
                    if (pagerState.currentPage == (pagerState.pageCount - 1)) return@clickable

                    corScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
                .background(backgroundColor)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, pageIndicatorAlignment),
            ) {
                repeat(pagerState.pageCount) {
                    PageDot(if (it == pagerState.currentPage) contentColor else disabledContentColor)
                }
            }

            HorizontalPager(
                modifier = Modifier
                    .animateContentSize()
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                state = pagerState
            ) { page ->
                pageContent(page)
            }
        }
    }
}

@Composable
private fun PageDot(
    color: Color,
) {
    Canvas(modifier = Modifier.size(4.dp)) {
        drawCircle(color)
    }
}

@Preview
@Composable
private fun PreviewPagedIsland() {
    PagedIsland(
        modifier = Modifier.size(400.dp, 250.dp),
        pagerState = rememberPagerState { 5 }
    ) { currentPage ->
        Column {
            Text("Current page: $currentPage")

            if (currentPage == 3) {
                Text("Middle in: $currentPage")
            }
        }
    }
}
