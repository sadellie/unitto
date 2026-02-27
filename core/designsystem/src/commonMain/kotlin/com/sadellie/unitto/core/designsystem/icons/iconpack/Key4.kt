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

val IconPack.Key4: ImageVector
  get() {
    if (_Key4 != null) {
      return _Key4!!
    }
    _Key4 =
      ImageVector.Builder(
          name = "Key4",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(13.202f, 15.719f)
            curveTo(13.069f, 15.719f, 12.954f, 15.672f, 12.857f, 15.579f)
            curveTo(12.76f, 15.482f, 12.712f, 15.365f, 12.712f, 15.229f)
            verticalLineTo(13.584f)
            lineTo(12.752f, 13.414f)
            verticalLineTo(9.389f)
            lineTo(13.142f, 9.809f)
            horizontalLineTo(12.722f)
            lineTo(10.267f, 13.104f)
            horizontalLineTo(13.097f)
            lineTo(13.327f, 13.074f)
            horizontalLineTo(14.337f)
            curveTo(14.46f, 13.074f, 14.567f, 13.119f, 14.657f, 13.209f)
            curveTo(14.747f, 13.295f, 14.792f, 13.4f, 14.792f, 13.524f)
            curveTo(14.792f, 13.647f, 14.747f, 13.754f, 14.657f, 13.844f)
            curveTo(14.567f, 13.93f, 14.46f, 13.974f, 14.337f, 13.974f)
            horizontalLineTo(9.837f)
            curveTo(9.657f, 13.974f, 9.505f, 13.915f, 9.382f, 13.799f)
            curveTo(9.262f, 13.682f, 9.202f, 13.537f, 9.202f, 13.364f)
            curveTo(9.202f, 13.277f, 9.215f, 13.207f, 9.242f, 13.154f)
            curveTo(9.269f, 13.097f, 9.3f, 13.042f, 9.337f, 12.989f)
            lineTo(12.502f, 8.724f)
            curveTo(12.559f, 8.647f, 12.634f, 8.582f, 12.727f, 8.529f)
            curveTo(12.82f, 8.472f, 12.924f, 8.444f, 13.037f, 8.444f)
            curveTo(13.22f, 8.444f, 13.374f, 8.51f, 13.497f, 8.644f)
            curveTo(13.624f, 8.774f, 13.687f, 8.932f, 13.687f, 9.119f)
            verticalLineTo(15.229f)
            curveTo(13.687f, 15.365f, 13.639f, 15.482f, 13.542f, 15.579f)
            curveTo(13.449f, 15.672f, 13.335f, 15.719f, 13.202f, 15.719f)
            close()
          }
        }
        .build()

    return _Key4!!
  }

@Suppress("ObjectPropertyName") private var _Key4: ImageVector? = null
