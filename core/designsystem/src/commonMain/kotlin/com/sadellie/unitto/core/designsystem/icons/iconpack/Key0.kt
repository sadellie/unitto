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

val IconPack.Key0: ImageVector
  get() {
    if (_Key0 != null) {
      return _Key0!!
    }
    _Key0 =
      ImageVector.Builder(
          name = "Key0",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(11.998f, 15.844f)
            curveTo(11.105f, 15.844f, 10.388f, 15.497f, 9.848f, 14.804f)
            curveTo(9.312f, 14.107f, 9.043f, 13.2f, 9.043f, 12.084f)
            curveTo(9.043f, 10.97f, 9.312f, 10.067f, 9.848f, 9.374f)
            curveTo(10.388f, 8.68f, 11.105f, 8.334f, 11.998f, 8.334f)
            curveTo(12.892f, 8.334f, 13.608f, 8.68f, 14.148f, 9.374f)
            curveTo(14.688f, 10.067f, 14.958f, 10.97f, 14.958f, 12.084f)
            curveTo(14.958f, 13.2f, 14.688f, 14.107f, 14.148f, 14.804f)
            curveTo(13.608f, 15.497f, 12.892f, 15.844f, 11.998f, 15.844f)
            close()
            moveTo(11.998f, 14.904f)
            curveTo(12.632f, 14.904f, 13.107f, 14.642f, 13.423f, 14.119f)
            curveTo(13.743f, 13.592f, 13.903f, 12.914f, 13.903f, 12.084f)
            curveTo(13.903f, 11.254f, 13.743f, 10.579f, 13.423f, 10.059f)
            curveTo(13.107f, 9.535f, 12.632f, 9.274f, 11.998f, 9.274f)
            curveTo(11.365f, 9.274f, 10.888f, 9.535f, 10.568f, 10.059f)
            curveTo(10.252f, 10.579f, 10.093f, 11.254f, 10.093f, 12.084f)
            curveTo(10.093f, 12.914f, 10.252f, 13.592f, 10.568f, 14.119f)
            curveTo(10.888f, 14.642f, 11.365f, 14.904f, 11.998f, 14.904f)
            close()
          }
        }
        .build()

    return _Key0!!
  }

@Suppress("ObjectPropertyName") private var _Key0: ImageVector? = null
