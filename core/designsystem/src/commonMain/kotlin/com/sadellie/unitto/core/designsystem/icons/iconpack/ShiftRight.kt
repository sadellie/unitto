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

val IconPack.ShiftRight: ImageVector
  get() {
    if (_ShiftRight != null) {
      return _ShiftRight!!
    }
    _ShiftRight =
      ImageVector.Builder(
          name = "ShiftRight",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color(0xFFE3E3E3))) {
            moveTo(14.908f, 12.001f)
            lineTo(12.251f, 14.668f)
            curveTo(12.16f, 14.758f, 12.112f, 14.871f, 12.107f, 15.005f)
            curveTo(12.101f, 15.138f, 12.149f, 15.256f, 12.251f, 15.358f)
            curveTo(12.346f, 15.453f, 12.461f, 15.5f, 12.596f, 15.5f)
            curveTo(12.731f, 15.5f, 12.846f, 15.453f, 12.941f, 15.358f)
            lineTo(15.883f, 12.415f)
            curveTo(15.945f, 12.354f, 15.988f, 12.289f, 16.013f, 12.221f)
            curveTo(16.038f, 12.153f, 16.051f, 12.08f, 16.051f, 12.001f)
            curveTo(16.051f, 11.922f, 16.038f, 11.849f, 16.013f, 11.781f)
            curveTo(15.988f, 11.713f, 15.945f, 11.648f, 15.883f, 11.587f)
            lineTo(12.941f, 8.644f)
            curveTo(12.85f, 8.554f, 12.738f, 8.506f, 12.604f, 8.5f)
            curveTo(12.47f, 8.495f, 12.353f, 8.543f, 12.251f, 8.644f)
            curveTo(12.156f, 8.739f, 12.109f, 8.854f, 12.109f, 8.989f)
            curveTo(12.109f, 9.125f, 12.156f, 9.24f, 12.251f, 9.335f)
            lineTo(14.908f, 12.001f)
            close()
            moveTo(10.75f, 12.001f)
            lineTo(8.094f, 14.668f)
            curveTo(8.003f, 14.758f, 7.955f, 14.871f, 7.95f, 15.005f)
            curveTo(7.944f, 15.138f, 7.992f, 15.256f, 8.094f, 15.358f)
            curveTo(8.189f, 15.453f, 8.304f, 15.5f, 8.439f, 15.5f)
            curveTo(8.574f, 15.5f, 8.689f, 15.453f, 8.784f, 15.358f)
            lineTo(11.726f, 12.415f)
            curveTo(11.787f, 12.354f, 11.83f, 12.289f, 11.856f, 12.221f)
            curveTo(11.881f, 12.153f, 11.894f, 12.08f, 11.894f, 12.001f)
            curveTo(11.894f, 11.922f, 11.881f, 11.849f, 11.856f, 11.781f)
            curveTo(11.83f, 11.713f, 11.787f, 11.648f, 11.726f, 11.587f)
            lineTo(8.784f, 8.644f)
            curveTo(8.693f, 8.554f, 8.581f, 8.506f, 8.447f, 8.5f)
            curveTo(8.313f, 8.495f, 8.195f, 8.543f, 8.094f, 8.644f)
            curveTo(7.999f, 8.739f, 7.951f, 8.854f, 7.951f, 8.989f)
            curveTo(7.951f, 9.125f, 7.999f, 9.24f, 8.094f, 9.335f)
            lineTo(10.75f, 12.001f)
            close()
          }
        }
        .build()

    return _ShiftRight!!
  }

@Suppress("ObjectPropertyName") private var _ShiftRight: ImageVector? = null
