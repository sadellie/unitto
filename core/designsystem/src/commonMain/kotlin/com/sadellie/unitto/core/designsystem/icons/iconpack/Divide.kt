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

val IconPack.Divide: ImageVector
  get() {
    if (_Divide != null) {
      return _Divide!!
    }
    _Divide =
      ImageVector.Builder(
          name = "Divide",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(11.334f, 9.006f)
            curveTo(11.334f, 8.913f, 11.35f, 8.827f, 11.382f, 8.748f)
            curveTo(11.417f, 8.665f, 11.463f, 8.594f, 11.52f, 8.533f)
            curveTo(11.581f, 8.473f, 11.651f, 8.425f, 11.73f, 8.39f)
            curveTo(11.813f, 8.355f, 11.901f, 8.337f, 11.993f, 8.337f)
            curveTo(12.085f, 8.337f, 12.171f, 8.355f, 12.251f, 8.39f)
            curveTo(12.333f, 8.425f, 12.405f, 8.473f, 12.465f, 8.533f)
            curveTo(12.526f, 8.594f, 12.574f, 8.665f, 12.609f, 8.748f)
            curveTo(12.644f, 8.827f, 12.661f, 8.913f, 12.661f, 9.006f)
            curveTo(12.661f, 9.101f, 12.644f, 9.189f, 12.609f, 9.268f)
            curveTo(12.574f, 9.348f, 12.526f, 9.418f, 12.465f, 9.478f)
            curveTo(12.405f, 9.539f, 12.333f, 9.585f, 12.251f, 9.617f)
            curveTo(12.171f, 9.652f, 12.085f, 9.669f, 11.993f, 9.669f)
            curveTo(11.901f, 9.669f, 11.813f, 9.652f, 11.73f, 9.617f)
            curveTo(11.651f, 9.585f, 11.581f, 9.539f, 11.52f, 9.478f)
            curveTo(11.463f, 9.418f, 11.417f, 9.348f, 11.382f, 9.268f)
            curveTo(11.35f, 9.189f, 11.334f, 9.101f, 11.334f, 9.006f)
            close()
          }
          path(fill = SolidColor(Color.White)) {
            moveTo(11.334f, 14.999f)
            curveTo(11.334f, 14.907f, 11.35f, 14.821f, 11.382f, 14.741f)
            curveTo(11.417f, 14.658f, 11.463f, 14.587f, 11.52f, 14.526f)
            curveTo(11.581f, 14.466f, 11.651f, 14.418f, 11.73f, 14.383f)
            curveTo(11.813f, 14.348f, 11.901f, 14.331f, 11.993f, 14.331f)
            curveTo(12.085f, 14.331f, 12.171f, 14.348f, 12.251f, 14.383f)
            curveTo(12.333f, 14.418f, 12.405f, 14.466f, 12.465f, 14.526f)
            curveTo(12.526f, 14.587f, 12.574f, 14.658f, 12.609f, 14.741f)
            curveTo(12.644f, 14.821f, 12.661f, 14.907f, 12.661f, 14.999f)
            curveTo(12.661f, 15.094f, 12.644f, 15.182f, 12.609f, 15.261f)
            curveTo(12.574f, 15.341f, 12.526f, 15.411f, 12.465f, 15.472f)
            curveTo(12.405f, 15.532f, 12.333f, 15.578f, 12.251f, 15.61f)
            curveTo(12.171f, 15.645f, 12.085f, 15.662f, 11.993f, 15.662f)
            curveTo(11.901f, 15.662f, 11.813f, 15.645f, 11.73f, 15.61f)
            curveTo(11.651f, 15.578f, 11.581f, 15.532f, 11.52f, 15.472f)
            curveTo(11.463f, 15.411f, 11.417f, 15.341f, 11.382f, 15.261f)
            curveTo(11.35f, 15.182f, 11.334f, 15.094f, 11.334f, 14.999f)
            close()
          }
          path(fill = SolidColor(Color.White)) {
            moveTo(8.195f, 11.881f)
            curveTo(8.195f, 11.605f, 8.418f, 11.382f, 8.694f, 11.382f)
            horizontalLineTo(15.306f)
            curveTo(15.582f, 11.382f, 15.805f, 11.605f, 15.805f, 11.881f)
            curveTo(15.805f, 12.157f, 15.582f, 12.38f, 15.306f, 12.38f)
            horizontalLineTo(8.694f)
            curveTo(8.418f, 12.38f, 8.195f, 12.157f, 8.195f, 11.881f)
            close()
          }
        }
        .build()

    return _Divide!!
  }

@Suppress("ObjectPropertyName") private var _Divide: ImageVector? = null
