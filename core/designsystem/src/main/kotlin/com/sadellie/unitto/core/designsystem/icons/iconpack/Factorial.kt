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
val IconPack.Factorial: ImageVector
  get() {
    if (_factorial != null) {
      return _factorial!!
    }
    _factorial =
      Builder(
          name = "Factorial",
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
            moveTo(12.4322f, 8.5037f)
            verticalLineTo(11.3637f)
            curveTo(12.4322f, 11.5137f, 12.4289f, 11.6604f, 12.4222f, 11.8037f)
            curveTo(12.4156f, 11.947f, 12.4072f, 12.092f, 12.3972f, 12.2387f)
            curveTo(12.3872f, 12.382f, 12.3739f, 12.5304f, 12.3572f, 12.6837f)
            curveTo(12.3439f, 12.8337f, 12.3272f, 12.9954f, 12.3072f, 13.1687f)
            horizontalLineTo(11.7072f)
            curveTo(11.6872f, 12.9954f, 11.6689f, 12.8337f, 11.6522f, 12.6837f)
            curveTo(11.6389f, 12.5304f, 11.6272f, 12.382f, 11.6172f, 12.2387f)
            curveTo(11.6072f, 12.092f, 11.5989f, 11.947f, 11.5922f, 11.8037f)
            curveTo(11.5856f, 11.6604f, 11.5822f, 11.5137f, 11.5822f, 11.3637f)
            verticalLineTo(8.5037f)
            horizontalLineTo(12.4322f)
            close()
            moveTo(11.3672f, 15.1187f)
            curveTo(11.3672f, 15.032f, 11.3822f, 14.9504f, 11.4122f, 14.8737f)
            curveTo(11.4456f, 14.797f, 11.4906f, 14.7304f, 11.5472f, 14.6737f)
            curveTo(11.6039f, 14.617f, 11.6689f, 14.572f, 11.7422f, 14.5387f)
            curveTo(11.8189f, 14.5054f, 11.9022f, 14.4887f, 11.9922f, 14.4887f)
            curveTo(12.0789f, 14.4887f, 12.1589f, 14.5054f, 12.2322f, 14.5387f)
            curveTo(12.3089f, 14.572f, 12.3756f, 14.617f, 12.4322f, 14.6737f)
            curveTo(12.4889f, 14.7304f, 12.5339f, 14.797f, 12.5672f, 14.8737f)
            curveTo(12.6006f, 14.9504f, 12.6172f, 15.032f, 12.6172f, 15.1187f)
            curveTo(12.6172f, 15.2087f, 12.6006f, 15.292f, 12.5672f, 15.3687f)
            curveTo(12.5339f, 15.442f, 12.4889f, 15.507f, 12.4322f, 15.5637f)
            curveTo(12.3756f, 15.6204f, 12.3089f, 15.6637f, 12.2322f, 15.6937f)
            curveTo(12.1589f, 15.727f, 12.0789f, 15.7437f, 11.9922f, 15.7437f)
            curveTo(11.9022f, 15.7437f, 11.8189f, 15.727f, 11.7422f, 15.6937f)
            curveTo(11.6689f, 15.6637f, 11.6039f, 15.6204f, 11.5472f, 15.5637f)
            curveTo(11.4906f, 15.507f, 11.4456f, 15.442f, 11.4122f, 15.3687f)
            curveTo(11.3822f, 15.292f, 11.3672f, 15.2087f, 11.3672f, 15.1187f)
            close()
          }
        }
        .build()
    return _factorial!!
  }

private var _factorial: ImageVector? = null
