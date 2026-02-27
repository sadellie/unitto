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

val IconPack.Power: ImageVector
  get() {
    if (_Power != null) {
      return _Power!!
    }
    _Power =
      ImageVector.Builder(
          name = "Power",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(10.399f, 11.614f)
            curveTo(10.233f, 11.614f, 10.113f, 11.547f, 10.039f, 11.414f)
            curveTo(9.966f, 11.277f, 9.979f, 11.132f, 10.079f, 10.979f)
            lineTo(11.529f, 8.734f)
            curveTo(11.576f, 8.657f, 11.641f, 8.594f, 11.724f, 8.544f)
            curveTo(11.808f, 8.49f, 11.899f, 8.464f, 11.999f, 8.464f)
            curveTo(12.099f, 8.464f, 12.191f, 8.49f, 12.274f, 8.544f)
            curveTo(12.358f, 8.594f, 12.423f, 8.657f, 12.469f, 8.734f)
            lineTo(13.919f, 10.974f)
            curveTo(14.019f, 11.127f, 14.033f, 11.272f, 13.959f, 11.409f)
            curveTo(13.886f, 11.545f, 13.766f, 11.614f, 13.599f, 11.614f)
            curveTo(13.526f, 11.614f, 13.456f, 11.595f, 13.389f, 11.559f)
            curveTo(13.323f, 11.522f, 13.271f, 11.472f, 13.234f, 11.409f)
            lineTo(12.009f, 9.439f)
            horizontalLineTo(11.979f)
            lineTo(10.759f, 11.414f)
            curveTo(10.723f, 11.47f, 10.671f, 11.519f, 10.604f, 11.559f)
            curveTo(10.541f, 11.595f, 10.473f, 11.614f, 10.399f, 11.614f)
            close()
          }
        }
        .build()

    return _Power!!
  }

@Suppress("ObjectPropertyName") private var _Power: ImageVector? = null
