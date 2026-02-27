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

val IconPack.RightBracket: ImageVector
  get() {
    if (_RightBracket != null) {
      return _RightBracket!!
    }
    _RightBracket =
      ImageVector.Builder(
          name = "RightBracket",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(11.464f, 15.688f)
            curveTo(11.533f, 15.688f, 11.587f, 15.675f, 11.624f, 15.648f)
            curveTo(11.656f, 15.621f, 11.691f, 15.587f, 11.728f, 15.544f)
            curveTo(12.112f, 15.085f, 12.397f, 14.547f, 12.584f, 13.928f)
            curveTo(12.765f, 13.309f, 12.856f, 12.667f, 12.856f, 12f)
            curveTo(12.856f, 11.333f, 12.765f, 10.691f, 12.584f, 10.072f)
            curveTo(12.397f, 9.448f, 12.112f, 8.909f, 11.728f, 8.456f)
            curveTo(11.691f, 8.413f, 11.656f, 8.379f, 11.624f, 8.352f)
            curveTo(11.587f, 8.325f, 11.533f, 8.312f, 11.464f, 8.312f)
            curveTo(11.379f, 8.312f, 11.304f, 8.341f, 11.24f, 8.4f)
            curveTo(11.176f, 8.459f, 11.144f, 8.533f, 11.144f, 8.624f)
            curveTo(11.144f, 8.683f, 11.16f, 8.741f, 11.192f, 8.8f)
            curveTo(11.469f, 9.259f, 11.696f, 9.76f, 11.872f, 10.304f)
            curveTo(12.043f, 10.843f, 12.128f, 11.408f, 12.128f, 12f)
            curveTo(12.128f, 12.592f, 12.043f, 13.16f, 11.872f, 13.704f)
            curveTo(11.696f, 14.243f, 11.469f, 14.741f, 11.192f, 15.2f)
            curveTo(11.16f, 15.253f, 11.144f, 15.312f, 11.144f, 15.376f)
            curveTo(11.144f, 15.467f, 11.176f, 15.541f, 11.24f, 15.6f)
            curveTo(11.304f, 15.659f, 11.379f, 15.688f, 11.464f, 15.688f)
            close()
          }
        }
        .build()

    return _RightBracket!!
  }

@Suppress("ObjectPropertyName") private var _RightBracket: ImageVector? = null
