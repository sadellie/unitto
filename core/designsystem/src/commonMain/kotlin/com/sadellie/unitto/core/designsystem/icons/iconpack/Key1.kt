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

val IconPack.Key1: ImageVector
  get() {
    if (_Key1 != null) {
      return _Key1!!
    }
    _Key1 =
      ImageVector.Builder(
          name = "Key1",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(12.589f, 15.724f)
            curveTo(12.453f, 15.724f, 12.334f, 15.674f, 12.234f, 15.574f)
            curveTo(12.138f, 15.474f, 12.089f, 15.354f, 12.089f, 15.214f)
            verticalLineTo(9.899f)
            lineTo(10.934f, 10.754f)
            curveTo(10.828f, 10.83f, 10.709f, 10.859f, 10.579f, 10.839f)
            curveTo(10.453f, 10.815f, 10.351f, 10.752f, 10.274f, 10.649f)
            curveTo(10.204f, 10.549f, 10.179f, 10.435f, 10.199f, 10.309f)
            curveTo(10.223f, 10.182f, 10.286f, 10.082f, 10.389f, 10.009f)
            lineTo(12.349f, 8.569f)
            curveTo(12.383f, 8.545f, 12.419f, 8.524f, 12.459f, 8.504f)
            curveTo(12.503f, 8.48f, 12.558f, 8.469f, 12.624f, 8.469f)
            curveTo(12.758f, 8.469f, 12.869f, 8.515f, 12.959f, 8.609f)
            curveTo(13.049f, 8.699f, 13.094f, 8.81f, 13.094f, 8.944f)
            verticalLineTo(15.214f)
            curveTo(13.094f, 15.354f, 13.044f, 15.474f, 12.944f, 15.574f)
            curveTo(12.848f, 15.674f, 12.729f, 15.724f, 12.589f, 15.724f)
            close()
          }
        }
        .build()

    return _Key1!!
  }

@Suppress("ObjectPropertyName") private var _Key1: ImageVector? = null
