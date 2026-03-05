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

val IconPack.Root: ImageVector
  get() {
    if (_Root != null) {
      return _Root!!
    }
    _Root =
      ImageVector.Builder(
          name = "Root",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(11.02f, 16.239f)
            curveTo(10.877f, 16.239f, 10.744f, 16.192f, 10.62f, 16.099f)
            curveTo(10.497f, 16.005f, 10.412f, 15.895f, 10.365f, 15.769f)
            lineTo(8.74f, 11.204f)
            curveTo(8.677f, 11.027f, 8.692f, 10.872f, 8.785f, 10.739f)
            curveTo(8.882f, 10.602f, 9.019f, 10.534f, 9.195f, 10.534f)
            curveTo(9.299f, 10.534f, 9.397f, 10.569f, 9.49f, 10.639f)
            curveTo(9.584f, 10.705f, 9.647f, 10.785f, 9.68f, 10.879f)
            lineTo(10.785f, 14.164f)
            lineTo(11f, 14.849f)
            horizontalLineTo(11.03f)
            lineTo(11.24f, 14.164f)
            lineTo(13.33f, 7.949f)
            curveTo(13.38f, 7.799f, 13.475f, 7.674f, 13.615f, 7.574f)
            curveTo(13.755f, 7.474f, 13.902f, 7.424f, 14.055f, 7.424f)
            horizontalLineTo(15.31f)
            curveTo(15.434f, 7.424f, 15.539f, 7.467f, 15.625f, 7.554f)
            curveTo(15.712f, 7.637f, 15.755f, 7.739f, 15.755f, 7.859f)
            curveTo(15.755f, 7.979f, 15.712f, 8.082f, 15.625f, 8.169f)
            curveTo(15.539f, 8.255f, 15.434f, 8.299f, 15.31f, 8.299f)
            horizontalLineTo(14.25f)
            lineTo(11.675f, 15.764f)
            curveTo(11.629f, 15.894f, 11.544f, 16.005f, 11.42f, 16.099f)
            curveTo(11.297f, 16.192f, 11.164f, 16.239f, 11.02f, 16.239f)
            close()
          }
        }
        .build()

    return _Root!!
  }

@Suppress("ObjectPropertyName") private var _Root: ImageVector? = null
