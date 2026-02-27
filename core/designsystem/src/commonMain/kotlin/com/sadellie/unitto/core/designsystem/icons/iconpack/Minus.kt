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

val IconPack.Minus: ImageVector
  get() {
    if (_Minus != null) {
      return _Minus!!
    }
    _Minus =
      ImageVector.Builder(
          name = "Minus",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(8.338f, 12f)
            curveTo(8.338f, 11.734f, 8.553f, 11.519f, 8.818f, 11.519f)
            horizontalLineTo(15.182f)
            curveTo(15.447f, 11.519f, 15.662f, 11.734f, 15.662f, 12f)
            curveTo(15.662f, 12.265f, 15.447f, 12.481f, 15.182f, 12.481f)
            horizontalLineTo(8.818f)
            curveTo(8.553f, 12.481f, 8.338f, 12.265f, 8.338f, 12f)
            close()
          }
        }
        .build()

    return _Minus!!
  }

@Suppress("ObjectPropertyName") private var _Minus: ImageVector? = null
