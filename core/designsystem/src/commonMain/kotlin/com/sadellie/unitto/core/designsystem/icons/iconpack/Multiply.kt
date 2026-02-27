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

val IconPack.Multiply: ImageVector
  get() {
    if (_Multiply != null) {
      return _Multiply!!
    }
    _Multiply =
      ImageVector.Builder(
          name = "Multiply",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(8.687f, 15.313f)
            curveTo(8.494f, 15.12f, 8.494f, 14.807f, 8.687f, 14.614f)
            lineTo(14.614f, 8.687f)
            curveTo(14.807f, 8.494f, 15.12f, 8.494f, 15.313f, 8.687f)
            curveTo(15.506f, 8.88f, 15.506f, 9.193f, 15.313f, 9.386f)
            lineTo(9.386f, 15.313f)
            curveTo(9.193f, 15.506f, 8.88f, 15.506f, 8.687f, 15.313f)
            close()
          }
          path(fill = SolidColor(Color.White)) {
            moveTo(8.687f, 8.687f)
            curveTo(8.88f, 8.494f, 9.193f, 8.494f, 9.386f, 8.687f)
            lineTo(15.313f, 14.614f)
            curveTo(15.506f, 14.807f, 15.506f, 15.12f, 15.313f, 15.313f)
            curveTo(15.12f, 15.506f, 14.807f, 15.506f, 14.614f, 15.313f)
            lineTo(8.687f, 9.386f)
            curveTo(8.494f, 9.193f, 8.494f, 8.88f, 8.687f, 8.687f)
            close()
          }
        }
        .build()

    return _Multiply!!
  }

@Suppress("ObjectPropertyName") private var _Multiply: ImageVector? = null
