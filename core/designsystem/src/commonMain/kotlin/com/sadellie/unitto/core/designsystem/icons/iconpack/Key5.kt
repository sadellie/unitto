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

val IconPack.Key5: ImageVector
  get() {
    if (_Key5 != null) {
      return _Key5!!
    }
    _Key5 =
      ImageVector.Builder(
          name = "Key5",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(11.949f, 15.844f)
            curveTo(11.536f, 15.844f, 11.164f, 15.772f, 10.834f, 15.629f)
            curveTo(10.508f, 15.485f, 10.236f, 15.29f, 10.019f, 15.044f)
            curveTo(9.803f, 14.794f, 9.661f, 14.579f, 9.594f, 14.399f)
            curveTo(9.528f, 14.219f, 9.526f, 14.069f, 9.589f, 13.949f)
            curveTo(9.653f, 13.825f, 9.741f, 13.742f, 9.854f, 13.699f)
            curveTo(9.971f, 13.655f, 10.091f, 13.655f, 10.214f, 13.699f)
            curveTo(10.338f, 13.742f, 10.431f, 13.835f, 10.494f, 13.979f)
            curveTo(10.561f, 14.119f, 10.661f, 14.267f, 10.794f, 14.424f)
            curveTo(10.928f, 14.58f, 11.088f, 14.7f, 11.274f, 14.784f)
            curveTo(11.461f, 14.864f, 11.681f, 14.904f, 11.934f, 14.904f)
            curveTo(12.388f, 14.904f, 12.753f, 14.765f, 13.029f, 14.489f)
            curveTo(13.309f, 14.212f, 13.449f, 13.844f, 13.449f, 13.384f)
            curveTo(13.449f, 12.93f, 13.309f, 12.569f, 13.029f, 12.299f)
            curveTo(12.749f, 12.025f, 12.399f, 11.889f, 11.979f, 11.889f)
            curveTo(11.743f, 11.889f, 11.538f, 11.932f, 11.364f, 12.019f)
            curveTo(11.191f, 12.105f, 11.056f, 12.19f, 10.959f, 12.274f)
            curveTo(10.836f, 12.377f, 10.709f, 12.45f, 10.579f, 12.494f)
            curveTo(10.453f, 12.534f, 10.314f, 12.524f, 10.164f, 12.464f)
            curveTo(10.021f, 12.404f, 9.911f, 12.304f, 9.834f, 12.164f)
            curveTo(9.761f, 12.024f, 9.734f, 11.882f, 9.754f, 11.739f)
            lineTo(10.094f, 9.059f)
            curveTo(10.111f, 8.909f, 10.181f, 8.78f, 10.304f, 8.674f)
            curveTo(10.428f, 8.564f, 10.564f, 8.509f, 10.714f, 8.509f)
            horizontalLineTo(13.614f)
            curveTo(13.744f, 8.509f, 13.856f, 8.555f, 13.949f, 8.649f)
            curveTo(14.043f, 8.739f, 14.089f, 8.849f, 14.089f, 8.979f)
            curveTo(14.089f, 9.105f, 14.043f, 9.215f, 13.949f, 9.309f)
            curveTo(13.856f, 9.399f, 13.744f, 9.444f, 13.614f, 9.444f)
            horizontalLineTo(10.944f)
            lineTo(10.674f, 11.499f)
            lineTo(10.709f, 11.509f)
            curveTo(10.906f, 11.342f, 11.124f, 11.21f, 11.364f, 11.114f)
            curveTo(11.608f, 11.017f, 11.884f, 10.969f, 12.194f, 10.969f)
            curveTo(12.821f, 10.969f, 13.354f, 11.195f, 13.794f, 11.649f)
            curveTo(14.234f, 12.102f, 14.454f, 12.684f, 14.454f, 13.394f)
            curveTo(14.454f, 14.114f, 14.218f, 14.702f, 13.744f, 15.159f)
            curveTo(13.274f, 15.615f, 12.676f, 15.844f, 11.949f, 15.844f)
            close()
          }
        }
        .build()

    return _Key5!!
  }

@Suppress("ObjectPropertyName") private var _Key5: ImageVector? = null
