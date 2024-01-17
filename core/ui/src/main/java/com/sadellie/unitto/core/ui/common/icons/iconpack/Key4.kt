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
val IconPack.Key4: ImageVector
    get() {
        if (_key4 != null) {
            return _key4!!
        }
        _key4 = Builder(name = "Key4", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(12.7496f, 13.0837f)
                verticalLineTo(10.1287f)
                curveTo(12.7496f, 10.042f, 12.7529f, 9.947f, 12.7596f, 9.8437f)
                curveTo(12.7663f, 9.7404f, 12.7763f, 9.6354f, 12.7896f, 9.5287f)
                lineTo(10.1796f, 13.0837f)
                horizontalLineTo(12.7496f)
                close()
                moveTo(14.6146f, 13.0837f)
                verticalLineTo(13.5937f)
                curveTo(14.6146f, 13.647f, 14.5979f, 13.692f, 14.5646f, 13.7287f)
                curveTo(14.5346f, 13.7654f, 14.4863f, 13.7837f, 14.4196f, 13.7837f)
                horizontalLineTo(13.5296f)
                verticalLineTo(15.6687f)
                horizontalLineTo(12.7496f)
                verticalLineTo(13.7837f)
                horizontalLineTo(9.5696f)
                curveTo(9.5029f, 13.7837f, 9.4446f, 13.7654f, 9.3946f, 13.7287f)
                curveTo(9.3479f, 13.6887f, 9.3179f, 13.6404f, 9.3046f, 13.5837f)
                lineTo(9.2146f, 13.1287f)
                lineTo(12.6996f, 8.5037f)
                horizontalLineTo(13.5296f)
                verticalLineTo(13.0837f)
                horizontalLineTo(14.6146f)
                close()
            }
        }
        .build()
        return _key4!!
    }

private var _key4: ImageVector? = null
