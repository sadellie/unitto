/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

package com.sadellie.unitto.core.ui.common.key.unittoicons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.ui.common.key.UnittoIcons

@Suppress("UnusedReceiverParameter")
val UnittoIcons.Swap: ImageVector
    get() {
        if (_swap != null) {
            return _swap!!
        }
        _swap = Builder(name = "Swap", defaultWidth = 170.0.dp, defaultHeight = 170.0.dp,
                viewportWidth = 170.0f, viewportHeight = 170.0f).apply {
            path(fill = SolidColor(Color(0xFF1C1B1F)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(66.2f, 89.8f)
                verticalLineTo(55.36f)
                lineTo(53.84f, 67.72f)
                lineTo(47.0f, 61.0f)
                lineTo(71.0f, 37.0f)
                lineTo(95.0f, 61.0f)
                lineTo(88.16f, 67.72f)
                lineTo(75.8f, 55.36f)
                verticalLineTo(89.8f)
                horizontalLineTo(66.2f)
                close()
                moveTo(99.8f, 133.0f)
                lineTo(75.8f, 109.0f)
                lineTo(82.64f, 102.28f)
                lineTo(95.0f, 114.64f)
                verticalLineTo(80.2f)
                horizontalLineTo(104.6f)
                verticalLineTo(114.64f)
                lineTo(116.96f, 102.28f)
                lineTo(123.8f, 109.0f)
                lineTo(99.8f, 133.0f)
                close()
            }
        }
        .build()
        return _swap!!
    }

private var _swap: ImageVector? = null
