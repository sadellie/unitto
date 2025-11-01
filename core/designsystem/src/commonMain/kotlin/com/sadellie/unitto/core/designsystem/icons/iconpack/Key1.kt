/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024 Elshan Agaev
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
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("UnusedReceiverParameter")
val IconPack.Key1: ImageVector
  get() {
    if (_key1 != null) {
      return _key1!!
    }
    _key1 =
      Builder(
          name = "Key1",
          defaultWidth = 24.0.dp,
          defaultHeight = 24.0.dp,
          viewportWidth = 24.0f,
          viewportHeight = 24.0f,
        )
        .apply {
          path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero,
          ) {
            moveTo(14.1896f, 14.9887f)
            verticalLineTo(15.6687f)
            horizontalLineTo(10.3496f)
            verticalLineTo(14.9887f)
            horizontalLineTo(11.8846f)
            verticalLineTo(10.1037f)
            curveTo(11.8846f, 9.957f, 11.8896f, 9.8087f, 11.8996f, 9.6587f)
            lineTo(10.6246f, 10.7537f)
            curveTo(10.5813f, 10.7904f, 10.5379f, 10.8137f, 10.4946f, 10.8237f)
            curveTo(10.4513f, 10.8304f, 10.4113f, 10.8304f, 10.3746f, 10.8237f)
            curveTo(10.3379f, 10.817f, 10.3029f, 10.8037f, 10.2696f, 10.7837f)
            curveTo(10.2396f, 10.7637f, 10.2163f, 10.742f, 10.1996f, 10.7187f)
            lineTo(9.9196f, 10.3337f)
            lineTo(12.0546f, 8.4887f)
            horizontalLineTo(12.7796f)
            verticalLineTo(14.9887f)
            horizontalLineTo(14.1896f)
            close()
          }
        }
        .build()
    return _key1!!
  }

private var _key1: ImageVector? = null
