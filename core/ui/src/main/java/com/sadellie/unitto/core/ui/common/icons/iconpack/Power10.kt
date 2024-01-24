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
val IconPack.Power10: ImageVector
    get() {
        if (_power10 != null) {
            return _power10!!
        }
        _power10 = Builder(name = "Power10", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(10.0634f, 14.9887f)
                verticalLineTo(15.6687f)
                horizontalLineTo(6.2234f)
                verticalLineTo(14.9887f)
                horizontalLineTo(7.7584f)
                verticalLineTo(10.1037f)
                curveTo(7.7584f, 9.957f, 7.7634f, 9.8087f, 7.7734f, 9.6587f)
                lineTo(6.4984f, 10.7537f)
                curveTo(6.4551f, 10.7904f, 6.4118f, 10.8137f, 6.3684f, 10.8237f)
                curveTo(6.3251f, 10.8304f, 6.2851f, 10.8304f, 6.2484f, 10.8237f)
                curveTo(6.2118f, 10.817f, 6.1768f, 10.8037f, 6.1434f, 10.7837f)
                curveTo(6.1134f, 10.7637f, 6.0901f, 10.742f, 6.0734f, 10.7187f)
                lineTo(5.7934f, 10.3337f)
                lineTo(7.9284f, 8.4887f)
                horizontalLineTo(8.6534f)
                verticalLineTo(14.9887f)
                horizontalLineTo(10.0634f)
                close()
                moveTo(16.0742f, 12.0887f)
                curveTo(16.0742f, 12.7154f, 16.0059f, 13.2587f, 15.8692f, 13.7187f)
                curveTo(15.7359f, 14.1754f, 15.5526f, 14.5537f, 15.3192f, 14.8537f)
                curveTo(15.0859f, 15.1537f, 14.8092f, 15.377f, 14.4892f, 15.5237f)
                curveTo(14.1726f, 15.6704f, 13.8326f, 15.7437f, 13.4692f, 15.7437f)
                curveTo(13.1026f, 15.7437f, 12.7609f, 15.6704f, 12.4442f, 15.5237f)
                curveTo(12.1309f, 15.377f, 11.8576f, 15.1537f, 11.6242f, 14.8537f)
                curveTo(11.3909f, 14.5537f, 11.2076f, 14.1754f, 11.0742f, 13.7187f)
                curveTo(10.9409f, 13.2587f, 10.8742f, 12.7154f, 10.8742f, 12.0887f)
                curveTo(10.8742f, 11.462f, 10.9409f, 10.9187f, 11.0742f, 10.4587f)
                curveTo(11.2076f, 9.9987f, 11.3909f, 9.6187f, 11.6242f, 9.3187f)
                curveTo(11.8576f, 9.0154f, 12.1309f, 8.7904f, 12.4442f, 8.6437f)
                curveTo(12.7609f, 8.497f, 13.1026f, 8.4237f, 13.4692f, 8.4237f)
                curveTo(13.8326f, 8.4237f, 14.1726f, 8.497f, 14.4892f, 8.6437f)
                curveTo(14.8092f, 8.7904f, 15.0859f, 9.0154f, 15.3192f, 9.3187f)
                curveTo(15.5526f, 9.6187f, 15.7359f, 9.9987f, 15.8692f, 10.4587f)
                curveTo(16.0059f, 10.9187f, 16.0742f, 11.462f, 16.0742f, 12.0887f)
                close()
                moveTo(15.1492f, 12.0887f)
                curveTo(15.1492f, 11.542f, 15.1026f, 11.0837f, 15.0092f, 10.7137f)
                curveTo(14.9192f, 10.3404f, 14.7959f, 10.0404f, 14.6392f, 9.8137f)
                curveTo(14.4859f, 9.587f, 14.3076f, 9.4254f, 14.1042f, 9.3287f)
                curveTo(13.9009f, 9.2287f, 13.6892f, 9.1787f, 13.4692f, 9.1787f)
                curveTo(13.2492f, 9.1787f, 13.0376f, 9.2287f, 12.8342f, 9.3287f)
                curveTo(12.6309f, 9.4254f, 12.4526f, 9.587f, 12.2992f, 9.8137f)
                curveTo(12.1459f, 10.0404f, 12.0226f, 10.3404f, 11.9292f, 10.7137f)
                curveTo(11.8392f, 11.0837f, 11.7942f, 11.542f, 11.7942f, 12.0887f)
                curveTo(11.7942f, 12.6354f, 11.8392f, 13.0937f, 11.9292f, 13.4637f)
                curveTo(12.0226f, 13.8337f, 12.1459f, 14.132f, 12.2992f, 14.3587f)
                curveTo(12.4526f, 14.5854f, 12.6309f, 14.7487f, 12.8342f, 14.8487f)
                curveTo(13.0376f, 14.9454f, 13.2492f, 14.9937f, 13.4692f, 14.9937f)
                curveTo(13.6892f, 14.9937f, 13.9009f, 14.9454f, 14.1042f, 14.8487f)
                curveTo(14.3076f, 14.7487f, 14.4859f, 14.5854f, 14.6392f, 14.3587f)
                curveTo(14.7959f, 14.132f, 14.9192f, 13.8337f, 15.0092f, 13.4637f)
                curveTo(15.1026f, 13.0937f, 15.1492f, 12.6354f, 15.1492f, 12.0887f)
                close()
                moveTo(18.905f, 11.0937f)
                horizontalLineTo(18.3f)
                curveTo(18.2533f, 11.0937f, 18.215f, 11.082f, 18.185f, 11.0587f)
                curveTo(18.1583f, 11.0354f, 18.1367f, 11.0087f, 18.12f, 10.9787f)
                lineTo(17.57f, 10.0737f)
                curveTo(17.5533f, 10.1404f, 17.5333f, 10.197f, 17.51f, 10.2437f)
                lineTo(17.035f, 10.9787f)
                curveTo(17.0183f, 11.0087f, 16.995f, 11.0354f, 16.965f, 11.0587f)
                curveTo(16.9383f, 11.082f, 16.9033f, 11.0937f, 16.86f, 11.0937f)
                horizontalLineTo(16.295f)
                lineTo(17.175f, 9.7587f)
                lineTo(16.33f, 8.5037f)
                horizontalLineTo(16.94f)
                curveTo(16.9867f, 8.5037f, 17.02f, 8.5104f, 17.04f, 8.5237f)
                curveTo(17.0633f, 8.537f, 17.0833f, 8.5587f, 17.1f, 8.5887f)
                lineTo(17.645f, 9.4587f)
                curveTo(17.655f, 9.4254f, 17.665f, 9.3937f, 17.675f, 9.3637f)
                curveTo(17.685f, 9.3337f, 17.6983f, 9.302f, 17.715f, 9.2687f)
                lineTo(18.145f, 8.5987f)
                curveTo(18.175f, 8.5354f, 18.2233f, 8.5037f, 18.29f, 8.5037f)
                horizontalLineTo(18.87f)
                lineTo(18.025f, 9.7337f)
                lineTo(18.905f, 11.0937f)
                close()
            }
        }
        .build()
        return _power10!!
    }

private var _power10: ImageVector? = null
