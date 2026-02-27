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

val IconPack.Dot: ImageVector
  get() {
    if (_Dot != null) {
      return _Dot!!
    }
    _Dot =
      ImageVector.Builder(
          name = "Dot",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(11.262f, 12.172f)
            curveTo(11.262f, 12.069f, 11.279f, 11.973f, 11.315f, 11.882f)
            curveTo(11.354f, 11.792f, 11.405f, 11.713f, 11.469f, 11.646f)
            curveTo(11.535f, 11.579f, 11.614f, 11.526f, 11.705f, 11.486f)
            curveTo(11.795f, 11.447f, 11.892f, 11.427f, 11.994f, 11.427f)
            curveTo(12.097f, 11.427f, 12.193f, 11.447f, 12.284f, 11.486f)
            curveTo(12.374f, 11.526f, 12.453f, 11.579f, 12.52f, 11.646f)
            curveTo(12.587f, 11.713f, 12.64f, 11.792f, 12.679f, 11.882f)
            curveTo(12.719f, 11.973f, 12.738f, 12.069f, 12.738f, 12.172f)
            curveTo(12.738f, 12.278f, 12.719f, 12.376f, 12.679f, 12.467f)
            curveTo(12.64f, 12.554f, 12.587f, 12.63f, 12.52f, 12.697f)
            curveTo(12.453f, 12.764f, 12.374f, 12.815f, 12.284f, 12.851f)
            curveTo(12.193f, 12.89f, 12.097f, 12.91f, 11.994f, 12.91f)
            curveTo(11.892f, 12.91f, 11.795f, 12.89f, 11.705f, 12.851f)
            curveTo(11.614f, 12.815f, 11.535f, 12.764f, 11.469f, 12.697f)
            curveTo(11.405f, 12.63f, 11.354f, 12.554f, 11.315f, 12.467f)
            curveTo(11.279f, 12.376f, 11.262f, 12.278f, 11.262f, 12.172f)
            close()
          }
        }
        .build()

    return _Dot!!
  }

@Suppress("ObjectPropertyName") private var _Dot: ImageVector? = null
