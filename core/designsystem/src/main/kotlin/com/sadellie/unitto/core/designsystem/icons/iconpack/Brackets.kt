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
val IconPack.Brackets: ImageVector
  get() {
    if (_brackets != null) {
      return _brackets!!
    }
    _brackets =
      Builder(
          name = "Brackets",
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
            moveTo(9.0068f, 12.1688f)
            curveTo(9.0068f, 12.7178f, 9.0748f, 13.255f, 9.2107f, 13.7805f)
            curveTo(9.3467f, 14.3033f, 9.5441f, 14.7961f, 9.8028f, 15.2588f)
            curveTo(9.8264f, 15.3059f, 9.8408f, 15.3477f, 9.846f, 15.3843f)
            curveTo(9.8512f, 15.4209f, 9.8486f, 15.4523f, 9.8382f, 15.4784f)
            curveTo(9.8277f, 15.5072f, 9.812f, 15.5307f, 9.7911f, 15.549f)
            curveTo(9.7728f, 15.5673f, 9.7519f, 15.5843f, 9.7283f, 15.6f)
            lineTo(9.3558f, 15.8313f)
            curveTo(9.1598f, 15.5307f, 8.9938f, 15.2314f, 8.8578f, 14.9334f)
            curveTo(8.7219f, 14.638f, 8.6108f, 14.3399f, 8.5245f, 14.0393f)
            curveTo(8.4382f, 13.7361f, 8.3755f, 13.4302f, 8.3363f, 13.1217f)
            curveTo(8.2971f, 12.8106f, 8.2775f, 12.493f, 8.2775f, 12.1688f)
            curveTo(8.2775f, 11.8473f, 8.2971f, 11.5323f, 8.3363f, 11.2238f)
            curveTo(8.3755f, 10.9127f, 8.4382f, 10.6069f, 8.5245f, 10.3062f)
            curveTo(8.6108f, 10.003f, 8.7219f, 9.7023f, 8.8578f, 9.4043f)
            curveTo(8.9938f, 9.1063f, 9.1598f, 8.807f, 9.3558f, 8.5063f)
            lineTo(9.7283f, 8.7377f)
            curveTo(9.7519f, 8.7534f, 9.7728f, 8.7704f, 9.7911f, 8.7887f)
            curveTo(9.812f, 8.807f, 9.8277f, 8.8305f, 9.8382f, 8.8593f)
            curveTo(9.8486f, 8.8854f, 9.8512f, 8.9168f, 9.846f, 8.9534f)
            curveTo(9.8408f, 8.99f, 9.8264f, 9.0318f, 9.8028f, 9.0789f)
            curveTo(9.5441f, 9.5442f, 9.3467f, 10.0383f, 9.2107f, 10.5611f)
            curveTo(9.0748f, 11.084f, 9.0068f, 11.6199f, 9.0068f, 12.1688f)
            close()
          }
          path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero,
          ) {
            moveTo(14.9933f, 12.1688f)
            curveTo(14.9933f, 12.7178f, 14.9253f, 13.255f, 14.7894f, 13.7805f)
            curveTo(14.6535f, 14.3033f, 14.4561f, 14.7961f, 14.1973f, 15.2588f)
            curveTo(14.1737f, 15.3059f, 14.1594f, 15.3477f, 14.1541f, 15.3843f)
            curveTo(14.1489f, 15.4209f, 14.1515f, 15.4523f, 14.162f, 15.4784f)
            curveTo(14.1724f, 15.5072f, 14.1881f, 15.5307f, 14.209f, 15.549f)
            curveTo(14.2273f, 15.5673f, 14.2482f, 15.5843f, 14.2718f, 15.6f)
            lineTo(14.6443f, 15.8313f)
            curveTo(14.8404f, 15.5307f, 15.0064f, 15.2314f, 15.1423f, 14.9334f)
            curveTo(15.2782f, 14.638f, 15.3893f, 14.3399f, 15.4756f, 14.0393f)
            curveTo(15.5619f, 13.7361f, 15.6246f, 13.4302f, 15.6638f, 13.1217f)
            curveTo(15.7031f, 12.8106f, 15.7227f, 12.493f, 15.7227f, 12.1688f)
            curveTo(15.7227f, 11.8473f, 15.7031f, 11.5323f, 15.6638f, 11.2238f)
            curveTo(15.6246f, 10.9127f, 15.5619f, 10.6069f, 15.4756f, 10.3062f)
            curveTo(15.3893f, 10.003f, 15.2782f, 9.7023f, 15.1423f, 9.4043f)
            curveTo(15.0064f, 9.1063f, 14.8404f, 8.807f, 14.6443f, 8.5063f)
            lineTo(14.2718f, 8.7377f)
            curveTo(14.2482f, 8.7534f, 14.2273f, 8.7704f, 14.209f, 8.7887f)
            curveTo(14.1881f, 8.807f, 14.1724f, 8.8305f, 14.162f, 8.8593f)
            curveTo(14.1515f, 8.8854f, 14.1489f, 8.9168f, 14.1541f, 8.9534f)
            curveTo(14.1594f, 8.99f, 14.1737f, 9.0318f, 14.1973f, 9.0789f)
            curveTo(14.4561f, 9.5442f, 14.6535f, 10.0383f, 14.7894f, 10.5611f)
            curveTo(14.9253f, 11.084f, 14.9933f, 11.6199f, 14.9933f, 12.1688f)
            close()
          }
        }
        .build()
    return _brackets!!
  }

private var _brackets: ImageVector? = null
