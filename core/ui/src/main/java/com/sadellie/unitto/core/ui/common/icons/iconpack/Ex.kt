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
val IconPack.Ex: ImageVector
    get() {
        if (_ex != null) {
            return _ex!!
        }
        _ex = Builder(name = "Ex", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp, viewportWidth =
                24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(12.1276f, 12.5837f)
                curveTo(12.1276f, 12.377f, 12.0976f, 12.1887f, 12.0376f, 12.0187f)
                curveTo(11.981f, 11.8454f, 11.896f, 11.697f, 11.7826f, 11.5737f)
                curveTo(11.6726f, 11.447f, 11.5376f, 11.3504f, 11.3776f, 11.2837f)
                curveTo(11.2176f, 11.2137f, 11.036f, 11.1787f, 10.8326f, 11.1787f)
                curveTo(10.406f, 11.1787f, 10.0676f, 11.3037f, 9.8176f, 11.5537f)
                curveTo(9.5709f, 11.8004f, 9.4176f, 12.1437f, 9.3576f, 12.5837f)
                horizontalLineTo(12.1276f)
                close()
                moveTo(12.8476f, 14.9587f)
                curveTo(12.7376f, 15.092f, 12.606f, 15.2087f, 12.4526f, 15.3087f)
                curveTo(12.2993f, 15.4054f, 12.1343f, 15.4854f, 11.9576f, 15.5487f)
                curveTo(11.7843f, 15.612f, 11.6043f, 15.6587f, 11.4176f, 15.6887f)
                curveTo(11.231f, 15.722f, 11.046f, 15.7387f, 10.8626f, 15.7387f)
                curveTo(10.5126f, 15.7387f, 10.1893f, 15.6804f, 9.8926f, 15.5637f)
                curveTo(9.5993f, 15.4437f, 9.3443f, 15.2704f, 9.1276f, 15.0437f)
                curveTo(8.9143f, 14.8137f, 8.7476f, 14.5304f, 8.6276f, 14.1937f)
                curveTo(8.5076f, 13.857f, 8.4476f, 13.4704f, 8.4476f, 13.0337f)
                curveTo(8.4476f, 12.6804f, 8.5009f, 12.3504f, 8.6076f, 12.0437f)
                curveTo(8.7176f, 11.737f, 8.8743f, 11.472f, 9.0776f, 11.2487f)
                curveTo(9.2809f, 11.022f, 9.5293f, 10.8454f, 9.8226f, 10.7187f)
                curveTo(10.116f, 10.5887f, 10.446f, 10.5237f, 10.8126f, 10.5237f)
                curveTo(11.116f, 10.5237f, 11.396f, 10.5754f, 11.6526f, 10.6787f)
                curveTo(11.9126f, 10.7787f, 12.136f, 10.9254f, 12.3226f, 11.1187f)
                curveTo(12.5126f, 11.3087f, 12.661f, 11.5454f, 12.7676f, 11.8287f)
                curveTo(12.8743f, 12.1087f, 12.9276f, 12.4287f, 12.9276f, 12.7887f)
                curveTo(12.9276f, 12.9287f, 12.9126f, 13.022f, 12.8826f, 13.0687f)
                curveTo(12.8526f, 13.1154f, 12.796f, 13.1387f, 12.7126f, 13.1387f)
                horizontalLineTo(9.3276f)
                curveTo(9.3376f, 13.4587f, 9.3809f, 13.737f, 9.4576f, 13.9737f)
                curveTo(9.5376f, 14.2104f, 9.6476f, 14.4087f, 9.7876f, 14.5687f)
                curveTo(9.9276f, 14.7254f, 10.0943f, 14.8437f, 10.2876f, 14.9237f)
                curveTo(10.481f, 15.0004f, 10.6976f, 15.0387f, 10.9376f, 15.0387f)
                curveTo(11.161f, 15.0387f, 11.3526f, 15.0137f, 11.5126f, 14.9637f)
                curveTo(11.676f, 14.9104f, 11.816f, 14.8537f, 11.9326f, 14.7937f)
                curveTo(12.0493f, 14.7337f, 12.146f, 14.6787f, 12.2226f, 14.6287f)
                curveTo(12.3026f, 14.5754f, 12.371f, 14.5487f, 12.4276f, 14.5487f)
                curveTo(12.501f, 14.5487f, 12.5576f, 14.577f, 12.5976f, 14.6337f)
                lineTo(12.8476f, 14.9587f)
                close()
                moveTo(15.8458f, 11.0937f)
                horizontalLineTo(15.2408f)
                curveTo(15.1942f, 11.0937f, 15.1558f, 11.082f, 15.1258f, 11.0587f)
                curveTo(15.0992f, 11.0354f, 15.0775f, 11.0087f, 15.0608f, 10.9787f)
                lineTo(14.5108f, 10.0737f)
                curveTo(14.4942f, 10.1404f, 14.4742f, 10.197f, 14.4508f, 10.2437f)
                lineTo(13.9758f, 10.9787f)
                curveTo(13.9592f, 11.0087f, 13.9358f, 11.0354f, 13.9058f, 11.0587f)
                curveTo(13.8792f, 11.082f, 13.8442f, 11.0937f, 13.8008f, 11.0937f)
                horizontalLineTo(13.2358f)
                lineTo(14.1158f, 9.7587f)
                lineTo(13.2708f, 8.5037f)
                horizontalLineTo(13.8808f)
                curveTo(13.9275f, 8.5037f, 13.9608f, 8.5104f, 13.9808f, 8.5237f)
                curveTo(14.0042f, 8.537f, 14.0242f, 8.5587f, 14.0408f, 8.5887f)
                lineTo(14.5858f, 9.4587f)
                curveTo(14.5958f, 9.4254f, 14.6058f, 9.3937f, 14.6158f, 9.3637f)
                curveTo(14.6258f, 9.3337f, 14.6392f, 9.302f, 14.6558f, 9.2687f)
                lineTo(15.0858f, 8.5987f)
                curveTo(15.1158f, 8.5354f, 15.1642f, 8.5037f, 15.2308f, 8.5037f)
                horizontalLineTo(15.8108f)
                lineTo(14.9658f, 9.7337f)
                lineTo(15.8458f, 11.0937f)
                close()
            }
        }
        .build()
        return _ex!!
    }

private var _ex: ImageVector? = null
