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
                moveTo(15.0551f, 7.1087f)
                lineTo(12.2901f, 15.6687f)
                horizontalLineTo(11.5401f)
                lineTo(10.2901f, 12.3537f)
                horizontalLineTo(9.4351f)
                curveTo(9.3418f, 12.3537f, 9.2601f, 12.327f, 9.1901f, 12.2737f)
                curveTo(9.1235f, 12.217f, 9.0901f, 12.1204f, 9.0901f, 11.9837f)
                verticalLineTo(11.6987f)
                horizontalLineTo(10.7651f)
                curveTo(10.8418f, 11.6987f, 10.9035f, 11.717f, 10.9501f, 11.7537f)
                curveTo(10.9968f, 11.7904f, 11.0285f, 11.8337f, 11.0451f, 11.8837f)
                lineTo(11.7901f, 13.9387f)
                curveTo(11.8268f, 14.0487f, 11.8551f, 14.1587f, 11.8751f, 14.2687f)
                curveTo(11.8985f, 14.3787f, 11.9185f, 14.4904f, 11.9351f, 14.6037f)
                curveTo(11.9518f, 14.5137f, 11.9685f, 14.4237f, 11.9851f, 14.3337f)
                curveTo(12.0051f, 14.2404f, 12.0285f, 14.1454f, 12.0551f, 14.0487f)
                lineTo(14.2201f, 7.2937f)
                curveTo(14.2368f, 7.2404f, 14.2685f, 7.197f, 14.3151f, 7.1637f)
                curveTo(14.3618f, 7.127f, 14.4168f, 7.1087f, 14.4801f, 7.1087f)
                horizontalLineTo(15.0551f)
                close()
            }
        }
        .build()
        return _root!!
    }

private var _root: ImageVector? = null
