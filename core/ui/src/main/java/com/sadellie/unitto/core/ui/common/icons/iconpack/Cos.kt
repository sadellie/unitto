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
val IconPack.Cos: ImageVector
    get() {
        if (_cos != null) {
            return _cos!!
        }
        _cos = Builder(name = "Cos", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp, viewportWidth
                = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(9.0863f, 11.5037f)
                curveTo(9.0596f, 11.5404f, 9.0329f, 11.5687f, 9.0063f, 11.5887f)
                curveTo(8.9796f, 11.6087f, 8.9429f, 11.6187f, 8.8962f, 11.6187f)
                curveTo(8.8462f, 11.6187f, 8.7913f, 11.5987f, 8.7312f, 11.5587f)
                curveTo(8.6713f, 11.5154f, 8.5962f, 11.4687f, 8.5063f, 11.4187f)
                curveTo(8.4196f, 11.3687f, 8.3112f, 11.3237f, 8.1812f, 11.2837f)
                curveTo(8.0546f, 11.2404f, 7.8979f, 11.2187f, 7.7112f, 11.2187f)
                curveTo(7.4612f, 11.2187f, 7.2413f, 11.2637f, 7.0512f, 11.3537f)
                curveTo(6.8612f, 11.4404f, 6.7013f, 11.567f, 6.5712f, 11.7337f)
                curveTo(6.4446f, 11.9004f, 6.3479f, 12.102f, 6.2813f, 12.3387f)
                curveTo(6.2179f, 12.5754f, 6.1863f, 12.8404f, 6.1863f, 13.1337f)
                curveTo(6.1863f, 13.4404f, 6.2213f, 13.7137f, 6.2913f, 13.9537f)
                curveTo(6.3612f, 14.1904f, 6.4596f, 14.3904f, 6.5862f, 14.5537f)
                curveTo(6.7162f, 14.7137f, 6.8713f, 14.837f, 7.0512f, 14.9237f)
                curveTo(7.2346f, 15.007f, 7.4396f, 15.0487f, 7.6663f, 15.0487f)
                curveTo(7.8829f, 15.0487f, 8.0612f, 15.0237f, 8.2013f, 14.9737f)
                curveTo(8.3413f, 14.9204f, 8.4563f, 14.862f, 8.5463f, 14.7987f)
                curveTo(8.6396f, 14.7354f, 8.7163f, 14.6787f, 8.7762f, 14.6287f)
                curveTo(8.8396f, 14.5754f, 8.9012f, 14.5487f, 8.9613f, 14.5487f)
                curveTo(9.0346f, 14.5487f, 9.0913f, 14.577f, 9.1313f, 14.6337f)
                lineTo(9.3813f, 14.9587f)
                curveTo(9.2712f, 15.0954f, 9.1462f, 15.212f, 9.0063f, 15.3087f)
                curveTo(8.8663f, 15.4054f, 8.7146f, 15.487f, 8.5513f, 15.5537f)
                curveTo(8.3912f, 15.617f, 8.2229f, 15.6637f, 8.0463f, 15.6937f)
                curveTo(7.8696f, 15.7237f, 7.6896f, 15.7387f, 7.5062f, 15.7387f)
                curveTo(7.1896f, 15.7387f, 6.8946f, 15.6804f, 6.6213f, 15.5637f)
                curveTo(6.3513f, 15.447f, 6.1163f, 15.2787f, 5.9163f, 15.0587f)
                curveTo(5.7162f, 14.8354f, 5.5596f, 14.562f, 5.4462f, 14.2387f)
                curveTo(5.3329f, 13.9154f, 5.2762f, 13.547f, 5.2762f, 13.1337f)
                curveTo(5.2762f, 12.757f, 5.3279f, 12.4087f, 5.4313f, 12.0887f)
                curveTo(5.5379f, 11.7687f, 5.6912f, 11.4937f, 5.8913f, 11.2637f)
                curveTo(6.0946f, 11.0304f, 6.3429f, 10.8487f, 6.6363f, 10.7187f)
                curveTo(6.9329f, 10.5887f, 7.2729f, 10.5237f, 7.6563f, 10.5237f)
                curveTo(8.0129f, 10.5237f, 8.3263f, 10.582f, 8.5962f, 10.6987f)
                curveTo(8.8696f, 10.812f, 9.1112f, 10.9737f, 9.3212f, 11.1837f)
                lineTo(9.0863f, 11.5037f)
                close()
                moveTo(12.1838f, 10.5237f)
                curveTo(12.5538f, 10.5237f, 12.8871f, 10.5854f, 13.1838f, 10.7087f)
                curveTo(13.4804f, 10.832f, 13.7338f, 11.007f, 13.9438f, 11.2337f)
                curveTo(14.1538f, 11.4604f, 14.3138f, 11.7354f, 14.4238f, 12.0587f)
                curveTo(14.5371f, 12.3787f, 14.5938f, 12.737f, 14.5938f, 13.1337f)
                curveTo(14.5938f, 13.5337f, 14.5371f, 13.8937f, 14.4238f, 14.2137f)
                curveTo(14.3138f, 14.5337f, 14.1538f, 14.807f, 13.9438f, 15.0337f)
                curveTo(13.7338f, 15.2604f, 13.4804f, 15.4354f, 13.1838f, 15.5587f)
                curveTo(12.8871f, 15.6787f, 12.5538f, 15.7387f, 12.1838f, 15.7387f)
                curveTo(11.8104f, 15.7387f, 11.4738f, 15.6787f, 11.1738f, 15.5587f)
                curveTo(10.8771f, 15.4354f, 10.6238f, 15.2604f, 10.4138f, 15.0337f)
                curveTo(10.2038f, 14.807f, 10.0421f, 14.5337f, 9.9288f, 14.2137f)
                curveTo(9.8188f, 13.8937f, 9.7638f, 13.5337f, 9.7638f, 13.1337f)
                curveTo(9.7638f, 12.737f, 9.8188f, 12.3787f, 9.9288f, 12.0587f)
                curveTo(10.0421f, 11.7354f, 10.2038f, 11.4604f, 10.4138f, 11.2337f)
                curveTo(10.6238f, 11.007f, 10.8771f, 10.832f, 11.1738f, 10.7087f)
                curveTo(11.4738f, 10.5854f, 11.8104f, 10.5237f, 12.1838f, 10.5237f)
                close()
                moveTo(12.1838f, 15.0437f)
                curveTo(12.6838f, 15.0437f, 13.0571f, 14.877f, 13.3038f, 14.5437f)
                curveTo(13.5504f, 14.207f, 13.6738f, 13.7387f, 13.6738f, 13.1387f)
                curveTo(13.6738f, 12.5354f, 13.5504f, 12.0654f, 13.3038f, 11.7287f)
                curveTo(13.0571f, 11.392f, 12.6838f, 11.2237f, 12.1838f, 11.2237f)
                curveTo(11.9304f, 11.2237f, 11.7088f, 11.267f, 11.5188f, 11.3537f)
                curveTo(11.3321f, 11.4404f, 11.1754f, 11.5654f, 11.0488f, 11.7287f)
                curveTo(10.9254f, 11.892f, 10.8321f, 12.0937f, 10.7688f, 12.3337f)
                curveTo(10.7088f, 12.5704f, 10.6788f, 12.8387f, 10.6788f, 13.1387f)
                curveTo(10.6788f, 13.7387f, 10.8021f, 14.207f, 11.0488f, 14.5437f)
                curveTo(11.2988f, 14.877f, 11.6771f, 15.0437f, 12.1838f, 15.0437f)
                close()
                moveTo(18.4726f, 11.4387f)
                curveTo(18.4326f, 11.512f, 18.3709f, 11.5487f, 18.2876f, 11.5487f)
                curveTo(18.2376f, 11.5487f, 18.1809f, 11.5304f, 18.1176f, 11.4937f)
                curveTo(18.0542f, 11.457f, 17.9759f, 11.417f, 17.8826f, 11.3737f)
                curveTo(17.7926f, 11.327f, 17.6842f, 11.2854f, 17.5576f, 11.2487f)
                curveTo(17.4309f, 11.2087f, 17.2809f, 11.1887f, 17.1076f, 11.1887f)
                curveTo(16.9576f, 11.1887f, 16.8226f, 11.2087f, 16.7026f, 11.2487f)
                curveTo(16.5826f, 11.2854f, 16.4792f, 11.337f, 16.3926f, 11.4037f)
                curveTo(16.3092f, 11.4704f, 16.2442f, 11.5487f, 16.1976f, 11.6387f)
                curveTo(16.1542f, 11.7254f, 16.1326f, 11.8204f, 16.1326f, 11.9237f)
                curveTo(16.1326f, 12.0537f, 16.1692f, 12.162f, 16.2426f, 12.2487f)
                curveTo(16.3192f, 12.3354f, 16.4192f, 12.4104f, 16.5426f, 12.4737f)
                curveTo(16.6659f, 12.537f, 16.8059f, 12.5937f, 16.9626f, 12.6437f)
                curveTo(17.1192f, 12.6904f, 17.2792f, 12.742f, 17.4426f, 12.7987f)
                curveTo(17.6092f, 12.852f, 17.7709f, 12.912f, 17.9276f, 12.9787f)
                curveTo(18.0842f, 13.0454f, 18.2242f, 13.1287f, 18.3476f, 13.2287f)
                curveTo(18.4709f, 13.3287f, 18.5692f, 13.452f, 18.6426f, 13.5987f)
                curveTo(18.7192f, 13.742f, 18.7576f, 13.9154f, 18.7576f, 14.1187f)
                curveTo(18.7576f, 14.352f, 18.7159f, 14.5687f, 18.6326f, 14.7687f)
                curveTo(18.5492f, 14.9654f, 18.4259f, 15.137f, 18.2626f, 15.2837f)
                curveTo(18.0992f, 15.427f, 17.8992f, 15.5404f, 17.6626f, 15.6237f)
                curveTo(17.4259f, 15.707f, 17.1526f, 15.7487f, 16.8426f, 15.7487f)
                curveTo(16.4892f, 15.7487f, 16.1692f, 15.692f, 15.8826f, 15.5787f)
                curveTo(15.5959f, 15.462f, 15.3526f, 15.3137f, 15.1526f, 15.1337f)
                lineTo(15.3626f, 14.7937f)
                curveTo(15.3892f, 14.7504f, 15.4209f, 14.717f, 15.4576f, 14.6937f)
                curveTo(15.4942f, 14.6704f, 15.5409f, 14.6587f, 15.5976f, 14.6587f)
                curveTo(15.6576f, 14.6587f, 15.7209f, 14.682f, 15.7876f, 14.7287f)
                curveTo(15.8542f, 14.7754f, 15.9342f, 14.827f, 16.0276f, 14.8837f)
                curveTo(16.1242f, 14.9404f, 16.2409f, 14.992f, 16.3776f, 15.0387f)
                curveTo(16.5142f, 15.0854f, 16.6842f, 15.1087f, 16.8876f, 15.1087f)
                curveTo(17.0609f, 15.1087f, 17.2126f, 15.087f, 17.3426f, 15.0437f)
                curveTo(17.4726f, 14.997f, 17.5809f, 14.9354f, 17.6676f, 14.8587f)
                curveTo(17.7542f, 14.782f, 17.8176f, 14.6937f, 17.8576f, 14.5937f)
                curveTo(17.9009f, 14.4937f, 17.9226f, 14.387f, 17.9226f, 14.2737f)
                curveTo(17.9226f, 14.1337f, 17.8842f, 14.0187f, 17.8076f, 13.9287f)
                curveTo(17.7342f, 13.8354f, 17.6359f, 13.757f, 17.5126f, 13.6937f)
                curveTo(17.3892f, 13.627f, 17.2476f, 13.5704f, 17.0876f, 13.5237f)
                curveTo(16.9309f, 13.4737f, 16.7692f, 13.422f, 16.6026f, 13.3687f)
                curveTo(16.4392f, 13.3154f, 16.2776f, 13.2554f, 16.1176f, 13.1887f)
                curveTo(15.9609f, 13.1187f, 15.8209f, 13.032f, 15.6976f, 12.9287f)
                curveTo(15.5742f, 12.8254f, 15.4742f, 12.6987f, 15.3976f, 12.5487f)
                curveTo(15.3242f, 12.3954f, 15.2876f, 12.2104f, 15.2876f, 11.9937f)
                curveTo(15.2876f, 11.8004f, 15.3276f, 11.6154f, 15.4076f, 11.4387f)
                curveTo(15.4876f, 11.2587f, 15.6042f, 11.102f, 15.7576f, 10.9687f)
                curveTo(15.9109f, 10.832f, 16.0992f, 10.7237f, 16.3226f, 10.6437f)
                curveTo(16.5459f, 10.5637f, 16.8009f, 10.5237f, 17.0876f, 10.5237f)
                curveTo(17.4209f, 10.5237f, 17.7192f, 10.577f, 17.9826f, 10.6837f)
                curveTo(18.2492f, 10.787f, 18.4792f, 10.9304f, 18.6726f, 11.1137f)
                lineTo(18.4726f, 11.4387f)
                close()
            }
        }
        .build()
        return _cos!!
    }

private var _cos: ImageVector? = null
