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

val IconPack.Key7: ImageVector
  get() {
    if (_Key7 != null) {
      return _Key7!!
    }
    _Key7 =
      ImageVector.Builder(
          name = "Key7",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(10.549f, 15.669f)
            curveTo(10.416f, 15.602f, 10.329f, 15.5f, 10.289f, 15.364f)
            curveTo(10.249f, 15.227f, 10.263f, 15.095f, 10.329f, 14.969f)
            lineTo(13.264f, 9.474f)
            lineTo(13.249f, 9.444f)
            horizontalLineTo(9.999f)
            curveTo(9.866f, 9.444f, 9.753f, 9.399f, 9.659f, 9.309f)
            curveTo(9.569f, 9.215f, 9.524f, 9.105f, 9.524f, 8.979f)
            curveTo(9.524f, 8.849f, 9.569f, 8.739f, 9.659f, 8.649f)
            curveTo(9.753f, 8.555f, 9.866f, 8.509f, 9.999f, 8.509f)
            horizontalLineTo(13.869f)
            curveTo(14.053f, 8.509f, 14.204f, 8.567f, 14.324f, 8.684f)
            curveTo(14.448f, 8.8f, 14.509f, 8.945f, 14.509f, 9.119f)
            curveTo(14.509f, 9.179f, 14.499f, 9.237f, 14.479f, 9.294f)
            curveTo(14.459f, 9.35f, 14.438f, 9.4f, 14.414f, 9.444f)
            lineTo(11.229f, 15.449f)
            curveTo(11.163f, 15.572f, 11.063f, 15.654f, 10.929f, 15.694f)
            curveTo(10.799f, 15.737f, 10.673f, 15.729f, 10.549f, 15.669f)
            close()
          }
        }
        .build()

    return _Key7!!
  }

@Suppress("ObjectPropertyName") private var _Key7: ImageVector? = null
