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
val IconPack.KeyA: ImageVector
    get() {
        if (_keyA != null) {
            return _keyA!!
        }
        _keyA = Builder(name = "KeyA", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(13.8362f, 13.0087f)
                lineTo(12.7112f, 10.0937f)
                curveTo(12.6779f, 10.007f, 12.6429f, 9.907f, 12.6062f, 9.7937f)
                curveTo(12.5695f, 9.677f, 12.5345f, 9.5537f, 12.5012f, 9.4237f)
                curveTo(12.4312f, 9.6937f, 12.3595f, 9.9187f, 12.2862f, 10.0987f)
                lineTo(11.1612f, 13.0087f)
                horizontalLineTo(13.8362f)
                close()
                moveTo(15.8562f, 15.6687f)
                horizontalLineTo(15.1062f)
                curveTo(15.0195f, 15.6687f, 14.9495f, 15.647f, 14.8962f, 15.6037f)
                curveTo(14.8429f, 15.5604f, 14.8029f, 15.5054f, 14.7762f, 15.4387f)
                lineTo(14.1062f, 13.7087f)
                horizontalLineTo(10.8912f)
                lineTo(10.2212f, 15.4387f)
                curveTo(10.2012f, 15.4987f, 10.1629f, 15.552f, 10.1062f, 15.5987f)
                curveTo(10.0495f, 15.6454f, 9.9795f, 15.6687f, 9.8962f, 15.6687f)
                horizontalLineTo(9.1462f)
                lineTo(12.0112f, 8.5037f)
                horizontalLineTo(12.9912f)
                lineTo(15.8562f, 15.6687f)
                close()
            }
        }
        .build()
        return _keyA!!
    }

private var _keyA: ImageVector? = null
