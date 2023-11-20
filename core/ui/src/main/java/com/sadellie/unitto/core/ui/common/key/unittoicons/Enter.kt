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
val UnittoIcons.Enter: ImageVector
    get() {
        if (_enter != null) {
            return _enter!!
        }
        _enter = Builder(name = "Enter", defaultWidth = 170.0.dp, defaultHeight = 170.0.dp,
                viewportWidth = 170.0f, viewportHeight = 170.0f).apply {
            path(fill = SolidColor(Color(0xFF1C1B1F)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(128.0f, 37.0f)
                lineTo(128.0f, 104.765f)
                lineTo(64.894f, 104.765f)
                lineTo(85.224f, 124.953f)
                lineTo(77.176f, 133.0f)
                lineTo(43.294f, 99.118f)
                lineTo(77.318f, 65.094f)
                lineTo(85.224f, 73.141f)
                lineTo(64.894f, 93.471f)
                lineTo(116.706f, 93.471f)
                lineTo(116.706f, 37.0f)
                lineTo(128.0f, 37.0f)
                close()
            }
        }
        .build()
        return _enter!!
    }

private var _enter: ImageVector? = null
