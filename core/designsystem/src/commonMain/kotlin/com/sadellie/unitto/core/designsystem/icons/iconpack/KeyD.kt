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

val IconPack.KeyD: ImageVector
  get() {
    if (_KeyD != null) {
      return _KeyD!!
    }
    _KeyD =
      ImageVector.Builder(
          name = "KeyD",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(9.703f, 15.669f)
            curveTo(9.553f, 15.669f, 9.423f, 15.615f, 9.313f, 15.509f)
            curveTo(9.206f, 15.399f, 9.153f, 15.267f, 9.153f, 15.114f)
            verticalLineTo(9.059f)
            curveTo(9.153f, 8.909f, 9.206f, 8.78f, 9.313f, 8.674f)
            curveTo(9.423f, 8.564f, 9.553f, 8.509f, 9.703f, 8.509f)
            horizontalLineTo(11.593f)
            curveTo(12.726f, 8.509f, 13.621f, 8.824f, 14.278f, 9.454f)
            curveTo(14.938f, 10.084f, 15.268f, 10.96f, 15.268f, 12.084f)
            curveTo(15.268f, 13.17f, 14.933f, 14.04f, 14.263f, 14.694f)
            curveTo(13.596f, 15.344f, 12.706f, 15.669f, 11.593f, 15.669f)
            horizontalLineTo(9.703f)
            close()
            moveTo(10.183f, 14.719f)
            horizontalLineTo(11.508f)
            curveTo(12.351f, 14.719f, 13.016f, 14.495f, 13.503f, 14.049f)
            curveTo(13.989f, 13.599f, 14.233f, 12.945f, 14.233f, 12.089f)
            curveTo(14.233f, 11.209f, 13.988f, 10.55f, 13.498f, 10.114f)
            curveTo(13.011f, 9.677f, 12.351f, 9.459f, 11.518f, 9.459f)
            horizontalLineTo(10.183f)
            verticalLineTo(14.719f)
            close()
          }
        }
        .build()

    return _KeyD!!
  }

@Suppress("ObjectPropertyName") private var _KeyD: ImageVector? = null
