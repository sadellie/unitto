/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

import androidx.annotation.IntRange
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PagedIsland(
    modifier: Modifier = Modifier,
    @IntRange(from = 1) pagesCount: Int,
    onPageChange: (currentPage: Int) -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    pageContent: @Composable ColumnScope.(currentPage: Int) -> Unit,
) {
    var currentPage: Int by remember { mutableIntStateOf(0) }
    val contentColor = MaterialTheme.colorScheme.contentColorFor(backgroundColor)
    val disabledContentColor = contentColor.copy(alpha = 0.5f)

    AnimatedContent(
        modifier = modifier
            .squashable(
                onClick = {
                    if (currentPage == pagesCount - 1) currentPage = 0 else currentPage++
                    onPageChange(currentPage)
                },
                cornerRadiusRange = 8.dp..32.dp,
                interactionSource = remember { MutableInteractionSource() }
            )
            .background(backgroundColor),
        targetState = currentPage
    ) { state ->
        ProvideColor(color = contentColor) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(pagesCount) {
                        ADot(color = if (it == state) contentColor else disabledContentColor)
                    }
                }
                pageContent(state)
            }
        }
    }
}

@Composable
private fun ADot(
    color: Color,
) {
    Canvas(modifier = Modifier.size(4.dp)) {
        drawCircle(color)
    }
}

@Preview
@Composable
private fun PreviewPagedIsland() {
    PagedIsland(pagesCount = 5) { currentPage ->
        Text("Current page: $currentPage")

        if (currentPage == 3) {
            Text("Middle in: $currentPage")
        }
    }
}
