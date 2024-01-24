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
val IconPack.Inv: ImageVector
    get() {
        if (_inv != null) {
            return _inv!!
        }
        _inv = Builder(name = "Inv", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp, viewportWidth
                = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(5.4815f, 15.6687f)
                horizontalLineTo(4.5115f)
                verticalLineTo(8.5037f)
                horizontalLineTo(5.4815f)
                verticalLineTo(15.6687f)
                close()
                moveTo(12.9192f, 8.5037f)
                verticalLineTo(15.6687f)
                horizontalLineTo(12.4342f)
                curveTo(12.3576f, 15.6687f, 12.2926f, 15.6554f, 12.2392f, 15.6287f)
                curveTo(12.1892f, 15.602f, 12.1392f, 15.557f, 12.0892f, 15.4937f)
                lineTo(7.9442f, 10.0937f)
                curveTo(7.9509f, 10.177f, 7.9559f, 10.2587f, 7.9592f, 10.3387f)
                curveTo(7.9626f, 10.4187f, 7.9642f, 10.4937f, 7.9642f, 10.5637f)
                verticalLineTo(15.6687f)
                horizontalLineTo(7.1142f)
                verticalLineTo(8.5037f)
                horizontalLineTo(7.6142f)
                curveTo(7.6575f, 8.5037f, 7.6942f, 8.507f, 7.7242f, 8.5137f)
                curveTo(7.7542f, 8.517f, 7.7809f, 8.5254f, 7.8042f, 8.5387f)
                curveTo(7.8275f, 8.5487f, 7.8509f, 8.5654f, 7.8742f, 8.5887f)
                curveTo(7.8976f, 8.6087f, 7.9226f, 8.6354f, 7.9492f, 8.6687f)
                lineTo(12.0942f, 14.0637f)
                curveTo(12.0876f, 13.977f, 12.0809f, 13.8937f, 12.0742f, 13.8137f)
                curveTo(12.0709f, 13.7304f, 12.0692f, 13.652f, 12.0692f, 13.5787f)
                verticalLineTo(8.5037f)
                horizontalLineTo(12.9192f)
                close()
                moveTo(20.3759f, 8.5037f)
                lineTo(17.4559f, 15.6687f)
                horizontalLineTo(16.5859f)
                lineTo(13.6659f, 8.5037f)
                horizontalLineTo(14.4409f)
                curveTo(14.5276f, 8.5037f, 14.5976f, 8.5254f, 14.6509f, 8.5687f)
                curveTo(14.7043f, 8.612f, 14.7443f, 8.667f, 14.7709f, 8.7337f)
                lineTo(16.7909f, 13.7887f)
                curveTo(16.8343f, 13.902f, 16.8759f, 14.0254f, 16.9159f, 14.1587f)
                curveTo(16.9593f, 14.292f, 16.9976f, 14.432f, 17.0309f, 14.5787f)
                curveTo(17.0643f, 14.432f, 17.0993f, 14.292f, 17.1359f, 14.1587f)
                curveTo(17.1726f, 14.0254f, 17.2126f, 13.902f, 17.2559f, 13.7887f)
                lineTo(19.2709f, 8.7337f)
                curveTo(19.2909f, 8.677f, 19.3293f, 8.6254f, 19.3859f, 8.5787f)
                curveTo(19.4459f, 8.5287f, 19.5176f, 8.5037f, 19.6009f, 8.5037f)
                horizontalLineTo(20.3759f)
                close()
            }
        }
        .build()
        return _inv!!
    }

private var _inv: ImageVector? = null
