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
val IconPack.Key7: ImageVector
    get() {
        if (_key7 != null) {
            return _key7!!
        }
        _key7 = Builder(name = "Key7", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(14.5046f, 8.5037f)
                verticalLineTo(8.9037f)
                curveTo(14.5046f, 9.017f, 14.4913f, 9.1104f, 14.4646f, 9.1837f)
                curveTo(14.4413f, 9.257f, 14.4179f, 9.3187f, 14.3946f, 9.3687f)
                lineTo(11.4246f, 15.3537f)
                curveTo(11.3813f, 15.4404f, 11.3213f, 15.5154f, 11.2446f, 15.5787f)
                curveTo(11.1679f, 15.6387f, 11.0663f, 15.6687f, 10.9396f, 15.6687f)
                horizontalLineTo(10.2996f)
                lineTo(13.3096f, 9.7587f)
                curveTo(13.3529f, 9.6754f, 13.3979f, 9.5987f, 13.4446f, 9.5287f)
                curveTo(13.4913f, 9.4587f, 13.5413f, 9.392f, 13.5946f, 9.3287f)
                horizontalLineTo(9.8546f)
                curveTo(9.7979f, 9.3287f, 9.7479f, 9.307f, 9.7046f, 9.2637f)
                curveTo(9.6613f, 9.217f, 9.6396f, 9.1654f, 9.6396f, 9.1087f)
                verticalLineTo(8.5037f)
                horizontalLineTo(14.5046f)
                close()
            }
        }
        .build()
        return _key7!!
    }

private var _key7: ImageVector? = null
