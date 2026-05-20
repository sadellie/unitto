/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024-2026 Elshan Agaev
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
package com.sadellie.unitto.core.designsystem.icons.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IconPack.Factorial: ImageVector
  get() {
    if (_Factorial != null) {
      return _Factorial!!
    }
    _Factorial =
      ImageVector.Builder(
          name = "Factorial",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(11.996f, 13.579f)
            curveTo(11.88f, 13.579f, 11.778f, 13.537f, 11.691f, 13.454f)
            curveTo(11.608f, 13.37f, 11.565f, 13.269f, 11.561f, 13.149f)
            lineTo(11.476f, 8.989f)
            curveTo(11.476f, 8.842f, 11.526f, 8.717f, 11.626f, 8.614f)
            curveTo(11.73f, 8.507f, 11.855f, 8.454f, 12.001f, 8.454f)
            curveTo(12.145f, 8.454f, 12.268f, 8.507f, 12.371f, 8.614f)
            curveTo(12.475f, 8.717f, 12.525f, 8.842f, 12.521f, 8.989f)
            lineTo(12.436f, 13.149f)
            curveTo(12.433f, 13.269f, 12.388f, 13.37f, 12.301f, 13.454f)
            curveTo(12.215f, 13.537f, 12.113f, 13.579f, 11.996f, 13.579f)
            close()
            moveTo(11.996f, 15.739f)
            curveTo(11.8f, 15.739f, 11.633f, 15.67f, 11.496f, 15.534f)
            curveTo(11.36f, 15.397f, 11.291f, 15.227f, 11.291f, 15.024f)
            curveTo(11.291f, 14.83f, 11.36f, 14.667f, 11.496f, 14.534f)
            curveTo(11.633f, 14.397f, 11.8f, 14.329f, 11.996f, 14.329f)
            curveTo(12.193f, 14.329f, 12.36f, 14.397f, 12.496f, 14.534f)
            curveTo(12.636f, 14.667f, 12.706f, 14.83f, 12.706f, 15.024f)
            curveTo(12.706f, 15.227f, 12.636f, 15.397f, 12.496f, 15.534f)
            curveTo(12.36f, 15.67f, 12.193f, 15.739f, 11.996f, 15.739f)
            close()
          }
        }
        .build()

    return _Factorial!!
  }

@Suppress("ObjectPropertyName") private var _Factorial: ImageVector? = null
