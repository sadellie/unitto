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

val IconPack.Equal: ImageVector
  get() {
    if (_Equal != null) {
      return _Equal!!
    }
    _Equal =
      ImageVector.Builder(
          name = "Equal",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(8.338f, 13.867f)
            curveTo(8.338f, 13.608f, 8.547f, 13.399f, 8.806f, 13.399f)
            horizontalLineTo(15.195f)
            curveTo(15.453f, 13.399f, 15.663f, 13.608f, 15.663f, 13.867f)
            curveTo(15.663f, 14.125f, 15.453f, 14.335f, 15.195f, 14.335f)
            horizontalLineTo(8.806f)
            curveTo(8.547f, 14.335f, 8.338f, 14.125f, 8.338f, 13.867f)
            close()
            moveTo(8.338f, 10.13f)
            curveTo(8.338f, 9.873f, 8.546f, 9.665f, 8.803f, 9.665f)
            horizontalLineTo(15.198f)
            curveTo(15.454f, 9.665f, 15.663f, 9.873f, 15.663f, 10.13f)
            curveTo(15.663f, 10.387f, 15.454f, 10.595f, 15.198f, 10.595f)
            horizontalLineTo(8.803f)
            curveTo(8.546f, 10.595f, 8.338f, 10.387f, 8.338f, 10.13f)
            close()
          }
        }
        .build()

    return _Equal!!
  }

@Suppress("ObjectPropertyName") private var _Equal: ImageVector? = null
