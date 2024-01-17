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
val IconPack.Root: ImageVector
    get() {
        if (_root != null) {
            return _root!!
        }
        _root = Builder(name = "Root", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(15.5551f, 7.1087f)
                lineTo(12.7901f, 15.6687f)
                horizontalLineTo(12.0401f)
                lineTo(10.7901f, 12.3537f)
                horizontalLineTo(9.9351f)
                curveTo(9.8418f, 12.3537f, 9.7601f, 12.327f, 9.6901f, 12.2737f)
                curveTo(9.6235f, 12.217f, 9.5901f, 12.1204f, 9.5901f, 11.9837f)
                verticalLineTo(11.6987f)
                horizontalLineTo(11.2651f)
                curveTo(11.3418f, 11.6987f, 11.4035f, 11.717f, 11.4501f, 11.7537f)
                curveTo(11.4968f, 11.7904f, 11.5285f, 11.8337f, 11.5451f, 11.8837f)
                lineTo(12.2901f, 13.9387f)
                curveTo(12.3268f, 14.0487f, 12.3551f, 14.1587f, 12.3751f, 14.2687f)
                curveTo(12.3985f, 14.3787f, 12.4185f, 14.4904f, 12.4351f, 14.6037f)
                curveTo(12.4518f, 14.5137f, 12.4685f, 14.4237f, 12.4851f, 14.3337f)
                curveTo(12.5051f, 14.2404f, 12.5285f, 14.1454f, 12.5551f, 14.0487f)
                lineTo(14.7201f, 7.2937f)
                curveTo(14.7368f, 7.2404f, 14.7685f, 7.197f, 14.8151f, 7.1637f)
                curveTo(14.8618f, 7.127f, 14.9168f, 7.1087f, 14.9801f, 7.1087f)
                horizontalLineTo(15.5551f)
                close()
            }
        }
        .build()
        return _root!!
    }

private var _root: ImageVector? = null
