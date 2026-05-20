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

val IconPack.Plus: ImageVector
  get() {
    if (_Plus != null) {
      return _Plus!!
    }
    _Plus =
      ImageVector.Builder(
          name = "Plus",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(8.338f, 12.023f)
            curveTo(8.338f, 11.757f, 8.553f, 11.542f, 8.818f, 11.542f)
            horizontalLineTo(15.182f)
            curveTo(15.447f, 11.542f, 15.663f, 11.757f, 15.663f, 12.023f)
            curveTo(15.663f, 12.288f, 15.447f, 12.503f, 15.182f, 12.503f)
            horizontalLineTo(8.818f)
            curveTo(8.553f, 12.503f, 8.338f, 12.288f, 8.338f, 12.023f)
            close()
          }
          path(fill = SolidColor(Color.White)) {
            moveTo(12.069f, 8.337f)
            curveTo(12.334f, 8.337f, 12.55f, 8.553f, 12.55f, 8.818f)
            lineTo(12.55f, 15.182f)
            curveTo(12.55f, 15.447f, 12.334f, 15.662f, 12.069f, 15.662f)
            curveTo(11.803f, 15.662f, 11.588f, 15.447f, 11.588f, 15.182f)
            lineTo(11.588f, 8.818f)
            curveTo(11.588f, 8.553f, 11.803f, 8.337f, 12.069f, 8.337f)
            close()
          }
        }
        .build()

    return _Plus!!
  }

@Suppress("ObjectPropertyName") private var _Plus: ImageVector? = null
