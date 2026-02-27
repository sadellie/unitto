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

val IconPack.Comma: ImageVector
  get() {
    if (_Comma != null) {
      return _Comma!!
    }
    _Comma =
      ImageVector.Builder(
          name = "Comma",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(11.466f, 13.392f)
            curveTo(11.437f, 13.366f, 11.417f, 13.343f, 11.406f, 13.321f)
            curveTo(11.395f, 13.299f, 11.39f, 13.272f, 11.39f, 13.239f)
            curveTo(11.39f, 13.214f, 11.397f, 13.188f, 11.412f, 13.163f)
            curveTo(11.43f, 13.137f, 11.45f, 13.114f, 11.472f, 13.092f)
            curveTo(11.508f, 13.052f, 11.553f, 13f, 11.608f, 12.934f)
            curveTo(11.666f, 12.869f, 11.724f, 12.791f, 11.782f, 12.7f)
            curveTo(11.84f, 12.613f, 11.893f, 12.515f, 11.94f, 12.406f)
            curveTo(11.991f, 12.3f, 12.027f, 12.188f, 12.049f, 12.068f)
            curveTo(12.038f, 12.071f, 12.025f, 12.073f, 12.011f, 12.073f)
            curveTo(12f, 12.073f, 11.989f, 12.073f, 11.978f, 12.073f)
            curveTo(11.789f, 12.073f, 11.635f, 12.012f, 11.515f, 11.888f)
            curveTo(11.399f, 11.761f, 11.341f, 11.599f, 11.341f, 11.403f)
            curveTo(11.341f, 11.233f, 11.399f, 11.087f, 11.515f, 10.967f)
            curveTo(11.635f, 10.847f, 11.793f, 10.788f, 11.989f, 10.788f)
            curveTo(12.098f, 10.788f, 12.194f, 10.808f, 12.278f, 10.847f)
            curveTo(12.361f, 10.887f, 12.43f, 10.944f, 12.485f, 11.016f)
            curveTo(12.543f, 11.085f, 12.587f, 11.167f, 12.616f, 11.262f)
            curveTo(12.645f, 11.352f, 12.659f, 11.452f, 12.659f, 11.561f)
            curveTo(12.659f, 11.725f, 12.636f, 11.895f, 12.588f, 12.073f)
            curveTo(12.545f, 12.248f, 12.478f, 12.42f, 12.387f, 12.591f)
            curveTo(12.3f, 12.765f, 12.193f, 12.934f, 12.065f, 13.097f)
            curveTo(11.938f, 13.261f, 11.793f, 13.412f, 11.63f, 13.55f)
            lineTo(11.466f, 13.392f)
            close()
          }
        }
        .build()

    return _Comma!!
  }

@Suppress("ObjectPropertyName") private var _Comma: ImageVector? = null
