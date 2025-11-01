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
val IconPack.Clear: ImageVector
  get() {
    if (_clear != null) {
      return _clear!!
    }
    _clear =
      Builder(
          name = "Clear",
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
            moveTo(10.3391f, 13.0087f)
            lineTo(9.2141f, 10.0937f)
            curveTo(9.1808f, 10.007f, 9.1458f, 9.907f, 9.1091f, 9.7937f)
            curveTo(9.0725f, 9.677f, 9.0375f, 9.5537f, 9.0041f, 9.4237f)
            curveTo(8.9341f, 9.6937f, 8.8625f, 9.9187f, 8.7891f, 10.0987f)
            lineTo(7.6641f, 13.0087f)
            horizontalLineTo(10.3391f)
            close()
            moveTo(12.3591f, 15.6687f)
            horizontalLineTo(11.6091f)
            curveTo(11.5225f, 15.6687f, 11.4525f, 15.647f, 11.3991f, 15.6037f)
            curveTo(11.3458f, 15.5604f, 11.3058f, 15.5054f, 11.2791f, 15.4387f)
            lineTo(10.6091f, 13.7087f)
            horizontalLineTo(7.3941f)
            lineTo(6.7241f, 15.4387f)
            curveTo(6.7041f, 15.4987f, 6.6658f, 15.552f, 6.6091f, 15.5987f)
            curveTo(6.5525f, 15.6454f, 6.4825f, 15.6687f, 6.3991f, 15.6687f)
            horizontalLineTo(5.6491f)
            lineTo(8.5141f, 8.5037f)
            horizontalLineTo(9.4941f)
            lineTo(12.3591f, 15.6687f)
            close()
            moveTo(17.6462f, 14.1887f)
            curveTo(17.6995f, 14.1887f, 17.7462f, 14.2104f, 17.7862f, 14.2537f)
            lineTo(18.1712f, 14.6687f)
            curveTo(17.8778f, 15.0087f, 17.5212f, 15.2737f, 17.1012f, 15.4637f)
            curveTo(16.6845f, 15.6537f, 16.1795f, 15.7487f, 15.5862f, 15.7487f)
            curveTo(15.0728f, 15.7487f, 14.6062f, 15.6604f, 14.1862f, 15.4837f)
            curveTo(13.7662f, 15.3037f, 13.4078f, 15.0537f, 13.1112f, 14.7337f)
            curveTo(12.8145f, 14.4104f, 12.5845f, 14.0237f, 12.4212f, 13.5737f)
            curveTo(12.2578f, 13.1237f, 12.1762f, 12.6287f, 12.1762f, 12.0887f)
            curveTo(12.1762f, 11.5487f, 12.2612f, 11.0537f, 12.4312f, 10.6037f)
            curveTo(12.6012f, 10.1537f, 12.8395f, 9.767f, 13.1462f, 9.4437f)
            curveTo(13.4562f, 9.1204f, 13.8262f, 8.8704f, 14.2562f, 8.6937f)
            curveTo(14.6862f, 8.5137f, 15.1612f, 8.4237f, 15.6812f, 8.4237f)
            curveTo(16.1912f, 8.4237f, 16.6412f, 8.5054f, 17.0312f, 8.6687f)
            curveTo(17.4212f, 8.832f, 17.7645f, 9.0537f, 18.0612f, 9.3337f)
            lineTo(17.7412f, 9.7787f)
            curveTo(17.7212f, 9.812f, 17.6945f, 9.8404f, 17.6612f, 9.8637f)
            curveTo(17.6312f, 9.8837f, 17.5895f, 9.8937f, 17.5362f, 9.8937f)
            curveTo(17.4762f, 9.8937f, 17.4028f, 9.862f, 17.3162f, 9.7987f)
            curveTo(17.2295f, 9.732f, 17.1162f, 9.6587f, 16.9762f, 9.5787f)
            curveTo(16.8362f, 9.4987f, 16.6612f, 9.427f, 16.4512f, 9.3637f)
            curveTo(16.2412f, 9.297f, 15.9828f, 9.2637f, 15.6762f, 9.2637f)
            curveTo(15.3062f, 9.2637f, 14.9678f, 9.3287f, 14.6612f, 9.4587f)
            curveTo(14.3545f, 9.5854f, 14.0895f, 9.7704f, 13.8662f, 10.0137f)
            curveTo(13.6462f, 10.257f, 13.4745f, 10.5537f, 13.3512f, 10.9037f)
            curveTo(13.2278f, 11.2537f, 13.1662f, 11.6487f, 13.1662f, 12.0887f)
            curveTo(13.1662f, 12.5354f, 13.2295f, 12.9337f, 13.3562f, 13.2837f)
            curveTo(13.4862f, 13.6337f, 13.6612f, 13.9304f, 13.8812f, 14.1737f)
            curveTo(14.1045f, 14.4137f, 14.3662f, 14.597f, 14.6662f, 14.7237f)
            curveTo(14.9695f, 14.8504f, 15.2962f, 14.9137f, 15.6462f, 14.9137f)
            curveTo(15.8595f, 14.9137f, 16.0512f, 14.902f, 16.2212f, 14.8787f)
            curveTo(16.3945f, 14.852f, 16.5528f, 14.812f, 16.6962f, 14.7587f)
            curveTo(16.8428f, 14.7054f, 16.9778f, 14.6387f, 17.1012f, 14.5587f)
            curveTo(17.2278f, 14.4754f, 17.3528f, 14.377f, 17.4762f, 14.2637f)
            curveTo(17.5328f, 14.2137f, 17.5895f, 14.1887f, 17.6462f, 14.1887f)
            close()
          }
        }
        .build()
    return _clear!!
  }

private var _clear: ImageVector? = null
