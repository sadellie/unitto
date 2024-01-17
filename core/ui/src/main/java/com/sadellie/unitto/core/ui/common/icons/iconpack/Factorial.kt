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
val IconPack.Factorial: ImageVector
    get() {
        if (_factorial != null) {
            return _factorial!!
        }
        _factorial = Builder(name = "Factorial", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(12.9322f, 8.5037f)
                verticalLineTo(11.3637f)
                curveTo(12.9322f, 11.5137f, 12.9289f, 11.6604f, 12.9222f, 11.8037f)
                curveTo(12.9156f, 11.947f, 12.9072f, 12.092f, 12.8972f, 12.2387f)
                curveTo(12.8872f, 12.382f, 12.8739f, 12.5304f, 12.8572f, 12.6837f)
                curveTo(12.8439f, 12.8337f, 12.8272f, 12.9954f, 12.8072f, 13.1687f)
                horizontalLineTo(12.2072f)
                curveTo(12.1872f, 12.9954f, 12.1689f, 12.8337f, 12.1522f, 12.6837f)
                curveTo(12.1389f, 12.5304f, 12.1272f, 12.382f, 12.1172f, 12.2387f)
                curveTo(12.1072f, 12.092f, 12.0989f, 11.947f, 12.0922f, 11.8037f)
                curveTo(12.0856f, 11.6604f, 12.0822f, 11.5137f, 12.0822f, 11.3637f)
                verticalLineTo(8.5037f)
                horizontalLineTo(12.9322f)
                close()
                moveTo(11.8672f, 15.1187f)
                curveTo(11.8672f, 15.032f, 11.8822f, 14.9504f, 11.9122f, 14.8737f)
                curveTo(11.9456f, 14.797f, 11.9906f, 14.7304f, 12.0472f, 14.6737f)
                curveTo(12.1039f, 14.617f, 12.1689f, 14.572f, 12.2422f, 14.5387f)
                curveTo(12.3189f, 14.5054f, 12.4022f, 14.4887f, 12.4922f, 14.4887f)
                curveTo(12.5789f, 14.4887f, 12.6589f, 14.5054f, 12.7322f, 14.5387f)
                curveTo(12.8089f, 14.572f, 12.8756f, 14.617f, 12.9322f, 14.6737f)
                curveTo(12.9889f, 14.7304f, 13.0339f, 14.797f, 13.0672f, 14.8737f)
                curveTo(13.1006f, 14.9504f, 13.1172f, 15.032f, 13.1172f, 15.1187f)
                curveTo(13.1172f, 15.2087f, 13.1006f, 15.292f, 13.0672f, 15.3687f)
                curveTo(13.0339f, 15.442f, 12.9889f, 15.507f, 12.9322f, 15.5637f)
                curveTo(12.8756f, 15.6204f, 12.8089f, 15.6637f, 12.7322f, 15.6937f)
                curveTo(12.6589f, 15.727f, 12.5789f, 15.7437f, 12.4922f, 15.7437f)
                curveTo(12.4022f, 15.7437f, 12.3189f, 15.727f, 12.2422f, 15.6937f)
                curveTo(12.1689f, 15.6637f, 12.1039f, 15.6204f, 12.0472f, 15.5637f)
                curveTo(11.9906f, 15.507f, 11.9456f, 15.442f, 11.9122f, 15.3687f)
                curveTo(11.8822f, 15.292f, 11.8672f, 15.2087f, 11.8672f, 15.1187f)
                close()
            }
        }
        .build()
        return _factorial!!
    }

private var _factorial: ImageVector? = null
