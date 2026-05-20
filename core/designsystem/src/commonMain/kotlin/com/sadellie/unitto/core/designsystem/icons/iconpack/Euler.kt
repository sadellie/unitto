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

val IconPack.Euler: ImageVector
  get() {
    if (_Euler != null) {
      return _Euler!!
    }
    _Euler =
      ImageVector.Builder(
          name = "Euler",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(12.124f, 15.834f)
            curveTo(11.297f, 15.834f, 10.629f, 15.574f, 10.119f, 15.054f)
            curveTo(9.609f, 14.534f, 9.354f, 13.854f, 9.354f, 13.014f)
            curveTo(9.354f, 12.224f, 9.601f, 11.555f, 10.094f, 11.009f)
            curveTo(10.587f, 10.459f, 11.249f, 10.184f, 12.079f, 10.184f)
            curveTo(12.849f, 10.184f, 13.464f, 10.419f, 13.924f, 10.889f)
            curveTo(14.384f, 11.355f, 14.614f, 11.947f, 14.614f, 12.664f)
            verticalLineTo(12.724f)
            curveTo(14.614f, 12.877f, 14.559f, 13.009f, 14.449f, 13.119f)
            curveTo(14.342f, 13.225f, 14.212f, 13.279f, 14.059f, 13.279f)
            horizontalLineTo(9.914f)
            verticalLineTo(12.514f)
            horizontalLineTo(13.644f)
            verticalLineTo(12.504f)
            curveTo(13.624f, 12.097f, 13.479f, 11.752f, 13.209f, 11.469f)
            curveTo(12.939f, 11.182f, 12.563f, 11.039f, 12.079f, 11.039f)
            curveTo(11.576f, 11.039f, 11.161f, 11.215f, 10.834f, 11.569f)
            curveTo(10.511f, 11.919f, 10.349f, 12.4f, 10.349f, 13.014f)
            curveTo(10.349f, 13.654f, 10.519f, 14.139f, 10.859f, 14.469f)
            curveTo(11.199f, 14.799f, 11.636f, 14.964f, 12.169f, 14.964f)
            curveTo(12.396f, 14.964f, 12.596f, 14.939f, 12.769f, 14.889f)
            curveTo(12.946f, 14.835f, 13.108f, 14.76f, 13.254f, 14.664f)
            curveTo(13.404f, 14.567f, 13.521f, 14.48f, 13.604f, 14.404f)
            curveTo(13.688f, 14.324f, 13.781f, 14.282f, 13.884f, 14.279f)
            curveTo(13.991f, 14.275f, 14.089f, 14.31f, 14.179f, 14.384f)
            curveTo(14.269f, 14.454f, 14.321f, 14.544f, 14.334f, 14.654f)
            curveTo(14.351f, 14.764f, 14.314f, 14.877f, 14.224f, 14.994f)
            curveTo(14.134f, 15.11f, 13.983f, 15.242f, 13.769f, 15.389f)
            curveTo(13.556f, 15.535f, 13.323f, 15.645f, 13.069f, 15.719f)
            curveTo(12.819f, 15.795f, 12.504f, 15.834f, 12.124f, 15.834f)
            close()
          }
        }
        .build()

    return _Euler!!
  }

@Suppress("ObjectPropertyName") private var _Euler: ImageVector? = null
