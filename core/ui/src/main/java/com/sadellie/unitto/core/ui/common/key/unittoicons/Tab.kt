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
val UnittoIcons.Tab: ImageVector
    get() {
        if (_tab != null) {
            return _tab!!
        }
        _tab = Builder(
            name = "Tab", defaultWidth = 170.0.dp, defaultHeight = 170.0.dp,
            viewportWidth = 170.0f, viewportHeight = 170.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF1C1B1F)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(141.667f, 127.5f)
                verticalLineTo(42.5f)
                horizontalLineTo(155.834f)
                verticalLineTo(127.5f)
                horizontalLineTo(141.667f)
                close()
                moveTo(85.0f, 127.5f)
                lineTo(74.907f, 117.583f)
                lineTo(100.407f, 92.083f)
                horizontalLineTo(14.167f)
                verticalLineTo(77.917f)
                horizontalLineTo(100.407f)
                lineTo(75.084f, 52.417f)
                lineTo(85.0f, 42.5f)
                lineTo(127.5f, 85.0f)
                lineTo(85.0f, 127.5f)
                close()
            }
        }
            .build()
        return _tab!!
    }

private var _tab: ImageVector? = null
