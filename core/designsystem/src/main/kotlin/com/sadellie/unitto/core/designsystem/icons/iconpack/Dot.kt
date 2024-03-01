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
val IconPack.Dot: ImageVector
  get() {
    if (_dot != null) {
      return _dot!!
    }
    _dot =
      Builder(
          name = "Dot",
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
            moveTo(11.2617f, 12.1715f)
            curveTo(11.2617f, 12.0691f, 11.2794f, 11.9726f, 11.3149f, 11.8821f)
            curveTo(11.3543f, 11.7915f, 11.4054f, 11.7127f, 11.4685f, 11.6458f)
            curveTo(11.5354f, 11.5789f, 11.6142f, 11.5257f, 11.7047f, 11.4863f)
            curveTo(11.7953f, 11.4469f, 11.8918f, 11.4272f, 11.9941f, 11.4272f)
            curveTo(12.0965f, 11.4272f, 12.193f, 11.4469f, 12.2836f, 11.4863f)
            curveTo(12.3741f, 11.5257f, 12.4529f, 11.5789f, 12.5198f, 11.6458f)
            curveTo(12.5868f, 11.7127f, 12.6399f, 11.7915f, 12.6793f, 11.8821f)
            curveTo(12.7187f, 11.9726f, 12.7384f, 12.0691f, 12.7384f, 12.1715f)
            curveTo(12.7384f, 12.2778f, 12.7187f, 12.3763f, 12.6793f, 12.4668f)
            curveTo(12.6399f, 12.5535f, 12.5868f, 12.6302f, 12.5198f, 12.6972f)
            curveTo(12.4529f, 12.7641f, 12.3741f, 12.8153f, 12.2836f, 12.8508f)
            curveTo(12.193f, 12.8901f, 12.0965f, 12.9098f, 11.9941f, 12.9098f)
            curveTo(11.8918f, 12.9098f, 11.7953f, 12.8901f, 11.7047f, 12.8508f)
            curveTo(11.6142f, 12.8153f, 11.5354f, 12.7641f, 11.4685f, 12.6972f)
            curveTo(11.4054f, 12.6302f, 11.3543f, 12.5535f, 11.3149f, 12.4668f)
            curveTo(11.2794f, 12.3763f, 11.2617f, 12.2778f, 11.2617f, 12.1715f)
            close()
          }
        }
        .build()
    return _dot!!
  }

private var _dot: ImageVector? = null
