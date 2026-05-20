/*
 * Unitto is a calculator for Android
 * Copyright (c) 2026 Elshan Agaev
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

val IconPack.ShiftLeft: ImageVector
  get() {
    if (_ShiftLeft != null) {
      return _ShiftLeft!!
    }
    _ShiftLeft =
      ImageVector.Builder(
          name = "ShiftLeft",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color(0xFFE3E3E3))) {
            moveTo(9.092f, 12.001f)
            lineTo(11.749f, 14.668f)
            curveTo(11.84f, 14.758f, 11.888f, 14.871f, 11.893f, 15.005f)
            curveTo(11.899f, 15.138f, 11.851f, 15.256f, 11.749f, 15.358f)
            curveTo(11.654f, 15.453f, 11.539f, 15.5f, 11.404f, 15.5f)
            curveTo(11.269f, 15.5f, 11.154f, 15.453f, 11.059f, 15.358f)
            lineTo(8.117f, 12.415f)
            curveTo(8.055f, 12.354f, 8.012f, 12.289f, 7.987f, 12.221f)
            curveTo(7.962f, 12.153f, 7.949f, 12.08f, 7.949f, 12.001f)
            curveTo(7.949f, 11.922f, 7.962f, 11.849f, 7.987f, 11.781f)
            curveTo(8.012f, 11.713f, 8.055f, 11.648f, 8.117f, 11.587f)
            lineTo(11.059f, 8.644f)
            curveTo(11.15f, 8.554f, 11.262f, 8.506f, 11.396f, 8.5f)
            curveTo(11.53f, 8.495f, 11.647f, 8.543f, 11.749f, 8.644f)
            curveTo(11.844f, 8.739f, 11.891f, 8.854f, 11.891f, 8.989f)
            curveTo(11.891f, 9.125f, 11.844f, 9.24f, 11.749f, 9.335f)
            lineTo(9.092f, 12.001f)
            close()
            moveTo(13.25f, 12.001f)
            lineTo(15.906f, 14.668f)
            curveTo(15.997f, 14.758f, 16.045f, 14.871f, 16.05f, 15.005f)
            curveTo(16.056f, 15.138f, 16.008f, 15.256f, 15.906f, 15.358f)
            curveTo(15.811f, 15.453f, 15.696f, 15.5f, 15.561f, 15.5f)
            curveTo(15.426f, 15.5f, 15.311f, 15.453f, 15.216f, 15.358f)
            lineTo(12.274f, 12.415f)
            curveTo(12.213f, 12.354f, 12.17f, 12.289f, 12.144f, 12.221f)
            curveTo(12.119f, 12.153f, 12.106f, 12.08f, 12.106f, 12.001f)
            curveTo(12.106f, 11.922f, 12.119f, 11.849f, 12.144f, 11.781f)
            curveTo(12.17f, 11.713f, 12.213f, 11.648f, 12.274f, 11.587f)
            lineTo(15.216f, 8.644f)
            curveTo(15.307f, 8.554f, 15.419f, 8.506f, 15.553f, 8.5f)
            curveTo(15.687f, 8.495f, 15.805f, 8.543f, 15.906f, 8.644f)
            curveTo(16.001f, 8.739f, 16.049f, 8.854f, 16.049f, 8.989f)
            curveTo(16.049f, 9.125f, 16.001f, 9.24f, 15.906f, 9.335f)
            lineTo(13.25f, 12.001f)
            close()
          }
        }
        .build()

    return _ShiftLeft!!
  }

@Suppress("ObjectPropertyName") private var _ShiftLeft: ImageVector? = null
