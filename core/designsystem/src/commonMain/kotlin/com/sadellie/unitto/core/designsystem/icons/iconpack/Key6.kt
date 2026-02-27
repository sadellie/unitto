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

val IconPack.Key6: ImageVector
  get() {
    if (_Key6 != null) {
      return _Key6!!
    }
    _Key6 =
      ImageVector.Builder(
          name = "Key6",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(12.037f, 15.844f)
            curveTo(11.237f, 15.844f, 10.601f, 15.605f, 10.127f, 15.129f)
            curveTo(9.654f, 14.652f, 9.417f, 14.034f, 9.417f, 13.274f)
            curveTo(9.417f, 12.797f, 9.539f, 12.32f, 9.782f, 11.844f)
            curveTo(10.029f, 11.364f, 10.406f, 10.839f, 10.912f, 10.269f)
            lineTo(12.402f, 8.544f)
            curveTo(12.489f, 8.444f, 12.599f, 8.39f, 12.732f, 8.384f)
            curveTo(12.866f, 8.374f, 12.981f, 8.414f, 13.077f, 8.504f)
            curveTo(13.174f, 8.59f, 13.226f, 8.699f, 13.232f, 8.829f)
            curveTo(13.239f, 8.959f, 13.201f, 9.072f, 13.117f, 9.169f)
            lineTo(11.947f, 10.539f)
            curveTo(11.784f, 10.729f, 11.517f, 10.959f, 11.147f, 11.229f)
            curveTo(10.777f, 11.495f, 10.492f, 12.185f, 10.292f, 13.299f)
            lineTo(9.512f, 13.269f)
            curveTo(9.676f, 12.412f, 10.031f, 11.8f, 10.577f, 11.434f)
            curveTo(11.127f, 11.064f, 11.707f, 10.879f, 12.317f, 10.879f)
            curveTo(12.967f, 10.879f, 13.514f, 11.109f, 13.957f, 11.569f)
            curveTo(14.404f, 12.025f, 14.627f, 12.604f, 14.627f, 13.304f)
            curveTo(14.627f, 14.02f, 14.387f, 14.624f, 13.907f, 15.114f)
            curveTo(13.431f, 15.6f, 12.807f, 15.844f, 12.037f, 15.844f)
            close()
            moveTo(12.022f, 14.939f)
            curveTo(12.486f, 14.939f, 12.864f, 14.792f, 13.157f, 14.499f)
            curveTo(13.451f, 14.205f, 13.597f, 13.819f, 13.597f, 13.339f)
            curveTo(13.597f, 12.869f, 13.449f, 12.489f, 13.152f, 12.199f)
            curveTo(12.859f, 11.905f, 12.484f, 11.759f, 12.027f, 11.759f)
            curveTo(11.557f, 11.759f, 11.176f, 11.902f, 10.882f, 12.189f)
            curveTo(10.589f, 12.475f, 10.442f, 12.857f, 10.442f, 13.334f)
            curveTo(10.442f, 13.817f, 10.591f, 14.205f, 10.887f, 14.499f)
            curveTo(11.184f, 14.792f, 11.562f, 14.939f, 12.022f, 14.939f)
            close()
          }
        }
        .build()

    return _Key6!!
  }

@Suppress("ObjectPropertyName") private var _Key6: ImageVector? = null
