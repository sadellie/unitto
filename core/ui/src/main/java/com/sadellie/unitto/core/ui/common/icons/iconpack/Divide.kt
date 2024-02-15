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
val IconPack.Divide: ImageVector
    get() {
        if (_divide != null) {
            return _divide!!
        }
        _divide = Builder(name = "Divide", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(11.3341f, 9.0057f)
                curveTo(11.3341f, 8.9134f, 11.35f, 8.8275f, 11.3818f, 8.7479f)
                curveTo(11.4168f, 8.6652f, 11.463f, 8.5936f, 11.5203f, 8.5331f)
                curveTo(11.5807f, 8.4726f, 11.6508f, 8.4249f, 11.7303f, 8.3899f)
                curveTo(11.8131f, 8.3549f, 11.9006f, 8.3374f, 11.9929f, 8.3374f)
                curveTo(12.0851f, 8.3374f, 12.1711f, 8.3549f, 12.2506f, 8.3899f)
                curveTo(12.3334f, 8.4249f, 12.405f, 8.4726f, 12.4654f, 8.5331f)
                curveTo(12.5259f, 8.5936f, 12.5736f, 8.6652f, 12.6087f, 8.7479f)
                curveTo(12.6437f, 8.8275f, 12.6612f, 8.9134f, 12.6612f, 9.0057f)
                curveTo(12.6612f, 9.1012f, 12.6437f, 9.1887f, 12.6087f, 9.2683f)
                curveTo(12.5736f, 9.3478f, 12.5259f, 9.4178f, 12.4654f, 9.4783f)
                curveTo(12.405f, 9.5388f, 12.3334f, 9.5849f, 12.2506f, 9.6167f)
                curveTo(12.1711f, 9.6517f, 12.0851f, 9.6692f, 11.9929f, 9.6692f)
                curveTo(11.9006f, 9.6692f, 11.8131f, 9.6517f, 11.7303f, 9.6167f)
                curveTo(11.6508f, 9.5849f, 11.5807f, 9.5388f, 11.5203f, 9.4783f)
                curveTo(11.463f, 9.4178f, 11.4168f, 9.3478f, 11.3818f, 9.2683f)
                curveTo(11.35f, 9.1887f, 11.3341f, 9.1012f, 11.3341f, 9.0057f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(11.3341f, 14.9989f)
                curveTo(11.3341f, 14.9066f, 11.35f, 14.8207f, 11.3818f, 14.7411f)
                curveTo(11.4168f, 14.6584f, 11.463f, 14.5868f, 11.5203f, 14.5263f)
                curveTo(11.5807f, 14.4658f, 11.6508f, 14.4181f, 11.7303f, 14.3831f)
                curveTo(11.8131f, 14.3481f, 11.9006f, 14.3306f, 11.9929f, 14.3306f)
                curveTo(12.0851f, 14.3306f, 12.1711f, 14.3481f, 12.2506f, 14.3831f)
                curveTo(12.3334f, 14.4181f, 12.405f, 14.4658f, 12.4654f, 14.5263f)
                curveTo(12.5259f, 14.5868f, 12.5736f, 14.6584f, 12.6087f, 14.7411f)
                curveTo(12.6437f, 14.8207f, 12.6612f, 14.9066f, 12.6612f, 14.9989f)
                curveTo(12.6612f, 15.0943f, 12.6437f, 15.1819f, 12.6087f, 15.2614f)
                curveTo(12.5736f, 15.341f, 12.5259f, 15.411f, 12.4654f, 15.4715f)
                curveTo(12.405f, 15.5319f, 12.3334f, 15.5781f, 12.2506f, 15.6099f)
                curveTo(12.1711f, 15.6449f, 12.0851f, 15.6624f, 11.9929f, 15.6624f)
                curveTo(11.9006f, 15.6624f, 11.8131f, 15.6449f, 11.7303f, 15.6099f)
                curveTo(11.6508f, 15.5781f, 11.5807f, 15.5319f, 11.5203f, 15.4715f)
                curveTo(11.463f, 15.411f, 11.4168f, 15.341f, 11.3818f, 15.2614f)
                curveTo(11.35f, 15.1819f, 11.3341f, 15.0943f, 11.3341f, 14.9989f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(8.1948f, 11.3816f)
                horizontalLineTo(15.8052f)
                verticalLineTo(12.3804f)
                horizontalLineTo(8.1948f)
                verticalLineTo(11.3816f)
                close()
            }
        }
        .build()
        return _divide!!
    }

private var _divide: ImageVector? = null
