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
val IconPack.Tab: ImageVector
    get() {
        if (_tab != null) {
            return _tab!!
        }
        _tab = Builder(name = "Tab", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp, viewportWidth
                = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF1C1B1F)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(16.2929f, 15.3884f)
                verticalLineTo(8.949f)
                horizontalLineTo(17.3661f)
                verticalLineTo(15.3884f)
                horizontalLineTo(16.2929f)
                close()
                moveTo(12.0f, 15.3884f)
                lineTo(11.2353f, 14.6371f)
                lineTo(13.1671f, 12.7053f)
                horizontalLineTo(6.6338f)
                verticalLineTo(11.6321f)
                horizontalLineTo(13.1671f)
                lineTo(11.2487f, 9.7002f)
                lineTo(12.0f, 8.949f)
                lineTo(15.2196f, 12.1687f)
                lineTo(12.0f, 15.3884f)
                close()
            }
        }
        .build()
        return _tab!!
    }

private var _tab: ImageVector? = null
