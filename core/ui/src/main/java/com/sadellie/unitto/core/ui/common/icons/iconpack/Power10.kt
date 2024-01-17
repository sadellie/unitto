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
                moveTo(10.5634f, 14.9887f)
                verticalLineTo(15.6687f)
                horizontalLineTo(6.7234f)
                verticalLineTo(14.9887f)
                horizontalLineTo(8.2584f)
                verticalLineTo(10.1037f)
                curveTo(8.2584f, 9.957f, 8.2634f, 9.8087f, 8.2734f, 9.6587f)
                lineTo(6.9984f, 10.7537f)
                curveTo(6.9551f, 10.7904f, 6.9118f, 10.8137f, 6.8684f, 10.8237f)
                curveTo(6.8251f, 10.8304f, 6.7851f, 10.8304f, 6.7484f, 10.8237f)
                curveTo(6.7118f, 10.817f, 6.6768f, 10.8037f, 6.6434f, 10.7837f)
                curveTo(6.6134f, 10.7637f, 6.5901f, 10.742f, 6.5734f, 10.7187f)
                lineTo(6.2934f, 10.3337f)
                lineTo(8.4284f, 8.4887f)
                horizontalLineTo(9.1534f)
                verticalLineTo(14.9887f)
                horizontalLineTo(10.5634f)
                close()
                moveTo(16.5742f, 12.0887f)
                curveTo(16.5742f, 12.7154f, 16.5059f, 13.2587f, 16.3692f, 13.7187f)
                curveTo(16.2359f, 14.1754f, 16.0526f, 14.5537f, 15.8192f, 14.8537f)
                curveTo(15.5859f, 15.1537f, 15.3092f, 15.377f, 14.9892f, 15.5237f)
                curveTo(14.6726f, 15.6704f, 14.3326f, 15.7437f, 13.9692f, 15.7437f)
                curveTo(13.6026f, 15.7437f, 13.2609f, 15.6704f, 12.9442f, 15.5237f)
                curveTo(12.6309f, 15.377f, 12.3576f, 15.1537f, 12.1242f, 14.8537f)
                curveTo(11.8909f, 14.5537f, 11.7076f, 14.1754f, 11.5742f, 13.7187f)
                curveTo(11.4409f, 13.2587f, 11.3742f, 12.7154f, 11.3742f, 12.0887f)
                curveTo(11.3742f, 11.462f, 11.4409f, 10.9187f, 11.5742f, 10.4587f)
                curveTo(11.7076f, 9.9987f, 11.8909f, 9.6187f, 12.1242f, 9.3187f)
                curveTo(12.3576f, 9.0154f, 12.6309f, 8.7904f, 12.9442f, 8.6437f)
                curveTo(13.2609f, 8.497f, 13.6026f, 8.4237f, 13.9692f, 8.4237f)
                curveTo(14.3326f, 8.4237f, 14.6726f, 8.497f, 14.9892f, 8.6437f)
                curveTo(15.3092f, 8.7904f, 15.5859f, 9.0154f, 15.8192f, 9.3187f)
                curveTo(16.0526f, 9.6187f, 16.2359f, 9.9987f, 16.3692f, 10.4587f)
                curveTo(16.5059f, 10.9187f, 16.5742f, 11.462f, 16.5742f, 12.0887f)
                close()
                moveTo(15.6492f, 12.0887f)
                curveTo(15.6492f, 11.542f, 15.6026f, 11.0837f, 15.5092f, 10.7137f)
                curveTo(15.4192f, 10.3404f, 15.2959f, 10.0404f, 15.1392f, 9.8137f)
                curveTo(14.9859f, 9.587f, 14.8076f, 9.4254f, 14.6042f, 9.3287f)
                curveTo(14.4009f, 9.2287f, 14.1892f, 9.1787f, 13.9692f, 9.1787f)
                curveTo(13.7492f, 9.1787f, 13.5376f, 9.2287f, 13.3342f, 9.3287f)
                curveTo(13.1309f, 9.4254f, 12.9526f, 9.587f, 12.7992f, 9.8137f)
                curveTo(12.6459f, 10.0404f, 12.5226f, 10.3404f, 12.4292f, 10.7137f)
                curveTo(12.3392f, 11.0837f, 12.2942f, 11.542f, 12.2942f, 12.0887f)
                curveTo(12.2942f, 12.6354f, 12.3392f, 13.0937f, 12.4292f, 13.4637f)
                curveTo(12.5226f, 13.8337f, 12.6459f, 14.132f, 12.7992f, 14.3587f)
                curveTo(12.9526f, 14.5854f, 13.1309f, 14.7487f, 13.3342f, 14.8487f)
                curveTo(13.5376f, 14.9454f, 13.7492f, 14.9937f, 13.9692f, 14.9937f)
                curveTo(14.1892f, 14.9937f, 14.4009f, 14.9454f, 14.6042f, 14.8487f)
                curveTo(14.8076f, 14.7487f, 14.9859f, 14.5854f, 15.1392f, 14.3587f)
                curveTo(15.2959f, 14.132f, 15.4192f, 13.8337f, 15.5092f, 13.4637f)
                curveTo(15.6026f, 13.0937f, 15.6492f, 12.6354f, 15.6492f, 12.0887f)
                close()
                moveTo(19.405f, 11.0937f)
                horizontalLineTo(18.8f)
                curveTo(18.7533f, 11.0937f, 18.715f, 11.082f, 18.685f, 11.0587f)
                curveTo(18.6583f, 11.0354f, 18.6367f, 11.0087f, 18.62f, 10.9787f)
                lineTo(18.07f, 10.0737f)
                curveTo(18.0533f, 10.1404f, 18.0333f, 10.197f, 18.01f, 10.2437f)
                lineTo(17.535f, 10.9787f)
                curveTo(17.5183f, 11.0087f, 17.495f, 11.0354f, 17.465f, 11.0587f)
                curveTo(17.4383f, 11.082f, 17.4033f, 11.0937f, 17.36f, 11.0937f)
                horizontalLineTo(16.795f)
                lineTo(17.675f, 9.7587f)
                lineTo(16.83f, 8.5037f)
                horizontalLineTo(17.44f)
                curveTo(17.4867f, 8.5037f, 17.52f, 8.5104f, 17.54f, 8.5237f)
                curveTo(17.5633f, 8.537f, 17.5833f, 8.5587f, 17.6f, 8.5887f)
                lineTo(18.145f, 9.4587f)
                curveTo(18.155f, 9.4254f, 18.165f, 9.3937f, 18.175f, 9.3637f)
                curveTo(18.185f, 9.3337f, 18.1983f, 9.302f, 18.215f, 9.2687f)
                lineTo(18.645f, 8.5987f)
                curveTo(18.675f, 8.5354f, 18.7233f, 8.5037f, 18.79f, 8.5037f)
                horizontalLineTo(19.37f)
                lineTo(18.525f, 9.7337f)
                lineTo(19.405f, 11.0937f)
                close()
            }
        }
        .build()
        return _power10!!
    }

private var _power10: ImageVector? = null
