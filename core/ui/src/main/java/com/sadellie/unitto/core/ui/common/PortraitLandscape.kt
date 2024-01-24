/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2024 Elshan Agaev
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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.ui.LocalWindowSize
import com.sadellie.unitto.core.ui.WindowHeightSizeClass
import com.sadellie.unitto.core.ui.WindowWidthSizeClass

/**
 * When Portrait mode will place [content1] and [content2] in a [Column].
 *
 * When Landscape mode will place [content1] and [content2] in a [Row].
 */
@Composable
fun PortraitLandscape(
    modifier: Modifier,
    content1: @Composable (Modifier) -> Unit,
    content2: @Composable (Modifier) -> Unit,
) {
    if ((LocalWindowSize.current.widthSizeClass > WindowWidthSizeClass.Expanded) or (LocalWindowSize.current.heightSizeClass > WindowHeightSizeClass.Compact)) {
        ColumnWithConstraints(modifier) {
            content1(Modifier.fillMaxHeight(0.38f).padding(horizontal = it.maxWidth * 0.03f))
            content2(Modifier.fillMaxSize().padding(it.maxWidth * 0.03f, it.maxHeight * 0.015f))
        }
    } else {
        RowWithConstraints(modifier) {
            val contentModifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(
                    it.maxWidth * 0.015f, 0.dp,
                    it.maxHeight * 0.03f, it.maxHeight * 0.03f)
            content1(contentModifier)
            content2(contentModifier)
        }
    }
}
