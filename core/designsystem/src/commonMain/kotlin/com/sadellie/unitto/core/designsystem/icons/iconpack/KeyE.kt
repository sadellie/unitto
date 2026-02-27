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

val IconPack.KeyE: ImageVector
  get() {
    if (_KeyE != null) {
      return _KeyE!!
    }
    _KeyE =
      ImageVector.Builder(
          name = "KeyE",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(10.503f, 15.669f)
            curveTo(10.354f, 15.669f, 10.224f, 15.615f, 10.113f, 15.509f)
            curveTo(10.007f, 15.399f, 9.953f, 15.267f, 9.953f, 15.114f)
            verticalLineTo(9.059f)
            curveTo(9.953f, 8.909f, 10.007f, 8.78f, 10.113f, 8.674f)
            curveTo(10.224f, 8.564f, 10.354f, 8.509f, 10.503f, 8.509f)
            horizontalLineTo(13.948f)
            curveTo(14.078f, 8.509f, 14.189f, 8.555f, 14.278f, 8.649f)
            curveTo(14.372f, 8.739f, 14.418f, 8.847f, 14.418f, 8.974f)
            curveTo(14.418f, 9.1f, 14.372f, 9.209f, 14.278f, 9.299f)
            curveTo(14.189f, 9.389f, 14.078f, 9.434f, 13.948f, 9.434f)
            horizontalLineTo(10.958f)
            verticalLineTo(14.744f)
            horizontalLineTo(14.068f)
            curveTo(14.198f, 14.744f, 14.309f, 14.789f, 14.399f, 14.879f)
            curveTo(14.492f, 14.969f, 14.538f, 15.077f, 14.538f, 15.204f)
            curveTo(14.538f, 15.33f, 14.492f, 15.44f, 14.399f, 15.534f)
            curveTo(14.309f, 15.624f, 14.198f, 15.669f, 14.068f, 15.669f)
            horizontalLineTo(10.503f)
            close()
            moveTo(10.573f, 12.429f)
            verticalLineTo(11.504f)
            horizontalLineTo(13.363f)
            curveTo(13.49f, 11.504f, 13.599f, 11.55f, 13.689f, 11.644f)
            curveTo(13.782f, 11.734f, 13.828f, 11.842f, 13.828f, 11.969f)
            curveTo(13.828f, 12.095f, 13.782f, 12.204f, 13.689f, 12.294f)
            curveTo(13.599f, 12.384f, 13.49f, 12.429f, 13.363f, 12.429f)
            horizontalLineTo(10.573f)
            close()
          }
        }
        .build()

    return _KeyE!!
  }

@Suppress("ObjectPropertyName") private var _KeyE: ImageVector? = null
