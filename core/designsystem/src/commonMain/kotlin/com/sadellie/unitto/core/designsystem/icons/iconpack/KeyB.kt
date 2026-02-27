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

val IconPack.KeyB: ImageVector
  get() {
    if (_KeyB != null) {
      return _KeyB!!
    }
    _KeyB =
      ImageVector.Builder(
          name = "KeyB",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(10.21f, 15.669f)
            curveTo(10.057f, 15.672f, 9.927f, 15.62f, 9.821f, 15.514f)
            curveTo(9.714f, 15.404f, 9.661f, 15.274f, 9.661f, 15.124f)
            verticalLineTo(9.059f)
            curveTo(9.661f, 8.909f, 9.714f, 8.78f, 9.821f, 8.674f)
            curveTo(9.931f, 8.564f, 10.061f, 8.509f, 10.21f, 8.509f)
            horizontalLineTo(12.255f)
            curveTo(12.949f, 8.509f, 13.491f, 8.682f, 13.88f, 9.029f)
            curveTo(14.274f, 9.372f, 14.47f, 9.805f, 14.47f, 10.329f)
            curveTo(14.47f, 10.752f, 14.366f, 11.095f, 14.156f, 11.359f)
            curveTo(13.946f, 11.619f, 13.677f, 11.804f, 13.351f, 11.914f)
            verticalLineTo(11.944f)
            curveTo(13.774f, 12.03f, 14.119f, 12.229f, 14.385f, 12.539f)
            curveTo(14.656f, 12.849f, 14.79f, 13.227f, 14.79f, 13.674f)
            curveTo(14.79f, 14.25f, 14.589f, 14.727f, 14.186f, 15.104f)
            curveTo(13.782f, 15.48f, 13.224f, 15.669f, 12.51f, 15.669f)
            horizontalLineTo(10.21f)
            close()
            moveTo(10.665f, 14.764f)
            horizontalLineTo(12.47f)
            curveTo(12.844f, 14.764f, 13.156f, 14.66f, 13.406f, 14.454f)
            curveTo(13.656f, 14.244f, 13.781f, 13.964f, 13.781f, 13.614f)
            curveTo(13.781f, 13.274f, 13.656f, 12.989f, 13.406f, 12.759f)
            curveTo(13.156f, 12.529f, 12.819f, 12.414f, 12.396f, 12.414f)
            horizontalLineTo(10.37f)
            verticalLineTo(11.539f)
            horizontalLineTo(12.241f)
            curveTo(12.611f, 11.539f, 12.91f, 11.444f, 13.141f, 11.254f)
            curveTo(13.37f, 11.06f, 13.486f, 10.794f, 13.486f, 10.454f)
            curveTo(13.486f, 10.107f, 13.367f, 9.847f, 13.13f, 9.674f)
            curveTo(12.894f, 9.5f, 12.594f, 9.414f, 12.231f, 9.414f)
            horizontalLineTo(10.665f)
            verticalLineTo(14.764f)
            close()
          }
        }
        .build()

    return _KeyB!!
  }

@Suppress("ObjectPropertyName") private var _KeyB: ImageVector? = null
