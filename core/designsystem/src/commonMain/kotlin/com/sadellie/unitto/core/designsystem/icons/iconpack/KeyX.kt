/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024 Elshan Agaev
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

val IconPack.KeyX: ImageVector
  get() {
    if (_KeyX != null) {
      return _KeyX!!
    }
    _KeyX =
      ImageVector.Builder(
          name = "KeyX",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color(0xFF000000))) {
            moveTo(15.133f, 15.669f)
            horizontalLineTo(14.173f)
            curveTo(14.1f, 15.669f, 14.041f, 15.65f, 13.998f, 15.614f)
            curveTo(13.958f, 15.574f, 13.925f, 15.53f, 13.898f, 15.484f)
            lineTo(11.978f, 12.474f)
            curveTo(11.951f, 12.554f, 11.921f, 12.622f, 11.888f, 12.679f)
            lineTo(10.018f, 15.484f)
            curveTo(9.985f, 15.53f, 9.948f, 15.574f, 9.908f, 15.614f)
            curveTo(9.871f, 15.65f, 9.82f, 15.669f, 9.753f, 15.669f)
            horizontalLineTo(8.853f)
            lineTo(11.318f, 11.989f)
            lineTo(8.953f, 8.504f)
            horizontalLineTo(9.918f)
            curveTo(9.988f, 8.504f, 10.04f, 8.515f, 10.073f, 8.539f)
            curveTo(10.106f, 8.562f, 10.136f, 8.595f, 10.163f, 8.639f)
            lineTo(12.033f, 11.509f)
            curveTo(12.056f, 11.439f, 12.091f, 11.362f, 12.138f, 11.279f)
            lineTo(13.903f, 8.659f)
            curveTo(13.93f, 8.612f, 13.961f, 8.575f, 13.998f, 8.549f)
            curveTo(14.035f, 8.519f, 14.078f, 8.504f, 14.128f, 8.504f)
            horizontalLineTo(15.053f)
            lineTo(12.678f, 11.944f)
            lineTo(15.133f, 15.669f)
            close()
          }
        }
        .build()

    return _KeyX!!
  }

@Suppress("ObjectPropertyName") private var _KeyX: ImageVector? = null
