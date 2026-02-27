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

val IconPack.KeyA: ImageVector
  get() {
    if (_KeyA != null) {
      return _KeyA!!
    }
    _KeyA =
      ImageVector.Builder(
          name = "KeyA",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(9.347f, 15.724f)
            curveTo(9.167f, 15.724f, 9.03f, 15.655f, 8.937f, 15.519f)
            curveTo(8.844f, 15.379f, 8.832f, 15.219f, 8.902f, 15.039f)
            lineTo(11.357f, 8.889f)
            curveTo(11.404f, 8.765f, 11.487f, 8.66f, 11.607f, 8.574f)
            curveTo(11.73f, 8.487f, 11.864f, 8.444f, 12.007f, 8.444f)
            curveTo(12.147f, 8.444f, 12.277f, 8.487f, 12.397f, 8.574f)
            curveTo(12.52f, 8.66f, 12.605f, 8.765f, 12.652f, 8.889f)
            lineTo(15.102f, 15.034f)
            curveTo(15.172f, 15.214f, 15.16f, 15.374f, 15.067f, 15.514f)
            curveTo(14.974f, 15.654f, 14.835f, 15.724f, 14.652f, 15.724f)
            curveTo(14.552f, 15.724f, 14.457f, 15.692f, 14.367f, 15.629f)
            curveTo(14.277f, 15.565f, 14.215f, 15.489f, 14.182f, 15.399f)
            lineTo(12.332f, 10.614f)
            lineTo(12.012f, 9.679f)
            horizontalLineTo(11.982f)
            lineTo(11.672f, 10.614f)
            lineTo(9.817f, 15.404f)
            curveTo(9.784f, 15.49f, 9.722f, 15.565f, 9.632f, 15.629f)
            curveTo(9.542f, 15.692f, 9.447f, 15.724f, 9.347f, 15.724f)
            close()
            moveTo(9.862f, 13.924f)
            lineTo(10.162f, 12.999f)
            horizontalLineTo(13.767f)
            lineTo(14.077f, 13.924f)
            horizontalLineTo(9.862f)
            close()
          }
        }
        .build()

    return _KeyA!!
  }

@Suppress("ObjectPropertyName") private var _KeyA: ImageVector? = null
