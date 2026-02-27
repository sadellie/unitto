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

val IconPack.Brackets: ImageVector
  get() {
    if (_Brackets != null) {
      return _Brackets!!
    }
    _Brackets =
      ImageVector.Builder(
          name = "Brackets",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(14.464f, 15.688f)
            curveTo(14.533f, 15.688f, 14.587f, 15.675f, 14.624f, 15.648f)
            curveTo(14.656f, 15.621f, 14.691f, 15.587f, 14.728f, 15.544f)
            curveTo(15.112f, 15.085f, 15.397f, 14.547f, 15.584f, 13.928f)
            curveTo(15.765f, 13.309f, 15.856f, 12.667f, 15.856f, 12f)
            curveTo(15.856f, 11.333f, 15.765f, 10.691f, 15.584f, 10.072f)
            curveTo(15.397f, 9.448f, 15.112f, 8.909f, 14.728f, 8.456f)
            curveTo(14.691f, 8.413f, 14.656f, 8.379f, 14.624f, 8.352f)
            curveTo(14.587f, 8.325f, 14.533f, 8.312f, 14.464f, 8.312f)
            curveTo(14.379f, 8.312f, 14.304f, 8.341f, 14.24f, 8.4f)
            curveTo(14.176f, 8.459f, 14.144f, 8.533f, 14.144f, 8.624f)
            curveTo(14.144f, 8.683f, 14.16f, 8.741f, 14.192f, 8.8f)
            curveTo(14.469f, 9.259f, 14.696f, 9.76f, 14.872f, 10.304f)
            curveTo(15.043f, 10.843f, 15.128f, 11.408f, 15.128f, 12f)
            curveTo(15.128f, 12.592f, 15.043f, 13.16f, 14.872f, 13.704f)
            curveTo(14.696f, 14.243f, 14.469f, 14.741f, 14.192f, 15.2f)
            curveTo(14.16f, 15.253f, 14.144f, 15.312f, 14.144f, 15.376f)
            curveTo(14.144f, 15.467f, 14.176f, 15.541f, 14.24f, 15.6f)
            curveTo(14.304f, 15.659f, 14.379f, 15.688f, 14.464f, 15.688f)
            close()
          }
          path(fill = SolidColor(Color.White)) {
            moveTo(9.536f, 15.688f)
            curveTo(9.467f, 15.688f, 9.413f, 15.675f, 9.376f, 15.648f)
            curveTo(9.344f, 15.621f, 9.309f, 15.587f, 9.272f, 15.544f)
            curveTo(8.888f, 15.085f, 8.603f, 14.547f, 8.416f, 13.928f)
            curveTo(8.235f, 13.309f, 8.144f, 12.667f, 8.144f, 12f)
            curveTo(8.144f, 11.333f, 8.235f, 10.691f, 8.416f, 10.072f)
            curveTo(8.603f, 9.448f, 8.888f, 8.909f, 9.272f, 8.456f)
            curveTo(9.309f, 8.413f, 9.344f, 8.379f, 9.376f, 8.352f)
            curveTo(9.413f, 8.325f, 9.467f, 8.312f, 9.536f, 8.312f)
            curveTo(9.621f, 8.312f, 9.696f, 8.341f, 9.76f, 8.4f)
            curveTo(9.824f, 8.459f, 9.856f, 8.533f, 9.856f, 8.624f)
            curveTo(9.856f, 8.683f, 9.84f, 8.741f, 9.808f, 8.8f)
            curveTo(9.531f, 9.259f, 9.304f, 9.76f, 9.128f, 10.304f)
            curveTo(8.957f, 10.843f, 8.872f, 11.408f, 8.872f, 12f)
            curveTo(8.872f, 12.592f, 8.957f, 13.16f, 9.128f, 13.704f)
            curveTo(9.304f, 14.243f, 9.531f, 14.741f, 9.808f, 15.2f)
            curveTo(9.84f, 15.253f, 9.856f, 15.312f, 9.856f, 15.376f)
            curveTo(9.856f, 15.467f, 9.824f, 15.541f, 9.76f, 15.6f)
            curveTo(9.696f, 15.659f, 9.621f, 15.688f, 9.536f, 15.688f)
            close()
          }
        }
        .build()

    return _Brackets!!
  }

@Suppress("ObjectPropertyName") private var _Brackets: ImageVector? = null
