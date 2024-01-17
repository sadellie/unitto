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
val IconPack.LeftBracket: ImageVector
    get() {
        if (_leftBracket != null) {
            return _leftBracket!!
        }
        _leftBracket = Builder(name = "LeftBracket", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(11.9438f, 12.1688f)
                curveTo(11.9438f, 12.7178f, 12.0118f, 13.255f, 12.1477f, 13.7805f)
                curveTo(12.2837f, 14.3033f, 12.4811f, 14.7961f, 12.7399f, 15.2588f)
                curveTo(12.7634f, 15.3059f, 12.7778f, 15.3477f, 12.783f, 15.3843f)
                curveTo(12.7882f, 15.4209f, 12.7856f, 15.4523f, 12.7752f, 15.4784f)
                curveTo(12.7647f, 15.5072f, 12.749f, 15.5307f, 12.7281f, 15.549f)
                curveTo(12.7098f, 15.5673f, 12.6889f, 15.5843f, 12.6654f, 15.6f)
                lineTo(12.2928f, 15.8313f)
                curveTo(12.0968f, 15.5307f, 11.9308f, 15.2314f, 11.7948f, 14.9334f)
                curveTo(11.6589f, 14.638f, 11.5478f, 14.3399f, 11.4615f, 14.0393f)
                curveTo(11.3753f, 13.7361f, 11.3125f, 13.4302f, 11.2733f, 13.1217f)
                curveTo(11.2341f, 12.8106f, 11.2145f, 12.493f, 11.2145f, 12.1688f)
                curveTo(11.2145f, 11.8473f, 11.2341f, 11.5323f, 11.2733f, 11.2238f)
                curveTo(11.3125f, 10.9127f, 11.3753f, 10.6069f, 11.4615f, 10.3062f)
                curveTo(11.5478f, 10.003f, 11.6589f, 9.7023f, 11.7948f, 9.4043f)
                curveTo(11.9308f, 9.1063f, 12.0968f, 8.807f, 12.2928f, 8.5063f)
                lineTo(12.6654f, 8.7377f)
                curveTo(12.6889f, 8.7534f, 12.7098f, 8.7704f, 12.7281f, 8.7887f)
                curveTo(12.749f, 8.807f, 12.7647f, 8.8305f, 12.7752f, 8.8593f)
                curveTo(12.7856f, 8.8854f, 12.7882f, 8.9168f, 12.783f, 8.9534f)
                curveTo(12.7778f, 8.99f, 12.7634f, 9.0318f, 12.7399f, 9.0789f)
                curveTo(12.4811f, 9.5442f, 12.2837f, 10.0383f, 12.1477f, 10.5611f)
                curveTo(12.0118f, 11.084f, 11.9438f, 11.6199f, 11.9438f, 12.1688f)
                close()
            }
        }
        .build()
        return _leftBracket!!
    }

private var _leftBracket: ImageVector? = null
