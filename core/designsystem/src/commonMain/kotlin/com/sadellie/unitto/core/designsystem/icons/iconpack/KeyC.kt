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

val IconPack.KeyC: ImageVector
  get() {
    if (_KeyC != null) {
      return _KeyC!!
    }
    _KeyC =
      ImageVector.Builder(
          name = "KeyC",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(12.497f, 15.839f)
            curveTo(11.391f, 15.839f, 10.489f, 15.49f, 9.792f, 14.794f)
            curveTo(9.096f, 14.097f, 8.747f, 13.194f, 8.747f, 12.084f)
            curveTo(8.747f, 11.01f, 9.112f, 10.119f, 9.842f, 9.409f)
            curveTo(10.576f, 8.695f, 11.479f, 8.339f, 12.552f, 8.339f)
            curveTo(12.912f, 8.339f, 13.247f, 8.379f, 13.557f, 8.459f)
            curveTo(13.867f, 8.539f, 14.161f, 8.66f, 14.437f, 8.824f)
            curveTo(14.714f, 8.984f, 14.901f, 9.12f, 14.997f, 9.234f)
            curveTo(15.094f, 9.347f, 15.137f, 9.469f, 15.127f, 9.599f)
            curveTo(15.121f, 9.729f, 15.076f, 9.835f, 14.992f, 9.919f)
            curveTo(14.909f, 10.002f, 14.802f, 10.047f, 14.672f, 10.054f)
            curveTo(14.546f, 10.06f, 14.434f, 10.022f, 14.337f, 9.939f)
            curveTo(14.244f, 9.852f, 14.106f, 9.752f, 13.922f, 9.639f)
            curveTo(13.742f, 9.525f, 13.541f, 9.439f, 13.317f, 9.379f)
            curveTo(13.097f, 9.315f, 12.834f, 9.284f, 12.527f, 9.284f)
            curveTo(11.777f, 9.284f, 11.136f, 9.54f, 10.602f, 10.054f)
            curveTo(10.069f, 10.564f, 9.802f, 11.242f, 9.802f, 12.089f)
            curveTo(9.802f, 12.925f, 10.066f, 13.604f, 10.592f, 14.124f)
            curveTo(11.119f, 14.64f, 11.751f, 14.899f, 12.487f, 14.899f)
            curveTo(12.787f, 14.899f, 13.064f, 14.862f, 13.317f, 14.789f)
            curveTo(13.571f, 14.712f, 13.807f, 14.604f, 14.027f, 14.464f)
            curveTo(14.251f, 14.324f, 14.412f, 14.207f, 14.512f, 14.114f)
            curveTo(14.616f, 14.02f, 14.732f, 13.98f, 14.862f, 13.994f)
            curveTo(14.992f, 14.004f, 15.096f, 14.047f, 15.172f, 14.124f)
            curveTo(15.252f, 14.2f, 15.297f, 14.305f, 15.307f, 14.439f)
            curveTo(15.321f, 14.572f, 15.274f, 14.704f, 15.167f, 14.834f)
            curveTo(15.061f, 14.964f, 14.857f, 15.117f, 14.557f, 15.294f)
            curveTo(14.257f, 15.47f, 13.937f, 15.605f, 13.597f, 15.699f)
            curveTo(13.257f, 15.792f, 12.891f, 15.839f, 12.497f, 15.839f)
            close()
          }
        }
        .build()

    return _KeyC!!
  }

@Suppress("ObjectPropertyName") private var _KeyC: ImageVector? = null
