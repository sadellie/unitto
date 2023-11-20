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
val UnittoIcons.Up: ImageVector
    get() {
        if (_up != null) {
            return _up!!
        }
        _up = Builder(name = "Up", defaultWidth = 170.0.dp, defaultHeight = 170.0.dp, viewportWidth
                = 170.0f, viewportHeight = 170.0f).apply {
            path(fill = SolidColor(Color(0xFF1C1B1F)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(79.0f, 133.0f)
                verticalLineTo(59.95f)
                lineTo(45.4f, 93.55f)
                lineTo(37.0f, 85.0f)
                lineTo(85.0f, 37.0f)
                lineTo(133.0f, 85.0f)
                lineTo(124.6f, 93.55f)
                lineTo(91.0f, 59.95f)
                verticalLineTo(133.0f)
                horizontalLineTo(79.0f)
                close()
            }
        }
        .build()
        return _up!!
    }

private var _up: ImageVector? = null
