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
val IconPack.Euler: ImageVector
  get() {
    if (_euler != null) {
      return _euler!!
    }
    _euler =
      Builder(
          name = "Euler",
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
            moveTo(13.4534f, 12.5837f)
            curveTo(13.4534f, 12.377f, 13.4234f, 12.1887f, 13.3634f, 12.0187f)
            curveTo(13.3067f, 11.8454f, 13.2217f, 11.697f, 13.1084f, 11.5737f)
            curveTo(12.9984f, 11.447f, 12.8634f, 11.3504f, 12.7034f, 11.2837f)
            curveTo(12.5434f, 11.2137f, 12.3617f, 11.1787f, 12.1584f, 11.1787f)
            curveTo(11.7317f, 11.1787f, 11.3934f, 11.3037f, 11.1434f, 11.5537f)
            curveTo(10.8967f, 11.8004f, 10.7434f, 12.1437f, 10.6834f, 12.5837f)
            horizontalLineTo(13.4534f)
            close()
            moveTo(14.1734f, 14.9587f)
            curveTo(14.0634f, 15.092f, 13.9317f, 15.2087f, 13.7784f, 15.3087f)
            curveTo(13.6251f, 15.4054f, 13.4601f, 15.4854f, 13.2834f, 15.5487f)
            curveTo(13.1101f, 15.612f, 12.9301f, 15.6587f, 12.7434f, 15.6887f)
            curveTo(12.5567f, 15.722f, 12.3717f, 15.7387f, 12.1884f, 15.7387f)
            curveTo(11.8384f, 15.7387f, 11.5151f, 15.6804f, 11.2184f, 15.5637f)
            curveTo(10.9251f, 15.4437f, 10.6701f, 15.2704f, 10.4534f, 15.0437f)
            curveTo(10.2401f, 14.8137f, 10.0734f, 14.5304f, 9.9534f, 14.1937f)
            curveTo(9.8334f, 13.857f, 9.7734f, 13.4704f, 9.7734f, 13.0337f)
            curveTo(9.7734f, 12.6804f, 9.8267f, 12.3504f, 9.9334f, 12.0437f)
            curveTo(10.0434f, 11.737f, 10.2001f, 11.472f, 10.4034f, 11.2487f)
            curveTo(10.6067f, 11.022f, 10.8551f, 10.8454f, 11.1484f, 10.7187f)
            curveTo(11.4417f, 10.5887f, 11.7717f, 10.5237f, 12.1384f, 10.5237f)
            curveTo(12.4417f, 10.5237f, 12.7217f, 10.5754f, 12.9784f, 10.6787f)
            curveTo(13.2384f, 10.7787f, 13.4617f, 10.9254f, 13.6484f, 11.1187f)
            curveTo(13.8384f, 11.3087f, 13.9867f, 11.5454f, 14.0934f, 11.8287f)
            curveTo(14.2001f, 12.1087f, 14.2534f, 12.4287f, 14.2534f, 12.7887f)
            curveTo(14.2534f, 12.9287f, 14.2384f, 13.022f, 14.2084f, 13.0687f)
            curveTo(14.1784f, 13.1154f, 14.1217f, 13.1387f, 14.0384f, 13.1387f)
            horizontalLineTo(10.6534f)
            curveTo(10.6634f, 13.4587f, 10.7067f, 13.737f, 10.7834f, 13.9737f)
            curveTo(10.8634f, 14.2104f, 10.9734f, 14.4087f, 11.1134f, 14.5687f)
            curveTo(11.2534f, 14.7254f, 11.4201f, 14.8437f, 11.6134f, 14.9237f)
            curveTo(11.8067f, 15.0004f, 12.0234f, 15.0387f, 12.2634f, 15.0387f)
            curveTo(12.4867f, 15.0387f, 12.6784f, 15.0137f, 12.8384f, 14.9637f)
            curveTo(13.0017f, 14.9104f, 13.1417f, 14.8537f, 13.2584f, 14.7937f)
            curveTo(13.3751f, 14.7337f, 13.4717f, 14.6787f, 13.5484f, 14.6287f)
            curveTo(13.6284f, 14.5754f, 13.6967f, 14.5487f, 13.7534f, 14.5487f)
            curveTo(13.8267f, 14.5487f, 13.8834f, 14.577f, 13.9234f, 14.6337f)
            lineTo(14.1734f, 14.9587f)
            close()
          }
        }
        .build()
    return _euler!!
  }

private var _euler: ImageVector? = null
