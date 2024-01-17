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
                moveTo(5.9815f, 15.6687f)
                horizontalLineTo(5.0115f)
                verticalLineTo(8.5037f)
                horizontalLineTo(5.9815f)
                verticalLineTo(15.6687f)
                close()
                moveTo(13.4192f, 8.5037f)
                verticalLineTo(15.6687f)
                horizontalLineTo(12.9342f)
                curveTo(12.8576f, 15.6687f, 12.7926f, 15.6554f, 12.7392f, 15.6287f)
                curveTo(12.6892f, 15.602f, 12.6392f, 15.557f, 12.5892f, 15.4937f)
                lineTo(8.4442f, 10.0937f)
                curveTo(8.4509f, 10.177f, 8.4559f, 10.2587f, 8.4592f, 10.3387f)
                curveTo(8.4626f, 10.4187f, 8.4642f, 10.4937f, 8.4642f, 10.5637f)
                verticalLineTo(15.6687f)
                horizontalLineTo(7.6142f)
                verticalLineTo(8.5037f)
                horizontalLineTo(8.1142f)
                curveTo(8.1575f, 8.5037f, 8.1942f, 8.507f, 8.2242f, 8.5137f)
                curveTo(8.2542f, 8.517f, 8.2809f, 8.5254f, 8.3042f, 8.5387f)
                curveTo(8.3275f, 8.5487f, 8.3509f, 8.5654f, 8.3742f, 8.5887f)
                curveTo(8.3975f, 8.6087f, 8.4226f, 8.6354f, 8.4492f, 8.6687f)
                lineTo(12.5942f, 14.0637f)
                curveTo(12.5876f, 13.977f, 12.5809f, 13.8937f, 12.5742f, 13.8137f)
                curveTo(12.5709f, 13.7304f, 12.5692f, 13.652f, 12.5692f, 13.5787f)
                verticalLineTo(8.5037f)
                horizontalLineTo(13.4192f)
                close()
                moveTo(20.8759f, 8.5037f)
                lineTo(17.9559f, 15.6687f)
                horizontalLineTo(17.0859f)
                lineTo(14.1659f, 8.5037f)
                horizontalLineTo(14.9409f)
                curveTo(15.0276f, 8.5037f, 15.0976f, 8.5254f, 15.1509f, 8.5687f)
                curveTo(15.2043f, 8.612f, 15.2443f, 8.667f, 15.2709f, 8.7337f)
                lineTo(17.2909f, 13.7887f)
                curveTo(17.3343f, 13.902f, 17.3759f, 14.0254f, 17.4159f, 14.1587f)
                curveTo(17.4593f, 14.292f, 17.4976f, 14.432f, 17.5309f, 14.5787f)
                curveTo(17.5643f, 14.432f, 17.5993f, 14.292f, 17.6359f, 14.1587f)
                curveTo(17.6726f, 14.0254f, 17.7126f, 13.902f, 17.7559f, 13.7887f)
                lineTo(19.7709f, 8.7337f)
                curveTo(19.7909f, 8.677f, 19.8293f, 8.6254f, 19.8859f, 8.5787f)
                curveTo(19.9459f, 8.5287f, 20.0176f, 8.5037f, 20.1009f, 8.5037f)
                horizontalLineTo(20.8759f)
                close()
            }
        }
        .build()
        return _inv!!
    }

private var _inv: ImageVector? = null
