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

val IconPack.Key8: ImageVector
  get() {
    if (_Key8 != null) {
      return _Key8!!
    }
    _Key8 =
      ImageVector.Builder(
          name = "Key8",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(12.001f, 15.844f)
            curveTo(11.241f, 15.844f, 10.63f, 15.642f, 10.166f, 15.239f)
            curveTo(9.706f, 14.835f, 9.476f, 14.314f, 9.476f, 13.674f)
            curveTo(9.476f, 13.204f, 9.61f, 12.809f, 9.876f, 12.489f)
            curveTo(10.143f, 12.169f, 10.478f, 11.952f, 10.881f, 11.839f)
            verticalLineTo(11.809f)
            curveTo(10.555f, 11.679f, 10.285f, 11.482f, 10.071f, 11.219f)
            curveTo(9.858f, 10.952f, 9.751f, 10.624f, 9.751f, 10.234f)
            curveTo(9.751f, 9.677f, 9.956f, 9.222f, 10.366f, 8.869f)
            curveTo(10.78f, 8.515f, 11.325f, 8.339f, 12.001f, 8.339f)
            curveTo(12.678f, 8.339f, 13.221f, 8.515f, 13.631f, 8.869f)
            curveTo(14.045f, 9.222f, 14.251f, 9.677f, 14.251f, 10.234f)
            curveTo(14.251f, 10.624f, 14.141f, 10.955f, 13.921f, 11.229f)
            curveTo(13.705f, 11.499f, 13.436f, 11.692f, 13.116f, 11.809f)
            verticalLineTo(11.839f)
            curveTo(13.523f, 11.952f, 13.86f, 12.164f, 14.126f, 12.474f)
            curveTo(14.393f, 12.78f, 14.526f, 13.18f, 14.526f, 13.674f)
            curveTo(14.526f, 14.314f, 14.295f, 14.835f, 13.831f, 15.239f)
            curveTo(13.371f, 15.642f, 12.761f, 15.844f, 12.001f, 15.844f)
            close()
            moveTo(12.001f, 14.919f)
            curveTo(12.468f, 14.919f, 12.833f, 14.799f, 13.096f, 14.559f)
            curveTo(13.363f, 14.315f, 13.496f, 14.004f, 13.496f, 13.624f)
            curveTo(13.496f, 13.247f, 13.365f, 12.934f, 13.101f, 12.684f)
            curveTo(12.838f, 12.434f, 12.471f, 12.309f, 12.001f, 12.309f)
            curveTo(11.538f, 12.309f, 11.173f, 12.432f, 10.906f, 12.679f)
            curveTo(10.64f, 12.922f, 10.506f, 13.234f, 10.506f, 13.614f)
            curveTo(10.506f, 13.994f, 10.638f, 14.307f, 10.901f, 14.554f)
            curveTo(11.168f, 14.797f, 11.535f, 14.919f, 12.001f, 14.919f)
            close()
            moveTo(12.001f, 11.424f)
            curveTo(12.371f, 11.424f, 12.671f, 11.324f, 12.901f, 11.124f)
            curveTo(13.131f, 10.92f, 13.246f, 10.655f, 13.246f, 10.329f)
            curveTo(13.246f, 9.999f, 13.131f, 9.737f, 12.901f, 9.544f)
            curveTo(12.671f, 9.347f, 12.371f, 9.249f, 12.001f, 9.249f)
            curveTo(11.631f, 9.249f, 11.331f, 9.347f, 11.101f, 9.544f)
            curveTo(10.871f, 9.737f, 10.756f, 9.999f, 10.756f, 10.329f)
            curveTo(10.756f, 10.655f, 10.871f, 10.92f, 11.101f, 11.124f)
            curveTo(11.331f, 11.324f, 11.631f, 11.424f, 12.001f, 11.424f)
            close()
          }
        }
        .build()

    return _Key8!!
  }

@Suppress("ObjectPropertyName") private var _Key8: ImageVector? = null
