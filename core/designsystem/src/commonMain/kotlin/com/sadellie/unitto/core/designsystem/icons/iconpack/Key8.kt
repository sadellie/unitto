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
val IconPack.Key8: ImageVector
  get() {
    if (_key8 != null) {
      return _key8!!
    }
    _key8 =
      Builder(
          name = "Key8",
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
            moveTo(11.9996f, 15.0387f)
            curveTo(12.2329f, 15.0387f, 12.4413f, 15.007f, 12.6246f, 14.9437f)
            curveTo(12.8113f, 14.877f, 12.9679f, 14.7854f, 13.0946f, 14.6687f)
            curveTo(13.2246f, 14.5487f, 13.3229f, 14.407f, 13.3896f, 14.2437f)
            curveTo(13.4596f, 14.077f, 13.4946f, 13.8937f, 13.4946f, 13.6937f)
            curveTo(13.4946f, 13.447f, 13.4529f, 13.237f, 13.3696f, 13.0637f)
            curveTo(13.2896f, 12.887f, 13.1796f, 12.7437f, 13.0396f, 12.6337f)
            curveTo(12.9029f, 12.5237f, 12.7446f, 12.4437f, 12.5646f, 12.3937f)
            curveTo(12.3846f, 12.3404f, 12.1963f, 12.3137f, 11.9996f, 12.3137f)
            curveTo(11.8029f, 12.3137f, 11.6146f, 12.3404f, 11.4346f, 12.3937f)
            curveTo(11.2546f, 12.4437f, 11.0946f, 12.5237f, 10.9546f, 12.6337f)
            curveTo(10.8179f, 12.7437f, 10.7079f, 12.887f, 10.6246f, 13.0637f)
            curveTo(10.5446f, 13.237f, 10.5046f, 13.447f, 10.5046f, 13.6937f)
            curveTo(10.5046f, 13.8937f, 10.5379f, 14.077f, 10.6046f, 14.2437f)
            curveTo(10.6746f, 14.407f, 10.7729f, 14.5487f, 10.8996f, 14.6687f)
            curveTo(11.0296f, 14.7854f, 11.1863f, 14.877f, 11.3696f, 14.9437f)
            curveTo(11.5563f, 15.007f, 11.7663f, 15.0387f, 11.9996f, 15.0387f)
            close()
            moveTo(11.9996f, 9.1137f)
            curveTo(11.7863f, 9.1137f, 11.5979f, 9.1454f, 11.4346f, 9.2087f)
            curveTo(11.2746f, 9.272f, 11.1396f, 9.3587f, 11.0296f, 9.4687f)
            curveTo(10.9229f, 9.5787f, 10.8413f, 9.707f, 10.7846f, 9.8537f)
            curveTo(10.7313f, 10.0004f, 10.7046f, 10.157f, 10.7046f, 10.3237f)
            curveTo(10.7046f, 10.487f, 10.7279f, 10.6454f, 10.7746f, 10.7987f)
            curveTo(10.8213f, 10.952f, 10.8963f, 11.0887f, 10.9996f, 11.2087f)
            curveTo(11.1029f, 11.3254f, 11.2363f, 11.4204f, 11.3996f, 11.4937f)
            curveTo(11.5663f, 11.5637f, 11.7663f, 11.5987f, 11.9996f, 11.5987f)
            curveTo(12.2329f, 11.5987f, 12.4313f, 11.5637f, 12.5946f, 11.4937f)
            curveTo(12.7613f, 11.4204f, 12.8963f, 11.3254f, 12.9996f, 11.2087f)
            curveTo(13.1029f, 11.0887f, 13.1779f, 10.952f, 13.2246f, 10.7987f)
            curveTo(13.2713f, 10.6454f, 13.2946f, 10.487f, 13.2946f, 10.3237f)
            curveTo(13.2946f, 10.157f, 13.2663f, 10.0004f, 13.2096f, 9.8537f)
            curveTo(13.1563f, 9.707f, 13.0746f, 9.5787f, 12.9646f, 9.4687f)
            curveTo(12.8579f, 9.3587f, 12.7229f, 9.272f, 12.5596f, 9.2087f)
            curveTo(12.3996f, 9.1454f, 12.2129f, 9.1137f, 11.9996f, 9.1137f)
            close()
            moveTo(13.0246f, 11.9337f)
            curveTo(13.4713f, 12.0604f, 13.8146f, 12.272f, 14.0546f, 12.5687f)
            curveTo(14.2946f, 12.8654f, 14.4146f, 13.2454f, 14.4146f, 13.7087f)
            curveTo(14.4146f, 14.022f, 14.3546f, 14.3054f, 14.2346f, 14.5587f)
            curveTo(14.1179f, 14.8087f, 13.9529f, 15.022f, 13.7396f, 15.1987f)
            curveTo(13.5296f, 15.3754f, 13.2763f, 15.512f, 12.9796f, 15.6087f)
            curveTo(12.6829f, 15.702f, 12.3563f, 15.7487f, 11.9996f, 15.7487f)
            curveTo(11.6429f, 15.7487f, 11.3163f, 15.702f, 11.0196f, 15.6087f)
            curveTo(10.7229f, 15.512f, 10.4679f, 15.3754f, 10.2546f, 15.1987f)
            curveTo(10.0413f, 15.022f, 9.8763f, 14.8087f, 9.7596f, 14.5587f)
            curveTo(9.6429f, 14.3054f, 9.5846f, 14.022f, 9.5846f, 13.7087f)
            curveTo(9.5846f, 13.2454f, 9.7046f, 12.8654f, 9.9446f, 12.5687f)
            curveTo(10.1846f, 12.272f, 10.5279f, 12.0604f, 10.9746f, 11.9337f)
            curveTo(10.5979f, 11.7937f, 10.3129f, 11.5854f, 10.1196f, 11.3087f)
            curveTo(9.9296f, 11.0287f, 9.8346f, 10.6954f, 9.8346f, 10.3087f)
            curveTo(9.8346f, 10.0454f, 9.8863f, 9.7987f, 9.9896f, 9.5687f)
            curveTo(10.0929f, 9.3387f, 10.2396f, 9.1387f, 10.4296f, 8.9687f)
            curveTo(10.6196f, 8.7987f, 10.8463f, 8.6654f, 11.1096f, 8.5687f)
            curveTo(11.3763f, 8.472f, 11.6729f, 8.4237f, 11.9996f, 8.4237f)
            curveTo(12.3229f, 8.4237f, 12.6179f, 8.472f, 12.8846f, 8.5687f)
            curveTo(13.1513f, 8.6654f, 13.3796f, 8.7987f, 13.5696f, 8.9687f)
            curveTo(13.7596f, 9.1387f, 13.9063f, 9.3387f, 14.0096f, 9.5687f)
            curveTo(14.1129f, 9.7987f, 14.1646f, 10.0454f, 14.1646f, 10.3087f)
            curveTo(14.1646f, 10.6954f, 14.0679f, 11.0287f, 13.8746f, 11.3087f)
            curveTo(13.6846f, 11.5854f, 13.4013f, 11.7937f, 13.0246f, 11.9337f)
            close()
          }
        }
        .build()
    return _key8!!
  }

private var _key8: ImageVector? = null
