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
                moveTo(12.6976f, 14.8987f)
                curveTo(12.9742f, 14.8987f, 13.2126f, 14.867f, 13.4126f, 14.8037f)
                curveTo(13.6126f, 14.7404f, 13.7759f, 14.652f, 13.9026f, 14.5387f)
                curveTo(14.0326f, 14.422f, 14.1276f, 14.2854f, 14.1876f, 14.1287f)
                curveTo(14.2476f, 13.9687f, 14.2776f, 13.7937f, 14.2776f, 13.6037f)
                curveTo(14.2776f, 13.2337f, 14.1459f, 12.942f, 13.8826f, 12.7287f)
                curveTo(13.6192f, 12.512f, 13.2242f, 12.4037f, 12.6976f, 12.4037f)
                horizontalLineTo(11.1476f)
                verticalLineTo(14.8987f)
                horizontalLineTo(12.6976f)
                close()
                moveTo(11.1476f, 9.2687f)
                verticalLineTo(11.7137f)
                horizontalLineTo(12.4276f)
                curveTo(12.7009f, 11.7137f, 12.9376f, 11.6837f, 13.1376f, 11.6237f)
                curveTo(13.3409f, 11.5637f, 13.5076f, 11.4804f, 13.6376f, 11.3737f)
                curveTo(13.7709f, 11.267f, 13.8692f, 11.1387f, 13.9326f, 10.9887f)
                curveTo(13.9959f, 10.8354f, 14.0276f, 10.6687f, 14.0276f, 10.4887f)
                curveTo(14.0276f, 10.0654f, 13.9009f, 9.757f, 13.6476f, 9.5637f)
                curveTo(13.3942f, 9.367f, 13.0009f, 9.2687f, 12.4676f, 9.2687f)
                horizontalLineTo(11.1476f)
                close()
                moveTo(12.4676f, 8.5037f)
                curveTo(12.9076f, 8.5037f, 13.2859f, 8.547f, 13.6026f, 8.6337f)
                curveTo(13.9226f, 8.7204f, 14.1842f, 8.8437f, 14.3876f, 9.0037f)
                curveTo(14.5942f, 9.1637f, 14.7459f, 9.3604f, 14.8426f, 9.5937f)
                curveTo(14.9392f, 9.8237f, 14.9876f, 10.0837f, 14.9876f, 10.3737f)
                curveTo(14.9876f, 10.5504f, 14.9592f, 10.7204f, 14.9026f, 10.8837f)
                curveTo(14.8492f, 11.0437f, 14.7676f, 11.1937f, 14.6576f, 11.3337f)
                curveTo(14.5476f, 11.4737f, 14.4076f, 11.6004f, 14.2376f, 11.7137f)
                curveTo(14.0709f, 11.8237f, 13.8742f, 11.9137f, 13.6476f, 11.9837f)
                curveTo(14.1742f, 12.0837f, 14.5692f, 12.272f, 14.8326f, 12.5487f)
                curveTo(15.0992f, 12.822f, 15.2326f, 13.182f, 15.2326f, 13.6287f)
                curveTo(15.2326f, 13.932f, 15.1759f, 14.2087f, 15.0626f, 14.4587f)
                curveTo(14.9526f, 14.7087f, 14.7892f, 14.9237f, 14.5726f, 15.1037f)
                curveTo(14.3592f, 15.2837f, 14.0959f, 15.4237f, 13.7826f, 15.5237f)
                curveTo(13.4692f, 15.6204f, 13.1126f, 15.6687f, 12.7126f, 15.6687f)
                horizontalLineTo(10.1826f)
                verticalLineTo(8.5037f)
                horizontalLineTo(12.4676f)
                close()
            }
        }
        .build()
        return _keyB!!
    }

private var _keyB: ImageVector? = null
