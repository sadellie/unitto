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
val IconPack.Comma: ImageVector
    get() {
        if (_comma != null) {
            return _comma!!
        }
        _comma = Builder(name = "Comma", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(11.4661f, 13.3917f)
                curveTo(11.4371f, 13.3663f, 11.4171f, 13.3427f, 11.4062f, 13.3209f)
                curveTo(11.3953f, 13.2991f, 11.3899f, 13.2719f, 11.3899f, 13.2392f)
                curveTo(11.3899f, 13.2138f, 11.3971f, 13.1883f, 11.4116f, 13.1629f)
                curveTo(11.4298f, 13.1375f, 11.4498f, 13.1139f, 11.4716f, 13.0921f)
                curveTo(11.5079f, 13.0521f, 11.5533f, 12.9995f, 11.6078f, 12.9341f)
                curveTo(11.6659f, 12.8687f, 11.724f, 12.7906f, 11.7821f, 12.6998f)
                curveTo(11.8402f, 12.6127f, 11.8929f, 12.5146f, 11.9401f, 12.4056f)
                curveTo(11.9909f, 12.3003f, 12.0273f, 12.1877f, 12.0491f, 12.0679f)
                curveTo(12.0382f, 12.0715f, 12.0254f, 12.0733f, 12.0109f, 12.0733f)
                curveTo(12.0f, 12.0733f, 11.9891f, 12.0733f, 11.9782f, 12.0733f)
                curveTo(11.7894f, 12.0733f, 11.635f, 12.0116f, 11.5152f, 11.8881f)
                curveTo(11.3989f, 11.761f, 11.3408f, 11.5993f, 11.3408f, 11.4032f)
                curveTo(11.3408f, 11.2325f, 11.3989f, 11.0872f, 11.5152f, 10.9674f)
                curveTo(11.635f, 10.8475f, 11.793f, 10.7876f, 11.9891f, 10.7876f)
                curveTo(12.0981f, 10.7876f, 12.1943f, 10.8076f, 12.2779f, 10.8475f)
                curveTo(12.3614f, 10.8875f, 12.4304f, 10.9438f, 12.4849f, 11.0164f)
                curveTo(12.543f, 11.0854f, 12.5866f, 11.1671f, 12.6156f, 11.2616f)
                curveTo(12.6447f, 11.3524f, 12.6592f, 11.4523f, 12.6592f, 11.5612f)
                curveTo(12.6592f, 11.7246f, 12.6356f, 11.8954f, 12.5884f, 12.0733f)
                curveTo(12.5448f, 12.2477f, 12.4776f, 12.4202f, 12.3868f, 12.5909f)
                curveTo(12.2997f, 12.7652f, 12.1925f, 12.9341f, 12.0654f, 13.0975f)
                curveTo(11.9383f, 13.261f, 11.793f, 13.4117f, 11.6296f, 13.5497f)
                lineTo(11.4661f, 13.3917f)
                close()
            }
        }
        .build()
        return _comma!!
    }

private var _comma: ImageVector? = null
