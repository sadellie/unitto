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

val IconPack.Modulo: ImageVector
  get() {
    if (_Modulo != null) {
      return _Modulo!!
    }
    _Modulo =
      ImageVector.Builder(
          name = "Modulo",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(12.703f, 15.834f)
            curveTo(12.586f, 15.834f, 12.49f, 15.789f, 12.413f, 15.699f)
            curveTo(12.34f, 15.612f, 12.311f, 15.509f, 12.328f, 15.389f)
            lineTo(13.328f, 8.674f)
            curveTo(13.341f, 8.587f, 13.385f, 8.51f, 13.458f, 8.444f)
            curveTo(13.535f, 8.377f, 13.62f, 8.344f, 13.713f, 8.344f)
            curveTo(13.83f, 8.344f, 13.925f, 8.389f, 13.998f, 8.479f)
            curveTo(14.071f, 8.565f, 14.1f, 8.669f, 14.083f, 8.789f)
            lineTo(13.088f, 15.504f)
            curveTo(13.075f, 15.59f, 13.03f, 15.667f, 12.953f, 15.734f)
            curveTo(12.88f, 15.8f, 12.796f, 15.834f, 12.703f, 15.834f)
            close()
            moveTo(10.298f, 15.834f)
            curveTo(10.181f, 15.834f, 10.085f, 15.79f, 10.008f, 15.704f)
            curveTo(9.935f, 15.617f, 9.906f, 15.512f, 9.923f, 15.389f)
            lineTo(10.928f, 8.674f)
            curveTo(10.941f, 8.587f, 10.985f, 8.51f, 11.058f, 8.444f)
            curveTo(11.131f, 8.377f, 11.215f, 8.344f, 11.308f, 8.344f)
            curveTo(11.425f, 8.344f, 11.52f, 8.389f, 11.593f, 8.479f)
            curveTo(11.67f, 8.565f, 11.698f, 8.669f, 11.678f, 8.789f)
            lineTo(10.683f, 15.499f)
            curveTo(10.67f, 15.589f, 10.625f, 15.667f, 10.548f, 15.734f)
            curveTo(10.475f, 15.8f, 10.391f, 15.834f, 10.298f, 15.834f)
            close()
            moveTo(9.308f, 13.789f)
            curveTo(9.181f, 13.789f, 9.08f, 13.745f, 9.003f, 13.659f)
            curveTo(8.926f, 13.572f, 8.898f, 13.465f, 8.918f, 13.339f)
            curveTo(8.931f, 13.245f, 8.976f, 13.167f, 9.053f, 13.104f)
            curveTo(9.133f, 13.037f, 9.218f, 13.004f, 9.308f, 13.004f)
            horizontalLineTo(14.313f)
            curveTo(14.44f, 13.004f, 14.541f, 13.049f, 14.618f, 13.139f)
            curveTo(14.695f, 13.225f, 14.723f, 13.329f, 14.703f, 13.449f)
            curveTo(14.69f, 13.542f, 14.645f, 13.622f, 14.568f, 13.689f)
            curveTo(14.491f, 13.755f, 14.406f, 13.789f, 14.313f, 13.789f)
            horizontalLineTo(9.308f)
            close()
            moveTo(9.693f, 11.224f)
            curveTo(9.566f, 11.224f, 9.465f, 11.18f, 9.388f, 11.094f)
            curveTo(9.315f, 11.007f, 9.286f, 10.904f, 9.303f, 10.784f)
            curveTo(9.316f, 10.69f, 9.361f, 10.61f, 9.438f, 10.544f)
            curveTo(9.518f, 10.477f, 9.603f, 10.444f, 9.693f, 10.444f)
            horizontalLineTo(14.698f)
            curveTo(14.825f, 10.444f, 14.926f, 10.487f, 15.003f, 10.574f)
            curveTo(15.08f, 10.66f, 15.108f, 10.764f, 15.088f, 10.884f)
            curveTo(15.075f, 10.977f, 15.03f, 11.057f, 14.953f, 11.124f)
            curveTo(14.876f, 11.19f, 14.791f, 11.224f, 14.698f, 11.224f)
            horizontalLineTo(9.693f)
            close()
          }
        }
        .build()

    return _Modulo!!
  }

@Suppress("ObjectPropertyName") private var _Modulo: ImageVector? = null
