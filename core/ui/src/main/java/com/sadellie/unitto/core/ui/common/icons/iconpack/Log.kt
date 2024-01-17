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
val IconPack.Log: ImageVector
    get() {
        if (_log != null) {
            return _log!!
        }
        _log = Builder(name = "Log", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp, viewportWidth
                = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(7.7234f, 8.3037f)
                verticalLineTo(15.6687f)
                horizontalLineTo(6.8334f)
                verticalLineTo(8.3037f)
                horizontalLineTo(7.7234f)
                close()
                moveTo(11.1017f, 10.5237f)
                curveTo(11.4717f, 10.5237f, 11.8051f, 10.5854f, 12.1017f, 10.7087f)
                curveTo(12.3984f, 10.832f, 12.6517f, 11.007f, 12.8617f, 11.2337f)
                curveTo(13.0717f, 11.4604f, 13.2317f, 11.7354f, 13.3417f, 12.0587f)
                curveTo(13.4551f, 12.3787f, 13.5117f, 12.737f, 13.5117f, 13.1337f)
                curveTo(13.5117f, 13.5337f, 13.4551f, 13.8937f, 13.3417f, 14.2137f)
                curveTo(13.2317f, 14.5337f, 13.0717f, 14.807f, 12.8617f, 15.0337f)
                curveTo(12.6517f, 15.2604f, 12.3984f, 15.4354f, 12.1017f, 15.5587f)
                curveTo(11.8051f, 15.6787f, 11.4717f, 15.7387f, 11.1017f, 15.7387f)
                curveTo(10.7284f, 15.7387f, 10.3917f, 15.6787f, 10.0917f, 15.5587f)
                curveTo(9.795f, 15.4354f, 9.5417f, 15.2604f, 9.3317f, 15.0337f)
                curveTo(9.1217f, 14.807f, 8.96f, 14.5337f, 8.8467f, 14.2137f)
                curveTo(8.7367f, 13.8937f, 8.6817f, 13.5337f, 8.6817f, 13.1337f)
                curveTo(8.6817f, 12.737f, 8.7367f, 12.3787f, 8.8467f, 12.0587f)
                curveTo(8.96f, 11.7354f, 9.1217f, 11.4604f, 9.3317f, 11.2337f)
                curveTo(9.5417f, 11.007f, 9.795f, 10.832f, 10.0917f, 10.7087f)
                curveTo(10.3917f, 10.5854f, 10.7284f, 10.5237f, 11.1017f, 10.5237f)
                close()
                moveTo(11.1017f, 15.0437f)
                curveTo(11.6017f, 15.0437f, 11.9751f, 14.877f, 12.2217f, 14.5437f)
                curveTo(12.4684f, 14.207f, 12.5917f, 13.7387f, 12.5917f, 13.1387f)
                curveTo(12.5917f, 12.5354f, 12.4684f, 12.0654f, 12.2217f, 11.7287f)
                curveTo(11.9751f, 11.392f, 11.6017f, 11.2237f, 11.1017f, 11.2237f)
                curveTo(10.8484f, 11.2237f, 10.6267f, 11.267f, 10.4367f, 11.3537f)
                curveTo(10.2501f, 11.4404f, 10.0934f, 11.5654f, 9.9667f, 11.7287f)
                curveTo(9.8434f, 11.892f, 9.75f, 12.0937f, 9.6867f, 12.3337f)
                curveTo(9.6267f, 12.5704f, 9.5967f, 12.8387f, 9.5967f, 13.1387f)
                curveTo(9.5967f, 13.7387f, 9.72f, 14.207f, 9.9667f, 14.5437f)
                curveTo(10.2167f, 14.877f, 10.5951f, 15.0437f, 11.1017f, 15.0437f)
                close()
                moveTo(16.1762f, 13.1937f)
                curveTo(16.3562f, 13.1937f, 16.5146f, 13.1687f, 16.6512f, 13.1187f)
                curveTo(16.7879f, 13.0687f, 16.9029f, 12.9987f, 16.9962f, 12.9087f)
                curveTo(17.0896f, 12.8187f, 17.1596f, 12.712f, 17.2062f, 12.5887f)
                curveTo(17.2529f, 12.462f, 17.2762f, 12.3237f, 17.2762f, 12.1737f)
                curveTo(17.2762f, 11.8637f, 17.1812f, 11.617f, 16.9912f, 11.4337f)
                curveTo(16.8046f, 11.2504f, 16.5329f, 11.1587f, 16.1762f, 11.1587f)
                curveTo(15.8162f, 11.1587f, 15.5412f, 11.2504f, 15.3512f, 11.4337f)
                curveTo(15.1646f, 11.617f, 15.0712f, 11.8637f, 15.0712f, 12.1737f)
                curveTo(15.0712f, 12.3237f, 15.0946f, 12.462f, 15.1412f, 12.5887f)
                curveTo(15.1912f, 12.712f, 15.2629f, 12.8187f, 15.3562f, 12.9087f)
                curveTo(15.4496f, 12.9987f, 15.5646f, 13.0687f, 15.7012f, 13.1187f)
                curveTo(15.8379f, 13.1687f, 15.9962f, 13.1937f, 16.1762f, 13.1937f)
                close()
                moveTo(17.7812f, 15.9437f)
                curveTo(17.7812f, 15.8204f, 17.7462f, 15.7204f, 17.6762f, 15.6437f)
                curveTo(17.6062f, 15.567f, 17.5112f, 15.507f, 17.3912f, 15.4637f)
                curveTo(17.2746f, 15.4204f, 17.1379f, 15.3904f, 16.9812f, 15.3737f)
                curveTo(16.8246f, 15.3537f, 16.6579f, 15.3387f, 16.4812f, 15.3287f)
                curveTo(16.3079f, 15.3187f, 16.1312f, 15.3087f, 15.9512f, 15.2987f)
                curveTo(15.7712f, 15.2887f, 15.5979f, 15.272f, 15.4312f, 15.2487f)
                curveTo(15.2446f, 15.3354f, 15.0912f, 15.4454f, 14.9712f, 15.5787f)
                curveTo(14.8546f, 15.7087f, 14.7962f, 15.862f, 14.7962f, 16.0387f)
                curveTo(14.7962f, 16.152f, 14.8246f, 16.257f, 14.8812f, 16.3537f)
                curveTo(14.9412f, 16.4537f, 15.0312f, 16.5387f, 15.1512f, 16.6087f)
                curveTo(15.2712f, 16.682f, 15.4212f, 16.7387f, 15.6012f, 16.7787f)
                curveTo(15.7846f, 16.822f, 15.9996f, 16.8437f, 16.2462f, 16.8437f)
                curveTo(16.4862f, 16.8437f, 16.7012f, 16.822f, 16.8912f, 16.7787f)
                curveTo(17.0812f, 16.7354f, 17.2412f, 16.6737f, 17.3712f, 16.5937f)
                curveTo(17.5046f, 16.5137f, 17.6062f, 16.4187f, 17.6762f, 16.3087f)
                curveTo(17.7462f, 16.1987f, 17.7812f, 16.077f, 17.7812f, 15.9437f)
                close()
                moveTo(18.6862f, 10.8037f)
                verticalLineTo(11.1337f)
                curveTo(18.6862f, 11.2437f, 18.6162f, 11.3137f, 18.4762f, 11.3437f)
                lineTo(17.9012f, 11.4187f)
                curveTo(18.0146f, 11.6387f, 18.0712f, 11.882f, 18.0712f, 12.1487f)
                curveTo(18.0712f, 12.3954f, 18.0229f, 12.6204f, 17.9262f, 12.8237f)
                curveTo(17.8329f, 13.0237f, 17.7029f, 13.1954f, 17.5362f, 13.3387f)
                curveTo(17.3696f, 13.482f, 17.1696f, 13.592f, 16.9362f, 13.6687f)
                curveTo(16.7029f, 13.7454f, 16.4496f, 13.7837f, 16.1762f, 13.7837f)
                curveTo(15.9396f, 13.7837f, 15.7162f, 13.7554f, 15.5062f, 13.6987f)
                curveTo(15.3996f, 13.7654f, 15.3179f, 13.837f, 15.2612f, 13.9137f)
                curveTo(15.2046f, 13.987f, 15.1762f, 14.062f, 15.1762f, 14.1387f)
                curveTo(15.1762f, 14.2587f, 15.2246f, 14.3504f, 15.3212f, 14.4137f)
                curveTo(15.4212f, 14.4737f, 15.5512f, 14.517f, 15.7112f, 14.5437f)
                curveTo(15.8712f, 14.5704f, 16.0529f, 14.587f, 16.2562f, 14.5937f)
                curveTo(16.4629f, 14.6004f, 16.6729f, 14.612f, 16.8862f, 14.6287f)
                curveTo(17.1029f, 14.642f, 17.3129f, 14.667f, 17.5162f, 14.7037f)
                curveTo(17.7229f, 14.7404f, 17.9062f, 14.8004f, 18.0662f, 14.8837f)
                curveTo(18.2262f, 14.967f, 18.3546f, 15.082f, 18.4513f, 15.2287f)
                curveTo(18.5512f, 15.3754f, 18.6012f, 15.5654f, 18.6012f, 15.7987f)
                curveTo(18.6012f, 16.0154f, 18.5462f, 16.2254f, 18.4362f, 16.4287f)
                curveTo(18.3296f, 16.632f, 18.1746f, 16.812f, 17.9712f, 16.9687f)
                curveTo(17.7679f, 17.1287f, 17.5179f, 17.2554f, 17.2212f, 17.3487f)
                curveTo(16.9279f, 17.4454f, 16.5962f, 17.4937f, 16.2262f, 17.4937f)
                curveTo(15.8562f, 17.4937f, 15.5329f, 17.457f, 15.2562f, 17.3837f)
                curveTo(14.9796f, 17.3104f, 14.7496f, 17.212f, 14.5662f, 17.0887f)
                curveTo(14.3829f, 16.9654f, 14.2446f, 16.822f, 14.1512f, 16.6587f)
                curveTo(14.0612f, 16.4987f, 14.0162f, 16.3304f, 14.0162f, 16.1537f)
                curveTo(14.0162f, 15.9037f, 14.0946f, 15.692f, 14.2512f, 15.5187f)
                curveTo(14.4079f, 15.3454f, 14.6229f, 15.207f, 14.8962f, 15.1037f)
                curveTo(14.7462f, 15.037f, 14.6262f, 14.9487f, 14.5362f, 14.8387f)
                curveTo(14.4496f, 14.7254f, 14.4062f, 14.5737f, 14.4062f, 14.3837f)
                curveTo(14.4062f, 14.3104f, 14.4196f, 14.2354f, 14.4462f, 14.1587f)
                curveTo(14.4729f, 14.0787f, 14.5129f, 14.0004f, 14.5662f, 13.9237f)
                curveTo(14.6229f, 13.8437f, 14.6912f, 13.7687f, 14.7712f, 13.6987f)
                curveTo(14.8512f, 13.6287f, 14.9446f, 13.567f, 15.0512f, 13.5137f)
                curveTo(14.8012f, 13.3737f, 14.6062f, 13.1887f, 14.4662f, 12.9587f)
                curveTo(14.3262f, 12.7254f, 14.2562f, 12.4554f, 14.2562f, 12.1487f)
                curveTo(14.2562f, 11.902f, 14.3029f, 11.6787f, 14.3962f, 11.4787f)
                curveTo(14.4929f, 11.2754f, 14.6262f, 11.1037f, 14.7962f, 10.9637f)
                curveTo(14.9662f, 10.8204f, 15.1679f, 10.7104f, 15.4012f, 10.6337f)
                curveTo(15.6379f, 10.557f, 15.8962f, 10.5187f, 16.1762f, 10.5187f)
                curveTo(16.3962f, 10.5187f, 16.6012f, 10.5437f, 16.7912f, 10.5937f)
                curveTo(16.9812f, 10.6404f, 17.1546f, 10.7104f, 17.3112f, 10.8037f)
                horizontalLineTo(18.6862f)
                close()
            }
        }
        .build()
        return _log!!
    }

private var _log: ImageVector? = null
