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

val IconPack.Key2: ImageVector
  get() {
    if (_Key2 != null) {
      return _Key2!!
    }
    _Key2 =
      ImageVector.Builder(
          name = "Key2",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(10.174f, 15.669f)
            curveTo(10.004f, 15.669f, 9.859f, 15.612f, 9.739f, 15.499f)
            curveTo(9.622f, 15.382f, 9.564f, 15.242f, 9.564f, 15.079f)
            curveTo(9.564f, 14.982f, 9.582f, 14.899f, 9.619f, 14.829f)
            curveTo(9.655f, 14.759f, 9.699f, 14.699f, 9.749f, 14.649f)
            lineTo(12.039f, 12.329f)
            curveTo(12.375f, 11.989f, 12.644f, 11.675f, 12.844f, 11.389f)
            curveTo(13.047f, 11.102f, 13.149f, 10.794f, 13.149f, 10.464f)
            curveTo(13.149f, 10.11f, 13.039f, 9.824f, 12.819f, 9.604f)
            curveTo(12.602f, 9.384f, 12.305f, 9.274f, 11.929f, 9.274f)
            curveTo(11.685f, 9.274f, 11.484f, 9.312f, 11.324f, 9.389f)
            curveTo(11.167f, 9.465f, 11.025f, 9.575f, 10.899f, 9.719f)
            curveTo(10.775f, 9.862f, 10.679f, 10.007f, 10.609f, 10.154f)
            curveTo(10.539f, 10.297f, 10.442f, 10.385f, 10.319f, 10.419f)
            curveTo(10.199f, 10.452f, 10.08f, 10.445f, 9.964f, 10.399f)
            curveTo(9.85f, 10.352f, 9.769f, 10.265f, 9.719f, 10.139f)
            curveTo(9.669f, 10.012f, 9.685f, 9.845f, 9.769f, 9.639f)
            curveTo(9.852f, 9.432f, 9.995f, 9.225f, 10.199f, 9.019f)
            curveTo(10.405f, 8.809f, 10.65f, 8.644f, 10.934f, 8.524f)
            curveTo(11.22f, 8.4f, 11.555f, 8.339f, 11.939f, 8.339f)
            curveTo(12.612f, 8.339f, 13.157f, 8.539f, 13.574f, 8.939f)
            curveTo(13.99f, 9.339f, 14.199f, 9.844f, 14.199f, 10.454f)
            curveTo(14.199f, 10.86f, 14.109f, 11.234f, 13.929f, 11.574f)
            curveTo(13.749f, 11.914f, 13.434f, 12.304f, 12.984f, 12.744f)
            lineTo(10.989f, 14.704f)
            lineTo(11.004f, 14.734f)
            horizontalLineTo(13.949f)
            curveTo(14.079f, 14.734f, 14.19f, 14.78f, 14.284f, 14.874f)
            curveTo(14.377f, 14.964f, 14.424f, 15.072f, 14.424f, 15.199f)
            curveTo(14.424f, 15.329f, 14.377f, 15.44f, 14.284f, 15.534f)
            curveTo(14.19f, 15.624f, 14.079f, 15.669f, 13.949f, 15.669f)
            horizontalLineTo(10.174f)
            close()
          }
        }
        .build()

    return _Key2!!
  }

@Suppress("ObjectPropertyName") private var _Key2: ImageVector? = null
