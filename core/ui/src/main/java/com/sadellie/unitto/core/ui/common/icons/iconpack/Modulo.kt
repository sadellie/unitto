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
val IconPack.Modulo: ImageVector
    get() {
        if (_modulo != null) {
            return _modulo!!
        }
        _modulo = Builder(name = "Modulo", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(12.7496f, 11.2687f)
                horizontalLineTo(11.5146f)
                lineTo(11.1896f, 12.8987f)
                horizontalLineTo(12.4246f)
                lineTo(12.7496f, 11.2687f)
                close()
                moveTo(14.6396f, 10.6137f)
                lineTo(14.5746f, 10.9887f)
                curveTo(14.5579f, 11.0854f, 14.5179f, 11.157f, 14.4546f, 11.2037f)
                curveTo(14.3946f, 11.247f, 14.3013f, 11.2687f, 14.1746f, 11.2687f)
                horizontalLineTo(13.4846f)
                lineTo(13.1596f, 12.8987f)
                horizontalLineTo(14.0546f)
                curveTo(14.1513f, 12.8987f, 14.2196f, 12.927f, 14.2596f, 12.9837f)
                curveTo(14.3029f, 13.037f, 14.3146f, 13.132f, 14.2946f, 13.2687f)
                lineTo(14.2496f, 13.5537f)
                horizontalLineTo(13.0646f)
                lineTo(12.6446f, 15.6687f)
                horizontalLineTo(12.2396f)
                curveTo(12.1963f, 15.6687f, 12.1563f, 15.6587f, 12.1196f, 15.6387f)
                curveTo(12.0829f, 15.6187f, 12.0513f, 15.592f, 12.0246f, 15.5587f)
                curveTo(12.0013f, 15.5254f, 11.9846f, 15.4854f, 11.9746f, 15.4387f)
                curveTo(11.9646f, 15.392f, 11.9646f, 15.3404f, 11.9746f, 15.2837f)
                lineTo(12.3296f, 13.5537f)
                horizontalLineTo(11.0946f)
                lineTo(10.7396f, 15.3287f)
                curveTo(10.7196f, 15.452f, 10.6713f, 15.5404f, 10.5946f, 15.5937f)
                curveTo(10.5179f, 15.6437f, 10.4313f, 15.6687f, 10.3346f, 15.6687f)
                horizontalLineTo(9.9346f)
                lineTo(10.3596f, 13.5537f)
                horizontalLineTo(9.6296f)
                curveTo(9.5363f, 13.5537f, 9.4679f, 13.527f, 9.4246f, 13.4737f)
                curveTo(9.3846f, 13.417f, 9.3746f, 13.3204f, 9.3946f, 13.1837f)
                lineTo(9.4346f, 12.8987f)
                horizontalLineTo(10.4546f)
                lineTo(10.7796f, 11.2687f)
                horizontalLineTo(9.6196f)
                lineTo(9.6846f, 10.8987f)
                curveTo(9.7013f, 10.802f, 9.7396f, 10.7304f, 9.7996f, 10.6837f)
                curveTo(9.8629f, 10.637f, 9.9579f, 10.6137f, 10.0846f, 10.6137f)
                horizontalLineTo(10.8746f)
                lineTo(11.2346f, 8.8237f)
                curveTo(11.2546f, 8.7237f, 11.2996f, 8.6454f, 11.3696f, 8.5887f)
                curveTo(11.4429f, 8.532f, 11.5296f, 8.5037f, 11.6296f, 8.5037f)
                horizontalLineTo(12.0296f)
                lineTo(11.6096f, 10.6137f)
                horizontalLineTo(12.8446f)
                lineTo(13.2646f, 8.5037f)
                horizontalLineTo(13.6596f)
                curveTo(13.7563f, 8.5037f, 13.8313f, 8.5354f, 13.8846f, 8.5987f)
                curveTo(13.9413f, 8.662f, 13.9613f, 8.742f, 13.9446f, 8.8387f)
                lineTo(13.5796f, 10.6137f)
                horizontalLineTo(14.6396f)
                close()
            }
        }
        .build()
        return _modulo!!
    }

private var _modulo: ImageVector? = null
