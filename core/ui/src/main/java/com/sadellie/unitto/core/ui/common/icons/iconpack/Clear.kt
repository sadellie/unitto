/*
 * Unitto is a unit converter for Android
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

package com.sadellie.unitto.core.ui.common.icons.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.ui.common.icons.IconPack

@Suppress("UnusedReceiverParameter")
val IconPack.Clear: ImageVector
    get() {
        if (_clear != null) {
            return _clear!!
        }
        _clear = Builder(name = "Clear", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(10.8391f, 13.0087f)
                lineTo(9.7141f, 10.0937f)
                curveTo(9.6808f, 10.007f, 9.6458f, 9.907f, 9.6091f, 9.7937f)
                curveTo(9.5725f, 9.677f, 9.5375f, 9.5537f, 9.5041f, 9.4237f)
                curveTo(9.4341f, 9.6937f, 9.3625f, 9.9187f, 9.2891f, 10.0987f)
                lineTo(8.1641f, 13.0087f)
                horizontalLineTo(10.8391f)
                close()
                moveTo(12.8591f, 15.6687f)
                horizontalLineTo(12.1091f)
                curveTo(12.0225f, 15.6687f, 11.9525f, 15.647f, 11.8991f, 15.6037f)
                curveTo(11.8458f, 15.5604f, 11.8058f, 15.5054f, 11.7791f, 15.4387f)
                lineTo(11.1091f, 13.7087f)
                horizontalLineTo(7.8941f)
                lineTo(7.2241f, 15.4387f)
                curveTo(7.2041f, 15.4987f, 7.1658f, 15.552f, 7.1091f, 15.5987f)
                curveTo(7.0525f, 15.6454f, 6.9825f, 15.6687f, 6.8991f, 15.6687f)
                horizontalLineTo(6.1491f)
                lineTo(9.0141f, 8.5037f)
                horizontalLineTo(9.9941f)
                lineTo(12.8591f, 15.6687f)
                close()
                moveTo(18.1462f, 14.1887f)
                curveTo(18.1995f, 14.1887f, 18.2462f, 14.2104f, 18.2862f, 14.2537f)
                lineTo(18.6712f, 14.6687f)
                curveTo(18.3778f, 15.0087f, 18.0212f, 15.2737f, 17.6012f, 15.4637f)
                curveTo(17.1845f, 15.6537f, 16.6795f, 15.7487f, 16.0862f, 15.7487f)
                curveTo(15.5728f, 15.7487f, 15.1062f, 15.6604f, 14.6862f, 15.4837f)
                curveTo(14.2662f, 15.3037f, 13.9078f, 15.0537f, 13.6112f, 14.7337f)
                curveTo(13.3145f, 14.4104f, 13.0845f, 14.0237f, 12.9212f, 13.5737f)
                curveTo(12.7578f, 13.1237f, 12.6762f, 12.6287f, 12.6762f, 12.0887f)
                curveTo(12.6762f, 11.5487f, 12.7612f, 11.0537f, 12.9312f, 10.6037f)
                curveTo(13.1012f, 10.1537f, 13.3395f, 9.767f, 13.6462f, 9.4437f)
                curveTo(13.9562f, 9.1204f, 14.3262f, 8.8704f, 14.7562f, 8.6937f)
                curveTo(15.1862f, 8.5137f, 15.6612f, 8.4237f, 16.1812f, 8.4237f)
                curveTo(16.6912f, 8.4237f, 17.1412f, 8.5054f, 17.5312f, 8.6687f)
                curveTo(17.9212f, 8.832f, 18.2645f, 9.0537f, 18.5612f, 9.3337f)
                lineTo(18.2412f, 9.7787f)
                curveTo(18.2212f, 9.812f, 18.1945f, 9.8404f, 18.1612f, 9.8637f)
                curveTo(18.1312f, 9.8837f, 18.0895f, 9.8937f, 18.0362f, 9.8937f)
                curveTo(17.9762f, 9.8937f, 17.9028f, 9.862f, 17.8162f, 9.7987f)
                curveTo(17.7295f, 9.732f, 17.6162f, 9.6587f, 17.4762f, 9.5787f)
                curveTo(17.3362f, 9.4987f, 17.1612f, 9.427f, 16.9512f, 9.3637f)
                curveTo(16.7412f, 9.297f, 16.4828f, 9.2637f, 16.1762f, 9.2637f)
                curveTo(15.8062f, 9.2637f, 15.4678f, 9.3287f, 15.1612f, 9.4587f)
                curveTo(14.8545f, 9.5854f, 14.5895f, 9.7704f, 14.3662f, 10.0137f)
                curveTo(14.1462f, 10.257f, 13.9745f, 10.5537f, 13.8512f, 10.9037f)
                curveTo(13.7278f, 11.2537f, 13.6662f, 11.6487f, 13.6662f, 12.0887f)
                curveTo(13.6662f, 12.5354f, 13.7295f, 12.9337f, 13.8562f, 13.2837f)
                curveTo(13.9862f, 13.6337f, 14.1612f, 13.9304f, 14.3812f, 14.1737f)
                curveTo(14.6045f, 14.4137f, 14.8662f, 14.597f, 15.1662f, 14.7237f)
                curveTo(15.4695f, 14.8504f, 15.7962f, 14.9137f, 16.1462f, 14.9137f)
                curveTo(16.3595f, 14.9137f, 16.5512f, 14.902f, 16.7212f, 14.8787f)
                curveTo(16.8945f, 14.852f, 17.0528f, 14.812f, 17.1962f, 14.7587f)
                curveTo(17.3428f, 14.7054f, 17.4778f, 14.6387f, 17.6012f, 14.5587f)
                curveTo(17.7278f, 14.4754f, 17.8528f, 14.377f, 17.9762f, 14.2637f)
                curveTo(18.0328f, 14.2137f, 18.0895f, 14.1887f, 18.1462f, 14.1887f)
                close()
            }
        }
        .build()
        return _clear!!
    }

private var _clear: ImageVector? = null
