/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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

package com.sadellie.unitto.core.designsystem

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

val LocalWindowSize: ProvidableCompositionLocal<WindowSizeClass> = compositionLocalOf {
  // Phone in portrait mode: WindowWidthSizeClass.Compact and WindowHeightSizeClass.Medium
  WindowSizeClass.calculateFromSize(DpSize(599.dp, 480.dp))
}

val WindowWidthSizeClass.Companion.expanded: Dp
  get() = 840.dp

val WindowWidthSizeClass.Companion.medium: Dp
  get() = 600.dp

val WindowWidthSizeClass.Companion.compact: Dp
  get() = 400.dp

val WindowHeightSizeClass.Companion.expanded: Dp
  get() = 900.dp

val WindowHeightSizeClass.Companion.medium: Dp
  get() = 480.dp

val WindowHeightSizeClass.Companion.compact: Dp
  get() = 400.dp
