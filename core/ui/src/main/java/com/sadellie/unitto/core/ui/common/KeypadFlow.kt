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

import androidx.annotation.IntRange
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

/**
 * Keypad layout for buttons. Uses [FlowRow].
 *
 * @param modifier [Modifier] that will be applied to [FlowRow].
 * @param rows Amount of rows. Vertical size.
 * @param columns Amount of columns. Horizontal size.
 * @param verticalPadding Percentage. How much space of button height is gonna be a padding.
 * @param horizontalPadding Percentage. How much space of button width is gonna be a padding.
 * @param content Content, usually some buttons. Use `width` and `height` in [fillMaxWidth]
 * and [fillMaxHeight] to build a [Modifier]. Passed [Composable]s are equally distributed, occupy
 * entire height and width.
 *
 * _Note_: Do not use provided `width` if [horizontalPadding] is 0. Use `weight` modifier.
 */
@Composable
fun KeypadFlow(
    modifier: Modifier,
    rows: Int,
    columns: Int,
    @IntRange(0, 100) horizontalPadding: Int = 10,
    @IntRange(0, 100) verticalPadding: Int = 10,
    content: @Composable FlowRowScope.(width: Float, height: Float) -> Unit
) {
    val height: Float = remember { (1f - verticalPadding / 100f) / rows }
    val width: Float = remember { (1f - horizontalPadding / 100f) / columns }

    FlowRow(
        modifier = modifier,
        maxItemsInEachRow = columns,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        content(width, height)
    }
}
