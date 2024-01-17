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
val IconPack.KeyD: ImageVector
    get() {
        if (_keyD != null) {
            return _keyD!!
        }
        _keyD = Builder(name = "KeyD", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(15.3213f, 12.0887f)
                curveTo(15.3213f, 12.6254f, 15.2363f, 13.1137f, 15.0663f, 13.5537f)
                curveTo(14.8963f, 13.9937f, 14.6563f, 14.3704f, 14.3463f, 14.6837f)
                curveTo(14.0363f, 14.997f, 13.663f, 15.2404f, 13.2263f, 15.4137f)
                curveTo(12.793f, 15.5837f, 12.313f, 15.6687f, 11.7863f, 15.6687f)
                horizontalLineTo(9.1113f)
                verticalLineTo(8.5037f)
                horizontalLineTo(11.7863f)
                curveTo(12.313f, 8.5037f, 12.793f, 8.5904f, 13.2263f, 8.7637f)
                curveTo(13.663f, 8.9337f, 14.0363f, 9.177f, 14.3463f, 9.4937f)
                curveTo(14.6563f, 9.807f, 14.8963f, 10.1837f, 15.0663f, 10.6237f)
                curveTo(15.2363f, 11.0637f, 15.3213f, 11.552f, 15.3213f, 12.0887f)
                close()
                moveTo(14.3263f, 12.0887f)
                curveTo(14.3263f, 11.6487f, 14.2663f, 11.2554f, 14.1463f, 10.9087f)
                curveTo(14.0263f, 10.562f, 13.8563f, 10.2687f, 13.6363f, 10.0287f)
                curveTo(13.4163f, 9.7887f, 13.1496f, 9.6054f, 12.8363f, 9.4787f)
                curveTo(12.523f, 9.352f, 12.173f, 9.2887f, 11.7863f, 9.2887f)
                horizontalLineTo(10.0813f)
                verticalLineTo(14.8837f)
                horizontalLineTo(11.7863f)
                curveTo(12.173f, 14.8837f, 12.523f, 14.8204f, 12.8363f, 14.6937f)
                curveTo(13.1496f, 14.567f, 13.4163f, 14.3854f, 13.6363f, 14.1487f)
                curveTo(13.8563f, 13.9087f, 14.0263f, 13.6154f, 14.1463f, 13.2687f)
                curveTo(14.2663f, 12.922f, 14.3263f, 12.5287f, 14.3263f, 12.0887f)
                close()
            }
        }
        .build()
        return _keyD!!
    }

private var _keyD: ImageVector? = null
