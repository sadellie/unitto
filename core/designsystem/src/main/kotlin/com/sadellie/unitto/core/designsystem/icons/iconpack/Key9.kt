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
val IconPack.Key9: ImageVector
  get() {
    if (_key9 != null) {
      return _key9!!
    }
    _key9 =
      Builder(
          name = "Key9",
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
            moveTo(10.5146f, 10.6087f)
            curveTo(10.5146f, 10.8354f, 10.5463f, 11.0387f, 10.6096f, 11.2187f)
            curveTo(10.6763f, 11.3954f, 10.7696f, 11.5454f, 10.8896f, 11.6687f)
            curveTo(11.0129f, 11.792f, 11.1613f, 11.8854f, 11.3346f, 11.9487f)
            curveTo(11.5113f, 12.012f, 11.7079f, 12.0437f, 11.9246f, 12.0437f)
            curveTo(12.1646f, 12.0437f, 12.3763f, 12.0054f, 12.5596f, 11.9287f)
            curveTo(12.7463f, 11.8487f, 12.9029f, 11.7437f, 13.0296f, 11.6137f)
            curveTo(13.1563f, 11.4837f, 13.2529f, 11.3337f, 13.3196f, 11.1637f)
            curveTo(13.3863f, 10.9937f, 13.4196f, 10.817f, 13.4196f, 10.6337f)
            curveTo(13.4196f, 10.4104f, 13.3829f, 10.2087f, 13.3096f, 10.0287f)
            curveTo(13.2396f, 9.8454f, 13.1413f, 9.6904f, 13.0146f, 9.5637f)
            curveTo(12.8879f, 9.437f, 12.7363f, 9.3404f, 12.5596f, 9.2737f)
            curveTo(12.3863f, 9.2037f, 12.1946f, 9.1687f, 11.9846f, 9.1687f)
            curveTo(11.7646f, 9.1687f, 11.5646f, 9.2054f, 11.3846f, 9.2787f)
            curveTo(11.2046f, 9.3487f, 11.0496f, 9.447f, 10.9196f, 9.5737f)
            curveTo(10.7896f, 9.7004f, 10.6896f, 9.852f, 10.6196f, 10.0287f)
            curveTo(10.5496f, 10.2054f, 10.5146f, 10.3987f, 10.5146f, 10.6087f)
            close()
            moveTo(12.5946f, 12.8137f)
            curveTo(12.6679f, 12.717f, 12.7363f, 12.6254f, 12.7996f, 12.5387f)
            curveTo(12.8629f, 12.452f, 12.9229f, 12.3654f, 12.9796f, 12.2787f)
            curveTo(12.7963f, 12.4254f, 12.5896f, 12.537f, 12.3596f, 12.6137f)
            curveTo(12.1296f, 12.6904f, 11.8863f, 12.7287f, 11.6296f, 12.7287f)
            curveTo(11.3596f, 12.7287f, 11.1029f, 12.6837f, 10.8596f, 12.5937f)
            curveTo(10.6196f, 12.5037f, 10.4079f, 12.372f, 10.2246f, 12.1987f)
            curveTo(10.0413f, 12.022f, 9.8946f, 11.807f, 9.7846f, 11.5537f)
            curveTo(9.6779f, 11.297f, 9.6246f, 11.0037f, 9.6246f, 10.6737f)
            curveTo(9.6246f, 10.3604f, 9.6829f, 10.067f, 9.7996f, 9.7937f)
            curveTo(9.9163f, 9.5204f, 10.0796f, 9.282f, 10.2896f, 9.0787f)
            curveTo(10.4996f, 8.8754f, 10.7496f, 8.7154f, 11.0396f, 8.5987f)
            curveTo(11.3296f, 8.482f, 11.6479f, 8.4237f, 11.9946f, 8.4237f)
            curveTo(12.3379f, 8.4237f, 12.6496f, 8.4804f, 12.9296f, 8.5937f)
            curveTo(13.2096f, 8.707f, 13.4496f, 8.8654f, 13.6496f, 9.0687f)
            curveTo(13.8496f, 9.272f, 14.0029f, 9.5154f, 14.1096f, 9.7987f)
            curveTo(14.2196f, 10.082f, 14.2746f, 10.3954f, 14.2746f, 10.7387f)
            curveTo(14.2746f, 10.9454f, 14.2546f, 11.142f, 14.2146f, 11.3287f)
            curveTo(14.1779f, 11.512f, 14.1229f, 11.6937f, 14.0496f, 11.8737f)
            curveTo(13.9796f, 12.0504f, 13.8929f, 12.227f, 13.7896f, 12.4037f)
            curveTo(13.6863f, 12.577f, 13.5696f, 12.7554f, 13.4396f, 12.9387f)
            lineTo(11.6946f, 15.4587f)
            curveTo(11.6513f, 15.522f, 11.5896f, 15.5737f, 11.5096f, 15.6137f)
            curveTo(11.4296f, 15.6504f, 11.3379f, 15.6687f, 11.2346f, 15.6687f)
            horizontalLineTo(10.4146f)
            lineTo(12.5946f, 12.8137f)
            close()
          }
        }
        .build()
    return _key9!!
  }

private var _key9: ImageVector? = null
