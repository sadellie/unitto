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
val IconPack.Swap: ImageVector
    get() {
        if (_swap != null) {
            return _swap!!
        }
        _swap = Builder(name = "Swap", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF1C1B1F)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(10.5454f, 12.5325f)
                verticalLineTo(9.9234f)
                lineTo(9.609f, 10.8597f)
                lineTo(9.0908f, 10.3507f)
                lineTo(10.909f, 8.5325f)
                lineTo(12.7272f, 10.3507f)
                lineTo(12.209f, 10.8597f)
                lineTo(11.2726f, 9.9234f)
                verticalLineTo(12.5325f)
                horizontalLineTo(10.5454f)
                close()
                moveTo(13.0908f, 15.8052f)
                lineTo(11.2726f, 13.987f)
                lineTo(11.7908f, 13.4779f)
                lineTo(12.7272f, 14.4143f)
                verticalLineTo(11.8052f)
                horizontalLineTo(13.4545f)
                verticalLineTo(14.4143f)
                lineTo(14.3908f, 13.4779f)
                lineTo(14.909f, 13.987f)
                lineTo(13.0908f, 15.8052f)
                close()
            }
        }
        .build()
        return _swap!!
    }

private var _swap: ImageVector? = null
