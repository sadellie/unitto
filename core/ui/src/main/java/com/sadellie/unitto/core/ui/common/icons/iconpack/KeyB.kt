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
val IconPack.KeyB: ImageVector
    get() {
        if (_keyB != null) {
            return _keyB!!
        }
        _keyB = Builder(name = "KeyB", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(12.1976f, 14.8987f)
                curveTo(12.4742f, 14.8987f, 12.7126f, 14.867f, 12.9126f, 14.8037f)
                curveTo(13.1126f, 14.7404f, 13.2759f, 14.652f, 13.4026f, 14.5387f)
                curveTo(13.5326f, 14.422f, 13.6276f, 14.2854f, 13.6876f, 14.1287f)
                curveTo(13.7476f, 13.9687f, 13.7776f, 13.7937f, 13.7776f, 13.6037f)
                curveTo(13.7776f, 13.2337f, 13.6459f, 12.942f, 13.3826f, 12.7287f)
                curveTo(13.1192f, 12.512f, 12.7242f, 12.4037f, 12.1976f, 12.4037f)
                horizontalLineTo(10.6476f)
                verticalLineTo(14.8987f)
                horizontalLineTo(12.1976f)
                close()
                moveTo(10.6476f, 9.2687f)
                verticalLineTo(11.7137f)
                horizontalLineTo(11.9276f)
                curveTo(12.2009f, 11.7137f, 12.4376f, 11.6837f, 12.6376f, 11.6237f)
                curveTo(12.8409f, 11.5637f, 13.0076f, 11.4804f, 13.1376f, 11.3737f)
                curveTo(13.2709f, 11.267f, 13.3692f, 11.1387f, 13.4326f, 10.9887f)
                curveTo(13.4959f, 10.8354f, 13.5276f, 10.6687f, 13.5276f, 10.4887f)
                curveTo(13.5276f, 10.0654f, 13.4009f, 9.757f, 13.1476f, 9.5637f)
                curveTo(12.8942f, 9.367f, 12.5009f, 9.2687f, 11.9676f, 9.2687f)
                horizontalLineTo(10.6476f)
                close()
                moveTo(11.9676f, 8.5037f)
                curveTo(12.4076f, 8.5037f, 12.7859f, 8.547f, 13.1026f, 8.6337f)
                curveTo(13.4226f, 8.7204f, 13.6842f, 8.8437f, 13.8876f, 9.0037f)
                curveTo(14.0942f, 9.1637f, 14.2459f, 9.3604f, 14.3426f, 9.5937f)
                curveTo(14.4392f, 9.8237f, 14.4876f, 10.0837f, 14.4876f, 10.3737f)
                curveTo(14.4876f, 10.5504f, 14.4592f, 10.7204f, 14.4026f, 10.8837f)
                curveTo(14.3492f, 11.0437f, 14.2676f, 11.1937f, 14.1576f, 11.3337f)
                curveTo(14.0476f, 11.4737f, 13.9076f, 11.6004f, 13.7376f, 11.7137f)
                curveTo(13.5709f, 11.8237f, 13.3742f, 11.9137f, 13.1476f, 11.9837f)
                curveTo(13.6742f, 12.0837f, 14.0692f, 12.272f, 14.3326f, 12.5487f)
                curveTo(14.5992f, 12.822f, 14.7326f, 13.182f, 14.7326f, 13.6287f)
                curveTo(14.7326f, 13.932f, 14.6759f, 14.2087f, 14.5626f, 14.4587f)
                curveTo(14.4526f, 14.7087f, 14.2892f, 14.9237f, 14.0726f, 15.1037f)
                curveTo(13.8592f, 15.2837f, 13.5959f, 15.4237f, 13.2826f, 15.5237f)
                curveTo(12.9692f, 15.6204f, 12.6126f, 15.6687f, 12.2126f, 15.6687f)
                horizontalLineTo(9.6826f)
                verticalLineTo(8.5037f)
                horizontalLineTo(11.9676f)
                close()
            }
        }
        .build()
        return _keyB!!
    }

private var _keyB: ImageVector? = null
