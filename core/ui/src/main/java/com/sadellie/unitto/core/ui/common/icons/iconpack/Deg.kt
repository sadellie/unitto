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
val IconPack.Deg: ImageVector
    get() {
        if (_deg != null) {
            return _deg!!
        }
        _deg = Builder(name = "Deg", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp, viewportWidth
                = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(9.0613f, 12.0887f)
                curveTo(9.0613f, 12.6254f, 8.9763f, 13.1137f, 8.8063f, 13.5537f)
                curveTo(8.6363f, 13.9937f, 8.3963f, 14.3704f, 8.0863f, 14.6837f)
                curveTo(7.7763f, 14.997f, 7.403f, 15.2404f, 6.9663f, 15.4137f)
                curveTo(6.533f, 15.5837f, 6.053f, 15.6687f, 5.5263f, 15.6687f)
                horizontalLineTo(2.8513f)
                verticalLineTo(8.5037f)
                horizontalLineTo(5.5263f)
                curveTo(6.053f, 8.5037f, 6.533f, 8.5904f, 6.9663f, 8.7637f)
                curveTo(7.403f, 8.9337f, 7.7763f, 9.177f, 8.0863f, 9.4937f)
                curveTo(8.3963f, 9.807f, 8.6363f, 10.1837f, 8.8063f, 10.6237f)
                curveTo(8.9763f, 11.0637f, 9.0613f, 11.552f, 9.0613f, 12.0887f)
                close()
                moveTo(8.0663f, 12.0887f)
                curveTo(8.0663f, 11.6487f, 8.0063f, 11.2554f, 7.8863f, 10.9087f)
                curveTo(7.7663f, 10.562f, 7.5963f, 10.2687f, 7.3763f, 10.0287f)
                curveTo(7.1563f, 9.7887f, 6.8897f, 9.6054f, 6.5763f, 9.4787f)
                curveTo(6.263f, 9.352f, 5.913f, 9.2887f, 5.5263f, 9.2887f)
                horizontalLineTo(3.8213f)
                verticalLineTo(14.8837f)
                horizontalLineTo(5.5263f)
                curveTo(5.913f, 14.8837f, 6.263f, 14.8204f, 6.5763f, 14.6937f)
                curveTo(6.8897f, 14.567f, 7.1563f, 14.3854f, 7.3763f, 14.1487f)
                curveTo(7.5963f, 13.9087f, 7.7663f, 13.6154f, 7.8863f, 13.2687f)
                curveTo(8.0063f, 12.922f, 8.0663f, 12.5287f, 8.0663f, 12.0887f)
                close()
                moveTo(14.6788f, 14.8787f)
                lineTo(14.6738f, 15.6687f)
                horizontalLineTo(10.2588f)
                verticalLineTo(8.5037f)
                horizontalLineTo(14.6738f)
                verticalLineTo(9.2937f)
                horizontalLineTo(11.2288f)
                verticalLineTo(11.6737f)
                horizontalLineTo(14.0188f)
                verticalLineTo(12.4337f)
                horizontalLineTo(11.2288f)
                verticalLineTo(14.8787f)
                horizontalLineTo(14.6788f)
                close()
                moveTo(21.494f, 12.2237f)
                verticalLineTo(14.9737f)
                curveTo(21.134f, 15.2337f, 20.749f, 15.4287f, 20.339f, 15.5587f)
                curveTo(19.9323f, 15.6854f, 19.4857f, 15.7487f, 18.999f, 15.7487f)
                curveTo(18.4223f, 15.7487f, 17.9023f, 15.6604f, 17.439f, 15.4837f)
                curveTo(16.9757f, 15.3037f, 16.579f, 15.0537f, 16.249f, 14.7337f)
                curveTo(15.9223f, 14.4104f, 15.6707f, 14.0237f, 15.494f, 13.5737f)
                curveTo(15.3173f, 13.1237f, 15.229f, 12.6287f, 15.229f, 12.0887f)
                curveTo(15.229f, 11.542f, 15.314f, 11.0437f, 15.484f, 10.5937f)
                curveTo(15.6573f, 10.1437f, 15.9023f, 9.7587f, 16.219f, 9.4387f)
                curveTo(16.5357f, 9.1154f, 16.9207f, 8.8654f, 17.374f, 8.6887f)
                curveTo(17.8273f, 8.512f, 18.334f, 8.4237f, 18.894f, 8.4237f)
                curveTo(19.1773f, 8.4237f, 19.4407f, 8.4454f, 19.684f, 8.4887f)
                curveTo(19.9273f, 8.5287f, 20.1523f, 8.5887f, 20.359f, 8.6687f)
                curveTo(20.5657f, 8.7454f, 20.7573f, 8.8404f, 20.934f, 8.9537f)
                curveTo(21.1107f, 9.0637f, 21.2757f, 9.1887f, 21.429f, 9.3287f)
                lineTo(21.154f, 9.7687f)
                curveTo(21.1107f, 9.8354f, 21.054f, 9.8787f, 20.984f, 9.8987f)
                curveTo(20.9173f, 9.9154f, 20.8423f, 9.8987f, 20.759f, 9.8487f)
                curveTo(20.679f, 9.802f, 20.5873f, 9.7454f, 20.484f, 9.6787f)
                curveTo(20.3807f, 9.612f, 20.254f, 9.5487f, 20.104f, 9.4887f)
                curveTo(19.9573f, 9.4254f, 19.7823f, 9.372f, 19.579f, 9.3287f)
                curveTo(19.379f, 9.2854f, 19.1407f, 9.2637f, 18.864f, 9.2637f)
                curveTo(18.4607f, 9.2637f, 18.0957f, 9.3304f, 17.769f, 9.4637f)
                curveTo(17.4423f, 9.5937f, 17.164f, 9.782f, 16.934f, 10.0287f)
                curveTo(16.704f, 10.272f, 16.5273f, 10.5687f, 16.404f, 10.9187f)
                curveTo(16.2807f, 11.2654f, 16.219f, 11.6554f, 16.219f, 12.0887f)
                curveTo(16.219f, 12.5387f, 16.2823f, 12.942f, 16.409f, 13.2987f)
                curveTo(16.539f, 13.652f, 16.7223f, 13.9537f, 16.959f, 14.2037f)
                curveTo(17.199f, 14.4504f, 17.489f, 14.6387f, 17.829f, 14.7687f)
                curveTo(18.169f, 14.8987f, 18.5507f, 14.9637f, 18.974f, 14.9637f)
                curveTo(19.3073f, 14.9637f, 19.6023f, 14.927f, 19.859f, 14.8537f)
                curveTo(20.119f, 14.777f, 20.3723f, 14.672f, 20.619f, 14.5387f)
                verticalLineTo(12.9637f)
                horizontalLineTo(19.504f)
                curveTo(19.4407f, 12.9637f, 19.389f, 12.9454f, 19.349f, 12.9087f)
                curveTo(19.3123f, 12.872f, 19.294f, 12.827f, 19.294f, 12.7737f)
                verticalLineTo(12.2237f)
                horizontalLineTo(21.494f)
                close()
            }
        }
        .build()
        return _deg!!
    }

private var _deg: ImageVector? = null
