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

package com.sadellie.unitto.feature.calculator.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.offset

/**
 * Screen layout where [historyList] can be seen only when you drag [textFields] down.
 *
 * @param modifier [Modifier] that will be applied to [SubcomposeLayout].
 * @param drag Drag amount. Update this when dragging [textFields].
 * @param historyItemHeight Height of one item in [historyList].
 * @param textFields This part of the UI should be used as a handle. Offsets when dragging.
 * @param numPad Composable with buttons. Offsets when drag amount is higher than [historyItemHeight]
 *  (otherwise will just shrink).
 */
@Composable
internal fun DragDownView(
    modifier: Modifier,
    drag: Int,
    historyItemHeight: Int,
    historyList: @Composable () -> Unit,
    textFields: @Composable (maxDragAmount: Int) -> Unit,
    numPad: @Composable () -> Unit
) {
    SubcomposeLayout(modifier = modifier) { constraints ->
        val showHistory = drag < historyItemHeight
        val offset = if (showHistory) (drag - historyItemHeight).coerceAtLeast(0) else 0

        val textFieldPlaceables = subcompose(DragDownContent.TextFields) {
            textFields(constraints.maxHeight)
        }.map { it.measure(constraints.offset(offset)) }
        val textFieldsHeight = textFieldPlaceables.maxByOrNull { it.height }?.height ?: 0

        val historyListPlaceables = subcompose(DragDownContent.HistoryList) {
            historyList()
        }.map {
            it.measure(
                constraints.copy(
                    maxHeight = drag.coerceAtMost(constraints.maxHeight - textFieldsHeight)
                )
            )
        }

        val padding = if (showHistory) drag.coerceAtLeast(0) else historyItemHeight
        val numPadConstraints = constraints
            .offset(offset)
            .copy(maxHeight = constraints.maxHeight - textFieldsHeight - padding)
        val numPadPlaceables = subcompose(DragDownContent.NumPad, numPad).map {
            it.measure(numPadConstraints)
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            var yPos = 0
            historyListPlaceables.forEach {
                it.place(0, yPos)
                yPos += it.height
            }

            textFieldPlaceables.forEach {
                it.place(0, yPos)
                yPos += it.height
            }

            numPadPlaceables.forEach {
                it.place(0, yPos)
                yPos += it.height
            }
        }
    }
}

private enum class DragDownContent { HistoryList, TextFields, NumPad }
