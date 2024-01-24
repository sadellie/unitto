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
val IconPack.Ln: ImageVector
    get() {
        if (_ln != null) {
            return _ln!!
        }
        _ln = Builder(name = "Ln", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp, viewportWidth =
                24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(9.7553f, 8.3037f)
                verticalLineTo(15.6687f)
                horizontalLineTo(8.8653f)
                verticalLineTo(8.3037f)
                horizontalLineTo(9.7553f)
                close()
                moveTo(11.8386f, 11.3387f)
                curveTo(11.9486f, 11.2154f, 12.0652f, 11.1037f, 12.1886f, 11.0037f)
                curveTo(12.3119f, 10.9037f, 12.4419f, 10.8187f, 12.5786f, 10.7487f)
                curveTo(12.7186f, 10.6754f, 12.8652f, 10.6204f, 13.0186f, 10.5837f)
                curveTo(13.1752f, 10.5437f, 13.3436f, 10.5237f, 13.5236f, 10.5237f)
                curveTo(13.8002f, 10.5237f, 14.0436f, 10.5704f, 14.2536f, 10.6637f)
                curveTo(14.4669f, 10.7537f, 14.6436f, 10.8837f, 14.7836f, 11.0537f)
                curveTo(14.9269f, 11.2204f, 15.0352f, 11.422f, 15.1086f, 11.6587f)
                curveTo(15.1819f, 11.8954f, 15.2186f, 12.157f, 15.2186f, 12.4437f)
                verticalLineTo(15.6687f)
                horizontalLineTo(14.3236f)
                verticalLineTo(12.4437f)
                curveTo(14.3236f, 12.0604f, 14.2352f, 11.7637f, 14.0586f, 11.5537f)
                curveTo(13.8852f, 11.3404f, 13.6202f, 11.2337f, 13.2636f, 11.2337f)
                curveTo(13.0002f, 11.2337f, 12.7536f, 11.297f, 12.5236f, 11.4237f)
                curveTo(12.2969f, 11.5504f, 12.0869f, 11.722f, 11.8936f, 11.9387f)
                verticalLineTo(15.6687f)
                horizontalLineTo(10.9986f)
                verticalLineTo(10.6037f)
                horizontalLineTo(11.5336f)
                curveTo(11.6602f, 10.6037f, 11.7386f, 10.6654f, 11.7686f, 10.7887f)
                lineTo(11.8386f, 11.3387f)
                close()
            }
        }
        .build()
        return _ln!!
    }

private var _ln: ImageVector? = null
