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

val IconPack.Key9: ImageVector
  get() {
    if (_Key9 != null) {
      return _Key9!!
    }
    _Key9 =
      ImageVector.Builder(
          name = "Key9",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(11.967f, 8.334f)
            curveTo(12.767f, 8.334f, 13.404f, 8.572f, 13.877f, 9.049f)
            curveTo(14.351f, 9.525f, 14.587f, 10.144f, 14.587f, 10.904f)
            curveTo(14.587f, 11.38f, 14.464f, 11.859f, 14.217f, 12.339f)
            curveTo(13.974f, 12.815f, 13.599f, 13.339f, 13.092f, 13.909f)
            lineTo(11.602f, 15.634f)
            curveTo(11.516f, 15.734f, 11.406f, 15.787f, 11.272f, 15.794f)
            curveTo(11.139f, 15.804f, 11.024f, 15.764f, 10.927f, 15.674f)
            curveTo(10.831f, 15.587f, 10.779f, 15.479f, 10.772f, 15.349f)
            curveTo(10.766f, 15.219f, 10.804f, 15.105f, 10.887f, 15.009f)
            lineTo(12.057f, 13.639f)
            curveTo(12.221f, 13.449f, 12.487f, 13.22f, 12.857f, 12.954f)
            curveTo(13.227f, 12.684f, 13.512f, 11.992f, 13.712f, 10.879f)
            lineTo(14.492f, 10.909f)
            curveTo(14.329f, 11.765f, 13.972f, 12.379f, 13.422f, 12.749f)
            curveTo(12.876f, 13.115f, 12.297f, 13.299f, 11.687f, 13.299f)
            curveTo(11.037f, 13.299f, 10.489f, 13.07f, 10.042f, 12.614f)
            curveTo(9.599f, 12.154f, 9.377f, 11.574f, 9.377f, 10.874f)
            curveTo(9.377f, 10.157f, 9.616f, 9.555f, 10.092f, 9.069f)
            curveTo(10.572f, 8.579f, 11.197f, 8.334f, 11.967f, 8.334f)
            close()
            moveTo(11.982f, 9.239f)
            curveTo(11.519f, 9.239f, 11.141f, 9.385f, 10.847f, 9.679f)
            curveTo(10.554f, 9.972f, 10.407f, 10.359f, 10.407f, 10.839f)
            curveTo(10.407f, 11.309f, 10.554f, 11.69f, 10.847f, 11.984f)
            curveTo(11.144f, 12.274f, 11.521f, 12.419f, 11.977f, 12.419f)
            curveTo(12.447f, 12.419f, 12.829f, 12.275f, 13.122f, 11.989f)
            curveTo(13.416f, 11.702f, 13.562f, 11.32f, 13.562f, 10.844f)
            curveTo(13.562f, 10.36f, 13.414f, 9.972f, 13.117f, 9.679f)
            curveTo(12.821f, 9.385f, 12.442f, 9.239f, 11.982f, 9.239f)
            close()
          }
        }
        .build()

    return _Key9!!
  }

@Suppress("ObjectPropertyName") private var _Key9: ImageVector? = null
