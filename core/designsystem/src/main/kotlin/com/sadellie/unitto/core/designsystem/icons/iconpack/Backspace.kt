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
val IconPack.Backspace: ImageVector
  get() {
    if (_backspace != null) {
      return _backspace!!
    }
    _backspace =
      Builder(
          name = "Backspace",
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
            moveTo(11.7f, 14.1687f)
            lineTo(13.0f, 12.8687f)
            lineTo(14.3f, 14.1687f)
            lineTo(15.0f, 13.4687f)
            lineTo(13.7f, 12.1687f)
            lineTo(15.0f, 10.8687f)
            lineTo(14.3f, 10.1687f)
            lineTo(13.0f, 11.4687f)
            lineTo(11.7f, 10.1687f)
            lineTo(11.0f, 10.8687f)
            lineTo(12.3f, 12.1687f)
            lineTo(11.0f, 13.4687f)
            lineTo(11.7f, 14.1687f)
            close()
            moveTo(7.5f, 12.1687f)
            lineTo(9.675f, 9.0937f)
            curveTo(9.7667f, 8.9604f, 9.8854f, 8.8562f, 10.0313f, 8.7812f)
            curveTo(10.1771f, 8.7062f, 10.3333f, 8.6687f, 10.5f, 8.6687f)
            horizontalLineTo(15.5f)
            curveTo(15.775f, 8.6687f, 16.0104f, 8.7666f, 16.2063f, 8.9625f)
            curveTo(16.4021f, 9.1583f, 16.5f, 9.3937f, 16.5f, 9.6687f)
            verticalLineTo(14.6687f)
            curveTo(16.5f, 14.9437f, 16.4021f, 15.1791f, 16.2063f, 15.375f)
            curveTo(16.0104f, 15.5708f, 15.775f, 15.6687f, 15.5f, 15.6687f)
            horizontalLineTo(10.5f)
            curveTo(10.3333f, 15.6687f, 10.1771f, 15.6312f, 10.0313f, 15.5562f)
            curveTo(9.8854f, 15.4812f, 9.7667f, 15.377f, 9.675f, 15.2437f)
            lineTo(7.5f, 12.1687f)
            close()
          }
        }
        .build()
    return _backspace!!
  }

private var _backspace: ImageVector? = null
