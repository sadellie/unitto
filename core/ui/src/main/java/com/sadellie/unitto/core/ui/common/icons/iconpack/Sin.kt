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
val IconPack.Sin: ImageVector
    get() {
        if (_sin != null) {
            return _sin!!
        }
        _sin = Builder(name = "Sin", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp, viewportWidth
                = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(9.7027f, 11.4387f)
                curveTo(9.6627f, 11.512f, 9.601f, 11.5487f, 9.5177f, 11.5487f)
                curveTo(9.4677f, 11.5487f, 9.411f, 11.5304f, 9.3477f, 11.4937f)
                curveTo(9.2843f, 11.457f, 9.206f, 11.417f, 9.1127f, 11.3737f)
                curveTo(9.0227f, 11.327f, 8.9143f, 11.2854f, 8.7877f, 11.2487f)
                curveTo(8.661f, 11.2087f, 8.511f, 11.1887f, 8.3377f, 11.1887f)
                curveTo(8.1877f, 11.1887f, 8.0527f, 11.2087f, 7.9327f, 11.2487f)
                curveTo(7.8127f, 11.2854f, 7.7093f, 11.337f, 7.6227f, 11.4037f)
                curveTo(7.5393f, 11.4704f, 7.4743f, 11.5487f, 7.4277f, 11.6387f)
                curveTo(7.3843f, 11.7254f, 7.3627f, 11.8204f, 7.3627f, 11.9237f)
                curveTo(7.3627f, 12.0537f, 7.3993f, 12.162f, 7.4727f, 12.2487f)
                curveTo(7.5493f, 12.3354f, 7.6493f, 12.4104f, 7.7727f, 12.4737f)
                curveTo(7.896f, 12.537f, 8.036f, 12.5937f, 8.1927f, 12.6437f)
                curveTo(8.3493f, 12.6904f, 8.5093f, 12.742f, 8.6727f, 12.7987f)
                curveTo(8.8393f, 12.852f, 9.001f, 12.912f, 9.1577f, 12.9787f)
                curveTo(9.3143f, 13.0454f, 9.4543f, 13.1287f, 9.5777f, 13.2287f)
                curveTo(9.701f, 13.3287f, 9.7993f, 13.452f, 9.8727f, 13.5987f)
                curveTo(9.9493f, 13.742f, 9.9877f, 13.9154f, 9.9877f, 14.1187f)
                curveTo(9.9877f, 14.352f, 9.946f, 14.5687f, 9.8627f, 14.7687f)
                curveTo(9.7793f, 14.9654f, 9.656f, 15.137f, 9.4927f, 15.2837f)
                curveTo(9.3293f, 15.427f, 9.1293f, 15.5404f, 8.8927f, 15.6237f)
                curveTo(8.656f, 15.707f, 8.3827f, 15.7487f, 8.0727f, 15.7487f)
                curveTo(7.7193f, 15.7487f, 7.3993f, 15.692f, 7.1127f, 15.5787f)
                curveTo(6.826f, 15.462f, 6.5827f, 15.3137f, 6.3827f, 15.1337f)
                lineTo(6.5927f, 14.7937f)
                curveTo(6.6193f, 14.7504f, 6.651f, 14.717f, 6.6877f, 14.6937f)
                curveTo(6.7243f, 14.6704f, 6.771f, 14.6587f, 6.8277f, 14.6587f)
                curveTo(6.8877f, 14.6587f, 6.951f, 14.682f, 7.0177f, 14.7287f)
                curveTo(7.0843f, 14.7754f, 7.1643f, 14.827f, 7.2577f, 14.8837f)
                curveTo(7.3543f, 14.9404f, 7.471f, 14.992f, 7.6077f, 15.0387f)
                curveTo(7.7443f, 15.0854f, 7.9143f, 15.1087f, 8.1177f, 15.1087f)
                curveTo(8.291f, 15.1087f, 8.4427f, 15.087f, 8.5727f, 15.0437f)
                curveTo(8.7027f, 14.997f, 8.811f, 14.9354f, 8.8977f, 14.8587f)
                curveTo(8.9843f, 14.782f, 9.0477f, 14.6937f, 9.0877f, 14.5937f)
                curveTo(9.131f, 14.4937f, 9.1527f, 14.387f, 9.1527f, 14.2737f)
                curveTo(9.1527f, 14.1337f, 9.1143f, 14.0187f, 9.0377f, 13.9287f)
                curveTo(8.9643f, 13.8354f, 8.866f, 13.757f, 8.7427f, 13.6937f)
                curveTo(8.6193f, 13.627f, 8.4777f, 13.5704f, 8.3177f, 13.5237f)
                curveTo(8.161f, 13.4737f, 7.9993f, 13.422f, 7.8327f, 13.3687f)
                curveTo(7.6693f, 13.3154f, 7.5077f, 13.2554f, 7.3477f, 13.1887f)
                curveTo(7.191f, 13.1187f, 7.051f, 13.032f, 6.9277f, 12.9287f)
                curveTo(6.8043f, 12.8254f, 6.7043f, 12.6987f, 6.6277f, 12.5487f)
                curveTo(6.5543f, 12.3954f, 6.5177f, 12.2104f, 6.5177f, 11.9937f)
                curveTo(6.5177f, 11.8004f, 6.5577f, 11.6154f, 6.6377f, 11.4387f)
                curveTo(6.7177f, 11.2587f, 6.8343f, 11.102f, 6.9877f, 10.9687f)
                curveTo(7.141f, 10.832f, 7.3293f, 10.7237f, 7.5527f, 10.6437f)
                curveTo(7.776f, 10.5637f, 8.031f, 10.5237f, 8.3177f, 10.5237f)
                curveTo(8.651f, 10.5237f, 8.9493f, 10.577f, 9.2127f, 10.6837f)
                curveTo(9.4793f, 10.787f, 9.7093f, 10.9304f, 9.9027f, 11.1137f)
                lineTo(9.7027f, 11.4387f)
                close()
                moveTo(11.8188f, 10.6037f)
                verticalLineTo(15.6687f)
                horizontalLineTo(10.9288f)
                verticalLineTo(10.6037f)
                horizontalLineTo(11.8188f)
                close()
                moveTo(12.0088f, 9.0137f)
                curveTo(12.0088f, 9.1004f, 11.9905f, 9.182f, 11.9538f, 9.2587f)
                curveTo(11.9205f, 9.332f, 11.8738f, 9.3987f, 11.8138f, 9.4587f)
                curveTo(11.7572f, 9.5154f, 11.6888f, 9.5604f, 11.6088f, 9.5937f)
                curveTo(11.5322f, 9.627f, 11.4505f, 9.6437f, 11.3638f, 9.6437f)
                curveTo(11.2772f, 9.6437f, 11.1955f, 9.627f, 11.1188f, 9.5937f)
                curveTo(11.0455f, 9.5604f, 10.9805f, 9.5154f, 10.9238f, 9.4587f)
                curveTo(10.8672f, 9.3987f, 10.8222f, 9.332f, 10.7888f, 9.2587f)
                curveTo(10.7555f, 9.182f, 10.7388f, 9.1004f, 10.7388f, 9.0137f)
                curveTo(10.7388f, 8.927f, 10.7555f, 8.8454f, 10.7888f, 8.7687f)
                curveTo(10.8222f, 8.6887f, 10.8672f, 8.6204f, 10.9238f, 8.5637f)
                curveTo(10.9805f, 8.5037f, 11.0455f, 8.457f, 11.1188f, 8.4237f)
                curveTo(11.1955f, 8.3904f, 11.2772f, 8.3737f, 11.3638f, 8.3737f)
                curveTo(11.4505f, 8.3737f, 11.5322f, 8.3904f, 11.6088f, 8.4237f)
                curveTo(11.6888f, 8.457f, 11.7572f, 8.5037f, 11.8138f, 8.5637f)
                curveTo(11.8738f, 8.6204f, 11.9205f, 8.6887f, 11.9538f, 8.7687f)
                curveTo(11.9905f, 8.8454f, 12.0088f, 8.927f, 12.0088f, 9.0137f)
                close()
                moveTo(13.9212f, 11.3387f)
                curveTo(14.0312f, 11.2154f, 14.1478f, 11.1037f, 14.2712f, 11.0037f)
                curveTo(14.3945f, 10.9037f, 14.5245f, 10.8187f, 14.6612f, 10.7487f)
                curveTo(14.8012f, 10.6754f, 14.9478f, 10.6204f, 15.1012f, 10.5837f)
                curveTo(15.2578f, 10.5437f, 15.4262f, 10.5237f, 15.6062f, 10.5237f)
                curveTo(15.8828f, 10.5237f, 16.1262f, 10.5704f, 16.3362f, 10.6637f)
                curveTo(16.5495f, 10.7537f, 16.7262f, 10.8837f, 16.8662f, 11.0537f)
                curveTo(17.0095f, 11.2204f, 17.1178f, 11.422f, 17.1912f, 11.6587f)
                curveTo(17.2645f, 11.8954f, 17.3012f, 12.157f, 17.3012f, 12.4437f)
                verticalLineTo(15.6687f)
                horizontalLineTo(16.4062f)
                verticalLineTo(12.4437f)
                curveTo(16.4062f, 12.0604f, 16.3178f, 11.7637f, 16.1412f, 11.5537f)
                curveTo(15.9678f, 11.3404f, 15.7028f, 11.2337f, 15.3462f, 11.2337f)
                curveTo(15.0828f, 11.2337f, 14.8362f, 11.297f, 14.6062f, 11.4237f)
                curveTo(14.3795f, 11.5504f, 14.1695f, 11.722f, 13.9762f, 11.9387f)
                verticalLineTo(15.6687f)
                horizontalLineTo(13.0812f)
                verticalLineTo(10.6037f)
                horizontalLineTo(13.6162f)
                curveTo(13.7428f, 10.6037f, 13.8212f, 10.6654f, 13.8512f, 10.7887f)
                lineTo(13.9212f, 11.3387f)
                close()
            }
        }
        .build()
        return _sin!!
    }

private var _sin: ImageVector? = null
