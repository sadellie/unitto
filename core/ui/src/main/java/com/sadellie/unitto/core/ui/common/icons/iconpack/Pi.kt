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
val IconPack.Pi: ImageVector
    get() {
        if (_pi != null) {
            return _pi!!
        }
        _pi = Builder(name = "Pi", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp, viewportWidth =
                24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(15.2588f, 11.0737f)
                curveTo(15.2588f, 11.1537f, 15.2338f, 11.222f, 15.1838f, 11.2787f)
                curveTo(15.1372f, 11.332f, 15.0655f, 11.3587f, 14.9688f, 11.3587f)
                horizontalLineTo(14.2338f)
                curveTo(14.2305f, 11.5487f, 14.2272f, 11.7254f, 14.2238f, 11.8887f)
                curveTo(14.2205f, 12.052f, 14.2188f, 12.217f, 14.2188f, 12.3837f)
                curveTo(14.2188f, 12.7804f, 14.2255f, 13.122f, 14.2388f, 13.4087f)
                curveTo(14.2522f, 13.692f, 14.2705f, 13.932f, 14.2938f, 14.1287f)
                curveTo(14.3205f, 14.3254f, 14.3538f, 14.4837f, 14.3938f, 14.6037f)
                curveTo(14.4338f, 14.7237f, 14.4805f, 14.8154f, 14.5338f, 14.8787f)
                curveTo(14.5872f, 14.942f, 14.6472f, 14.9854f, 14.7138f, 15.0087f)
                curveTo(14.7805f, 15.0287f, 14.8555f, 15.0387f, 14.9388f, 15.0387f)
                horizontalLineTo(15.1288f)
                verticalLineTo(15.3987f)
                curveTo(15.1288f, 15.4954f, 15.0722f, 15.5704f, 14.9588f, 15.6237f)
                curveTo(14.8488f, 15.6737f, 14.7088f, 15.6987f, 14.5388f, 15.6987f)
                curveTo(14.3422f, 15.6987f, 14.1688f, 15.6654f, 14.0188f, 15.5987f)
                curveTo(13.8722f, 15.532f, 13.7505f, 15.4287f, 13.6538f, 15.2887f)
                curveTo(13.5572f, 15.1487f, 13.4838f, 14.9687f, 13.4338f, 14.7487f)
                curveTo(13.3872f, 14.5254f, 13.3638f, 14.2587f, 13.3638f, 13.9487f)
                curveTo(13.3638f, 13.7987f, 13.3705f, 13.637f, 13.3838f, 13.4637f)
                curveTo(13.3972f, 13.287f, 13.4122f, 13.0937f, 13.4288f, 12.8837f)
                curveTo(13.4455f, 12.6737f, 13.4622f, 12.4437f, 13.4788f, 12.1937f)
                curveTo(13.4955f, 11.9404f, 13.5088f, 11.662f, 13.5188f, 11.3587f)
                horizontalLineTo(11.6688f)
                curveTo(11.6588f, 11.932f, 11.6338f, 12.4387f, 11.5938f, 12.8787f)
                curveTo(11.5538f, 13.3187f, 11.5022f, 13.7004f, 11.4388f, 14.0237f)
                curveTo(11.3755f, 14.3437f, 11.3005f, 14.612f, 11.2138f, 14.8287f)
                curveTo(11.1305f, 15.0454f, 11.0372f, 15.2187f, 10.9338f, 15.3487f)
                curveTo(10.8338f, 15.4787f, 10.7238f, 15.572f, 10.6038f, 15.6287f)
                curveTo(10.4838f, 15.682f, 10.3588f, 15.7087f, 10.2288f, 15.7087f)
                curveTo(10.1888f, 15.7087f, 10.1472f, 15.7054f, 10.1038f, 15.6987f)
                curveTo(10.0605f, 15.692f, 10.0188f, 15.682f, 9.9788f, 15.6687f)
                curveTo(9.9422f, 15.652f, 9.9122f, 15.632f, 9.8888f, 15.6087f)
                curveTo(9.8655f, 15.5854f, 9.8538f, 15.5587f, 9.8538f, 15.5287f)
                verticalLineTo(14.9237f)
                horizontalLineTo(9.9538f)
                curveTo(10.0538f, 14.9237f, 10.1555f, 14.8604f, 10.2588f, 14.7337f)
                curveTo(10.3622f, 14.6037f, 10.4572f, 14.3987f, 10.5438f, 14.1187f)
                curveTo(10.6305f, 13.8354f, 10.7055f, 13.467f, 10.7688f, 13.0137f)
                curveTo(10.8322f, 12.5604f, 10.8722f, 12.0087f, 10.8888f, 11.3587f)
                horizontalLineTo(9.7938f)
                verticalLineTo(10.9837f)
                curveTo(9.7938f, 10.947f, 9.8005f, 10.9104f, 9.8138f, 10.8737f)
                curveTo(9.8305f, 10.8337f, 9.8538f, 10.797f, 9.8838f, 10.7637f)
                curveTo(9.9138f, 10.7304f, 9.9488f, 10.7037f, 9.9888f, 10.6837f)
                curveTo(10.0322f, 10.6637f, 10.0822f, 10.6537f, 10.1388f, 10.6537f)
                horizontalLineTo(15.2588f)
                verticalLineTo(11.0737f)
                close()
            }
        }
        .build()
        return _pi!!
    }

private var _pi: ImageVector? = null
