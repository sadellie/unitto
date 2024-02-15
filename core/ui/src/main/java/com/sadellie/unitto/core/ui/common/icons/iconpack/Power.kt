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
val IconPack.Power: ImageVector
    get() {
        if (_power != null) {
            return _power!!
        }
        _power = Builder(name = "Power", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(14.2396f, 12.1887f)
                horizontalLineTo(13.5896f)
                curveTo(13.5329f, 12.1887f, 13.4829f, 12.1737f, 13.4396f, 12.1437f)
                curveTo(13.3996f, 12.1104f, 13.3679f, 12.0687f, 13.3446f, 12.0187f)
                lineTo(12.1946f, 9.8037f)
                curveTo(12.1079f, 9.627f, 12.0413f, 9.4654f, 11.9946f, 9.3187f)
                curveTo(11.9713f, 9.3954f, 11.9446f, 9.4737f, 11.9146f, 9.5537f)
                curveTo(11.8846f, 9.6337f, 11.8513f, 9.717f, 11.8146f, 9.8037f)
                lineTo(10.6996f, 12.0187f)
                curveTo(10.6796f, 12.0654f, 10.6479f, 12.1054f, 10.6046f, 12.1387f)
                curveTo(10.5613f, 12.172f, 10.5063f, 12.1887f, 10.4396f, 12.1887f)
                horizontalLineTo(9.7596f)
                lineTo(11.6996f, 8.5037f)
                horizontalLineTo(12.2746f)
                lineTo(14.2396f, 12.1887f)
                close()
            }
        }
        .build()
        return _power!!
    }

private var _power: ImageVector? = null
