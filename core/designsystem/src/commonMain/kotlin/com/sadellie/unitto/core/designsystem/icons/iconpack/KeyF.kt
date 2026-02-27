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

val IconPack.KeyF: ImageVector
  get() {
    if (_KeyF != null) {
      return _KeyF!!
    }
    _KeyF =
      ImageVector.Builder(
          name = "KeyF",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(10.159f, 15.724f)
            curveTo(10.022f, 15.724f, 9.904f, 15.674f, 9.804f, 15.574f)
            curveTo(9.707f, 15.474f, 9.659f, 15.354f, 9.659f, 15.214f)
            verticalLineTo(9.109f)
            curveTo(9.659f, 8.945f, 9.715f, 8.805f, 9.829f, 8.689f)
            curveTo(9.945f, 8.569f, 10.084f, 8.509f, 10.244f, 8.509f)
            horizontalLineTo(13.434f)
            curveTo(13.564f, 8.509f, 13.674f, 8.555f, 13.764f, 8.649f)
            curveTo(13.857f, 8.739f, 13.904f, 8.847f, 13.904f, 8.974f)
            curveTo(13.904f, 9.1f, 13.857f, 9.209f, 13.764f, 9.299f)
            curveTo(13.674f, 9.389f, 13.564f, 9.434f, 13.434f, 9.434f)
            horizontalLineTo(10.664f)
            verticalLineTo(15.214f)
            curveTo(10.664f, 15.354f, 10.614f, 15.474f, 10.514f, 15.574f)
            curveTo(10.417f, 15.674f, 10.299f, 15.724f, 10.159f, 15.724f)
            close()
            moveTo(10.219f, 12.504f)
            verticalLineTo(11.584f)
            horizontalLineTo(13.074f)
            curveTo(13.2f, 11.584f, 13.31f, 11.629f, 13.404f, 11.719f)
            curveTo(13.497f, 11.809f, 13.544f, 11.917f, 13.544f, 12.044f)
            curveTo(13.544f, 12.17f, 13.497f, 12.279f, 13.404f, 12.369f)
            curveTo(13.31f, 12.459f, 13.2f, 12.504f, 13.074f, 12.504f)
            horizontalLineTo(10.219f)
            close()
          }
        }
        .build()

    return _KeyF!!
  }

@Suppress("ObjectPropertyName") private var _KeyF: ImageVector? = null
