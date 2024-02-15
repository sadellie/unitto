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

@file:Suppress("ktlint:standard:property-naming")

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
val IconPack.Key2: ImageVector
    get() {
        if (_key2 != null) {
            return _key2!!
        }
        _key2 = Builder(name = "Key2", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(14.0346f, 14.8237f)
                curveTo(14.1313f, 14.8237f, 14.2079f, 14.852f, 14.2646f, 14.9087f)
                curveTo(14.3213f, 14.9654f, 14.3496f, 15.0387f, 14.3496f, 15.1287f)
                verticalLineTo(15.6687f)
                horizontalLineTo(9.5696f)
                verticalLineTo(15.3637f)
                curveTo(9.5696f, 15.3004f, 9.5829f, 15.2354f, 9.6096f, 15.1687f)
                curveTo(9.6363f, 15.102f, 9.6779f, 15.0404f, 9.7346f, 14.9837f)
                lineTo(12.0296f, 12.6787f)
                curveTo(12.2196f, 12.4854f, 12.3929f, 12.3004f, 12.5496f, 12.1237f)
                curveTo(12.7063f, 11.9437f, 12.8396f, 11.7637f, 12.9496f, 11.5837f)
                curveTo(13.0596f, 11.4037f, 13.1446f, 11.222f, 13.2046f, 11.0387f)
                curveTo(13.2646f, 10.852f, 13.2946f, 10.6537f, 13.2946f, 10.4437f)
                curveTo(13.2946f, 10.2337f, 13.2613f, 10.0504f, 13.1946f, 9.8937f)
                curveTo(13.1279f, 9.7337f, 13.0363f, 9.602f, 12.9196f, 9.4987f)
                curveTo(12.8063f, 9.3954f, 12.6713f, 9.3187f, 12.5146f, 9.2687f)
                curveTo(12.3579f, 9.2154f, 12.1896f, 9.1887f, 12.0096f, 9.1887f)
                curveTo(11.8263f, 9.1887f, 11.6579f, 9.2154f, 11.5046f, 9.2687f)
                curveTo(11.3513f, 9.322f, 11.2146f, 9.397f, 11.0946f, 9.4937f)
                curveTo(10.9779f, 9.587f, 10.8796f, 9.6987f, 10.7996f, 9.8287f)
                curveTo(10.7196f, 9.9587f, 10.6629f, 10.102f, 10.6296f, 10.2587f)
                curveTo(10.5896f, 10.3754f, 10.5346f, 10.4537f, 10.4646f, 10.4937f)
                curveTo(10.3979f, 10.5304f, 10.3029f, 10.5404f, 10.1796f, 10.5237f)
                lineTo(9.7146f, 10.4437f)
                curveTo(9.7613f, 10.117f, 9.8513f, 9.8287f, 9.9846f, 9.5787f)
                curveTo(10.1213f, 9.3254f, 10.2913f, 9.1137f, 10.4946f, 8.9437f)
                curveTo(10.7013f, 8.7737f, 10.9363f, 8.6454f, 11.1996f, 8.5587f)
                curveTo(11.4629f, 8.4687f, 11.7479f, 8.4237f, 12.0546f, 8.4237f)
                curveTo(12.3579f, 8.4237f, 12.6413f, 8.4687f, 12.9046f, 8.5587f)
                curveTo(13.1679f, 8.6487f, 13.3963f, 8.7804f, 13.5896f, 8.9537f)
                curveTo(13.7829f, 9.1237f, 13.9346f, 9.332f, 14.0446f, 9.5787f)
                curveTo(14.1546f, 9.8254f, 14.2096f, 10.1054f, 14.2096f, 10.4187f)
                curveTo(14.2096f, 10.6854f, 14.1696f, 10.9337f, 14.0896f, 11.1637f)
                curveTo(14.0096f, 11.3904f, 13.9013f, 11.607f, 13.7646f, 11.8137f)
                curveTo(13.6279f, 12.0204f, 13.4696f, 12.222f, 13.2896f, 12.4187f)
                curveTo(13.1129f, 12.6154f, 12.9246f, 12.8137f, 12.7246f, 13.0137f)
                lineTo(10.8346f, 14.9437f)
                curveTo(10.9679f, 14.907f, 11.1029f, 14.8787f, 11.2396f, 14.8587f)
                curveTo(11.3763f, 14.8354f, 11.5079f, 14.8237f, 11.6346f, 14.8237f)
                horizontalLineTo(14.0346f)
                close()
            }
        }
        .build()
        return _key2!!
    }

private var _key2: ImageVector? = null
