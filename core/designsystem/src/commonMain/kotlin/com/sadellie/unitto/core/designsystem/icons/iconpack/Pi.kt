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
val IconPack.Pi: ImageVector
  get() {
    if (_pi != null) {
      return _pi!!
    }
    _pi =
      Builder(
          name = "Pi",
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
            moveTo(14.7588f, 11.0737f)
            curveTo(14.7588f, 11.1537f, 14.7338f, 11.222f, 14.6838f, 11.2787f)
            curveTo(14.6372f, 11.332f, 14.5655f, 11.3587f, 14.4688f, 11.3587f)
            horizontalLineTo(13.7338f)
            curveTo(13.7305f, 11.5487f, 13.7272f, 11.7254f, 13.7238f, 11.8887f)
            curveTo(13.7205f, 12.052f, 13.7188f, 12.217f, 13.7188f, 12.3837f)
            curveTo(13.7188f, 12.7804f, 13.7255f, 13.122f, 13.7388f, 13.4087f)
            curveTo(13.7522f, 13.692f, 13.7705f, 13.932f, 13.7938f, 14.1287f)
            curveTo(13.8205f, 14.3254f, 13.8538f, 14.4837f, 13.8938f, 14.6037f)
            curveTo(13.9338f, 14.7237f, 13.9805f, 14.8154f, 14.0338f, 14.8787f)
            curveTo(14.0872f, 14.942f, 14.1472f, 14.9854f, 14.2138f, 15.0087f)
            curveTo(14.2805f, 15.0287f, 14.3555f, 15.0387f, 14.4388f, 15.0387f)
            horizontalLineTo(14.6288f)
            verticalLineTo(15.3987f)
            curveTo(14.6288f, 15.4954f, 14.5722f, 15.5704f, 14.4588f, 15.6237f)
            curveTo(14.3488f, 15.6737f, 14.2088f, 15.6987f, 14.0388f, 15.6987f)
            curveTo(13.8422f, 15.6987f, 13.6688f, 15.6654f, 13.5188f, 15.5987f)
            curveTo(13.3722f, 15.532f, 13.2505f, 15.4287f, 13.1538f, 15.2887f)
            curveTo(13.0572f, 15.1487f, 12.9838f, 14.9687f, 12.9338f, 14.7487f)
            curveTo(12.8872f, 14.5254f, 12.8638f, 14.2587f, 12.8638f, 13.9487f)
            curveTo(12.8638f, 13.7987f, 12.8705f, 13.637f, 12.8838f, 13.4637f)
            curveTo(12.8972f, 13.287f, 12.9122f, 13.0937f, 12.9288f, 12.8837f)
            curveTo(12.9455f, 12.6737f, 12.9622f, 12.4437f, 12.9788f, 12.1937f)
            curveTo(12.9955f, 11.9404f, 13.0088f, 11.662f, 13.0188f, 11.3587f)
            horizontalLineTo(11.1688f)
            curveTo(11.1588f, 11.932f, 11.1338f, 12.4387f, 11.0938f, 12.8787f)
            curveTo(11.0538f, 13.3187f, 11.0022f, 13.7004f, 10.9388f, 14.0237f)
            curveTo(10.8755f, 14.3437f, 10.8005f, 14.612f, 10.7138f, 14.8287f)
            curveTo(10.6305f, 15.0454f, 10.5372f, 15.2187f, 10.4338f, 15.3487f)
            curveTo(10.3338f, 15.4787f, 10.2238f, 15.572f, 10.1038f, 15.6287f)
            curveTo(9.9838f, 15.682f, 9.8588f, 15.7087f, 9.7288f, 15.7087f)
            curveTo(9.6888f, 15.7087f, 9.6472f, 15.7054f, 9.6038f, 15.6987f)
            curveTo(9.5605f, 15.692f, 9.5188f, 15.682f, 9.4788f, 15.6687f)
            curveTo(9.4422f, 15.652f, 9.4122f, 15.632f, 9.3888f, 15.6087f)
            curveTo(9.3655f, 15.5854f, 9.3538f, 15.5587f, 9.3538f, 15.5287f)
            verticalLineTo(14.9237f)
            horizontalLineTo(9.4538f)
            curveTo(9.5538f, 14.9237f, 9.6555f, 14.8604f, 9.7588f, 14.7337f)
            curveTo(9.8622f, 14.6037f, 9.9572f, 14.3987f, 10.0438f, 14.1187f)
            curveTo(10.1305f, 13.8354f, 10.2055f, 13.467f, 10.2688f, 13.0137f)
            curveTo(10.3322f, 12.5604f, 10.3722f, 12.0087f, 10.3888f, 11.3587f)
            horizontalLineTo(9.2938f)
            verticalLineTo(10.9837f)
            curveTo(9.2938f, 10.947f, 9.3005f, 10.9104f, 9.3138f, 10.8737f)
            curveTo(9.3305f, 10.8337f, 9.3538f, 10.797f, 9.3838f, 10.7637f)
            curveTo(9.4138f, 10.7304f, 9.4488f, 10.7037f, 9.4888f, 10.6837f)
            curveTo(9.5322f, 10.6637f, 9.5822f, 10.6537f, 9.6388f, 10.6537f)
            horizontalLineTo(14.7588f)
            verticalLineTo(11.0737f)
            close()
          }
        }
        .build()
    return _pi!!
  }

private var _pi: ImageVector? = null
