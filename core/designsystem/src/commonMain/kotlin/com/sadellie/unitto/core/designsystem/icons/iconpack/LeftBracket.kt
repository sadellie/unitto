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

val IconPack.LeftBracket: ImageVector
  get() {
    if (_LeftBracket != null) {
      return _LeftBracket!!
    }
    _LeftBracket =
      ImageVector.Builder(
          name = "LeftBracket",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(12.536f, 15.688f)
            curveTo(12.467f, 15.688f, 12.413f, 15.675f, 12.376f, 15.648f)
            curveTo(12.344f, 15.621f, 12.309f, 15.587f, 12.272f, 15.544f)
            curveTo(11.888f, 15.085f, 11.603f, 14.547f, 11.416f, 13.928f)
            curveTo(11.235f, 13.309f, 11.144f, 12.667f, 11.144f, 12f)
            curveTo(11.144f, 11.333f, 11.235f, 10.691f, 11.416f, 10.072f)
            curveTo(11.603f, 9.448f, 11.888f, 8.909f, 12.272f, 8.456f)
            curveTo(12.309f, 8.413f, 12.344f, 8.379f, 12.376f, 8.352f)
            curveTo(12.413f, 8.325f, 12.467f, 8.312f, 12.536f, 8.312f)
            curveTo(12.621f, 8.312f, 12.696f, 8.341f, 12.76f, 8.4f)
            curveTo(12.824f, 8.459f, 12.856f, 8.533f, 12.856f, 8.624f)
            curveTo(12.856f, 8.683f, 12.84f, 8.741f, 12.808f, 8.8f)
            curveTo(12.531f, 9.259f, 12.304f, 9.76f, 12.128f, 10.304f)
            curveTo(11.957f, 10.843f, 11.872f, 11.408f, 11.872f, 12f)
            curveTo(11.872f, 12.592f, 11.957f, 13.16f, 12.128f, 13.704f)
            curveTo(12.304f, 14.243f, 12.531f, 14.741f, 12.808f, 15.2f)
            curveTo(12.84f, 15.253f, 12.856f, 15.312f, 12.856f, 15.376f)
            curveTo(12.856f, 15.467f, 12.824f, 15.541f, 12.76f, 15.6f)
            curveTo(12.696f, 15.659f, 12.621f, 15.688f, 12.536f, 15.688f)
            close()
          }
        }
        .build()

    return _LeftBracket!!
  }

@Suppress("ObjectPropertyName") private var _LeftBracket: ImageVector? = null
