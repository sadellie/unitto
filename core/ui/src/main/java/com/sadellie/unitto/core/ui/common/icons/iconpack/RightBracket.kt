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
val IconPack.RightBracket: ImageVector
    get() {
        if (_rightBracket != null) {
            return _rightBracket!!
        }
        _rightBracket = Builder(name = "RightBracket", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(12.0562f, 12.1688f)
                curveTo(12.0562f, 12.7178f, 11.9882f, 13.255f, 11.8523f, 13.7805f)
                curveTo(11.7163f, 14.3033f, 11.5189f, 14.7961f, 11.2601f, 15.2588f)
                curveTo(11.2366f, 15.3059f, 11.2222f, 15.3477f, 11.217f, 15.3843f)
                curveTo(11.2118f, 15.4209f, 11.2144f, 15.4523f, 11.2248f, 15.4784f)
                curveTo(11.2353f, 15.5072f, 11.251f, 15.5307f, 11.2719f, 15.549f)
                curveTo(11.2902f, 15.5673f, 11.3111f, 15.5843f, 11.3346f, 15.6f)
                lineTo(11.7072f, 15.8313f)
                curveTo(11.9032f, 15.5307f, 12.0692f, 15.2314f, 12.2052f, 14.9334f)
                curveTo(12.3411f, 14.638f, 12.4522f, 14.3399f, 12.5385f, 14.0393f)
                curveTo(12.6247f, 13.7361f, 12.6875f, 13.4302f, 12.7267f, 13.1217f)
                curveTo(12.7659f, 12.8106f, 12.7855f, 12.493f, 12.7855f, 12.1688f)
                curveTo(12.7855f, 11.8473f, 12.7659f, 11.5323f, 12.7267f, 11.2238f)
                curveTo(12.6875f, 10.9127f, 12.6247f, 10.6069f, 12.5385f, 10.3062f)
                curveTo(12.4522f, 10.003f, 12.3411f, 9.7023f, 12.2052f, 9.4043f)
                curveTo(12.0692f, 9.1063f, 11.9032f, 8.807f, 11.7072f, 8.5063f)
                lineTo(11.3346f, 8.7377f)
                curveTo(11.3111f, 8.7534f, 11.2902f, 8.7704f, 11.2719f, 8.7887f)
                curveTo(11.251f, 8.807f, 11.2353f, 8.8305f, 11.2248f, 8.8593f)
                curveTo(11.2144f, 8.8854f, 11.2118f, 8.9168f, 11.217f, 8.9534f)
                curveTo(11.2222f, 8.99f, 11.2366f, 9.0318f, 11.2601f, 9.0789f)
                curveTo(11.5189f, 9.5442f, 11.7163f, 10.0383f, 11.8523f, 10.5611f)
                curveTo(11.9882f, 11.084f, 12.0562f, 11.6199f, 12.0562f, 12.1688f)
                close()
            }
        }
        .build()
        return _rightBracket!!
    }

private var _rightBracket: ImageVector? = null
