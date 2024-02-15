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
val IconPack.Percent: ImageVector
    get() {
        if (_percent != null) {
            return _percent!!
        }
        _percent = Builder(name = "Percent", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(14.1468f, 8.6672f)
                lineTo(14.8026f, 9.1829f)
                lineTo(9.8561f, 15.6701f)
                lineTo(9.2003f, 15.1545f)
                lineTo(14.1468f, 8.6672f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(11.6513f, 10.2429f)
                curveTo(11.6513f, 11.1131f, 10.9459f, 11.8185f, 10.0757f, 11.8185f)
                curveTo(9.2054f, 11.8185f, 8.5f, 11.1131f, 8.5f, 10.2429f)
                curveTo(8.5f, 9.3727f, 9.2054f, 8.6672f, 10.0757f, 8.6672f)
                curveTo(10.9459f, 8.6672f, 11.6513f, 9.3727f, 11.6513f, 10.2429f)
                close()
                moveTo(9.0515f, 10.2429f)
                curveTo(9.0515f, 10.8085f, 9.51f, 11.2671f, 10.0757f, 11.2671f)
                curveTo(10.6413f, 11.2671f, 11.0998f, 10.8085f, 11.0998f, 10.2429f)
                curveTo(11.0998f, 9.6772f, 10.6413f, 9.2187f, 10.0757f, 9.2187f)
                curveTo(9.51f, 9.2187f, 9.0515f, 9.6772f, 9.0515f, 10.2429f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(15.5f, 14.0945f)
                curveTo(15.5f, 14.9647f, 14.7946f, 15.6701f, 13.9243f, 15.6701f)
                curveTo(13.0541f, 15.6701f, 12.3487f, 14.9647f, 12.3487f, 14.0945f)
                curveTo(12.3487f, 13.2243f, 13.0541f, 12.5188f, 13.9243f, 12.5188f)
                curveTo(14.7946f, 12.5188f, 15.5f, 13.2243f, 15.5f, 14.0945f)
                close()
                moveTo(12.9002f, 14.0945f)
                curveTo(12.9002f, 14.6601f, 13.3587f, 15.1187f, 13.9243f, 15.1187f)
                curveTo(14.49f, 15.1187f, 14.9485f, 14.6601f, 14.9485f, 14.0945f)
                curveTo(14.9485f, 13.5288f, 14.49f, 13.0703f, 13.9243f, 13.0703f)
                curveTo(13.3587f, 13.0703f, 12.9002f, 13.5288f, 12.9002f, 14.0945f)
                close()
            }
        }
        .build()
        return _percent!!
    }

private var _percent: ImageVector? = null
