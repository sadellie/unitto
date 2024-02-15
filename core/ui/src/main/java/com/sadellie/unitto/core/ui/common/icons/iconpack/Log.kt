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
                moveTo(7.2234f, 8.3037f)
                verticalLineTo(15.6687f)
                horizontalLineTo(6.3334f)
                verticalLineTo(8.3037f)
                horizontalLineTo(7.2234f)
                close()
                moveTo(10.6017f, 10.5237f)
                curveTo(10.9717f, 10.5237f, 11.3051f, 10.5854f, 11.6017f, 10.7087f)
                curveTo(11.8984f, 10.832f, 12.1517f, 11.007f, 12.3617f, 11.2337f)
                curveTo(12.5717f, 11.4604f, 12.7317f, 11.7354f, 12.8417f, 12.0587f)
                curveTo(12.9551f, 12.3787f, 13.0117f, 12.737f, 13.0117f, 13.1337f)
                curveTo(13.0117f, 13.5337f, 12.9551f, 13.8937f, 12.8417f, 14.2137f)
                curveTo(12.7317f, 14.5337f, 12.5717f, 14.807f, 12.3617f, 15.0337f)
                curveTo(12.1517f, 15.2604f, 11.8984f, 15.4354f, 11.6017f, 15.5587f)
                curveTo(11.3051f, 15.6787f, 10.9717f, 15.7387f, 10.6017f, 15.7387f)
                curveTo(10.2284f, 15.7387f, 9.8917f, 15.6787f, 9.5917f, 15.5587f)
                curveTo(9.295f, 15.4354f, 9.0417f, 15.2604f, 8.8317f, 15.0337f)
                curveTo(8.6217f, 14.807f, 8.46f, 14.5337f, 8.3467f, 14.2137f)
                curveTo(8.2367f, 13.8937f, 8.1817f, 13.5337f, 8.1817f, 13.1337f)
                curveTo(8.1817f, 12.737f, 8.2367f, 12.3787f, 8.3467f, 12.0587f)
                curveTo(8.46f, 11.7354f, 8.6217f, 11.4604f, 8.8317f, 11.2337f)
                curveTo(9.0417f, 11.007f, 9.295f, 10.832f, 9.5917f, 10.7087f)
                curveTo(9.8917f, 10.5854f, 10.2284f, 10.5237f, 10.6017f, 10.5237f)
                close()
                moveTo(10.6017f, 15.0437f)
                curveTo(11.1017f, 15.0437f, 11.4751f, 14.877f, 11.7217f, 14.5437f)
                curveTo(11.9684f, 14.207f, 12.0917f, 13.7387f, 12.0917f, 13.1387f)
                curveTo(12.0917f, 12.5354f, 11.9684f, 12.0654f, 11.7217f, 11.7287f)
                curveTo(11.4751f, 11.392f, 11.1017f, 11.2237f, 10.6017f, 11.2237f)
                curveTo(10.3484f, 11.2237f, 10.1267f, 11.267f, 9.9367f, 11.3537f)
                curveTo(9.75f, 11.4404f, 9.5934f, 11.5654f, 9.4667f, 11.7287f)
                curveTo(9.3434f, 11.892f, 9.25f, 12.0937f, 9.1867f, 12.3337f)
                curveTo(9.1267f, 12.5704f, 9.0967f, 12.8387f, 9.0967f, 13.1387f)
                curveTo(9.0967f, 13.7387f, 9.22f, 14.207f, 9.4667f, 14.5437f)
                curveTo(9.7167f, 14.877f, 10.0951f, 15.0437f, 10.6017f, 15.0437f)
                close()
                moveTo(15.6762f, 13.1937f)
                curveTo(15.8562f, 13.1937f, 16.0146f, 13.1687f, 16.1512f, 13.1187f)
                curveTo(16.2879f, 13.0687f, 16.4029f, 12.9987f, 16.4962f, 12.9087f)
                curveTo(16.5896f, 12.8187f, 16.6596f, 12.712f, 16.7062f, 12.5887f)
                curveTo(16.7529f, 12.462f, 16.7762f, 12.3237f, 16.7762f, 12.1737f)
                curveTo(16.7762f, 11.8637f, 16.6812f, 11.617f, 16.4912f, 11.4337f)
                curveTo(16.3046f, 11.2504f, 16.0329f, 11.1587f, 15.6762f, 11.1587f)
                curveTo(15.3162f, 11.1587f, 15.0412f, 11.2504f, 14.8512f, 11.4337f)
                curveTo(14.6646f, 11.617f, 14.5712f, 11.8637f, 14.5712f, 12.1737f)
                curveTo(14.5712f, 12.3237f, 14.5946f, 12.462f, 14.6412f, 12.5887f)
                curveTo(14.6912f, 12.712f, 14.7629f, 12.8187f, 14.8562f, 12.9087f)
                curveTo(14.9496f, 12.9987f, 15.0646f, 13.0687f, 15.2012f, 13.1187f)
                curveTo(15.3379f, 13.1687f, 15.4962f, 13.1937f, 15.6762f, 13.1937f)
                close()
                moveTo(17.2812f, 15.9437f)
                curveTo(17.2812f, 15.8204f, 17.2462f, 15.7204f, 17.1762f, 15.6437f)
                curveTo(17.1062f, 15.567f, 17.0112f, 15.507f, 16.8912f, 15.4637f)
                curveTo(16.7746f, 15.4204f, 16.6379f, 15.3904f, 16.4812f, 15.3737f)
                curveTo(16.3246f, 15.3537f, 16.1579f, 15.3387f, 15.9812f, 15.3287f)
                curveTo(15.8079f, 15.3187f, 15.6312f, 15.3087f, 15.4512f, 15.2987f)
                curveTo(15.2712f, 15.2887f, 15.0979f, 15.272f, 14.9312f, 15.2487f)
                curveTo(14.7446f, 15.3354f, 14.5912f, 15.4454f, 14.4712f, 15.5787f)
                curveTo(14.3546f, 15.7087f, 14.2962f, 15.862f, 14.2962f, 16.0387f)
                curveTo(14.2962f, 16.152f, 14.3246f, 16.257f, 14.3812f, 16.3537f)
                curveTo(14.4412f, 16.4537f, 14.5312f, 16.5387f, 14.6512f, 16.6087f)
                curveTo(14.7712f, 16.682f, 14.9212f, 16.7387f, 15.1012f, 16.7787f)
                curveTo(15.2846f, 16.822f, 15.4996f, 16.8437f, 15.7462f, 16.8437f)
                curveTo(15.9862f, 16.8437f, 16.2012f, 16.822f, 16.3912f, 16.7787f)
                curveTo(16.5812f, 16.7354f, 16.7412f, 16.6737f, 16.8712f, 16.5937f)
                curveTo(17.0046f, 16.5137f, 17.1062f, 16.4187f, 17.1762f, 16.3087f)
                curveTo(17.2462f, 16.1987f, 17.2812f, 16.077f, 17.2812f, 15.9437f)
                close()
                moveTo(18.1862f, 10.8037f)
                verticalLineTo(11.1337f)
                curveTo(18.1862f, 11.2437f, 18.1162f, 11.3137f, 17.9762f, 11.3437f)
                lineTo(17.4012f, 11.4187f)
                curveTo(17.5146f, 11.6387f, 17.5712f, 11.882f, 17.5712f, 12.1487f)
                curveTo(17.5712f, 12.3954f, 17.5229f, 12.6204f, 17.4262f, 12.8237f)
                curveTo(17.3329f, 13.0237f, 17.2029f, 13.1954f, 17.0362f, 13.3387f)
                curveTo(16.8696f, 13.482f, 16.6696f, 13.592f, 16.4362f, 13.6687f)
                curveTo(16.2029f, 13.7454f, 15.9496f, 13.7837f, 15.6762f, 13.7837f)
                curveTo(15.4396f, 13.7837f, 15.2162f, 13.7554f, 15.0062f, 13.6987f)
                curveTo(14.8996f, 13.7654f, 14.8179f, 13.837f, 14.7612f, 13.9137f)
                curveTo(14.7046f, 13.987f, 14.6762f, 14.062f, 14.6762f, 14.1387f)
                curveTo(14.6762f, 14.2587f, 14.7246f, 14.3504f, 14.8212f, 14.4137f)
                curveTo(14.9212f, 14.4737f, 15.0512f, 14.517f, 15.2112f, 14.5437f)
                curveTo(15.3712f, 14.5704f, 15.5529f, 14.587f, 15.7562f, 14.5937f)
                curveTo(15.9629f, 14.6004f, 16.1729f, 14.612f, 16.3862f, 14.6287f)
                curveTo(16.6029f, 14.642f, 16.8129f, 14.667f, 17.0162f, 14.7037f)
                curveTo(17.2229f, 14.7404f, 17.4062f, 14.8004f, 17.5662f, 14.8837f)
                curveTo(17.7262f, 14.967f, 17.8546f, 15.082f, 17.9513f, 15.2287f)
                curveTo(18.0512f, 15.3754f, 18.1012f, 15.5654f, 18.1012f, 15.7987f)
                curveTo(18.1012f, 16.0154f, 18.0462f, 16.2254f, 17.9362f, 16.4287f)
                curveTo(17.8296f, 16.632f, 17.6746f, 16.812f, 17.4712f, 16.9687f)
                curveTo(17.2679f, 17.1287f, 17.0179f, 17.2554f, 16.7212f, 17.3487f)
                curveTo(16.4279f, 17.4454f, 16.0962f, 17.4937f, 15.7262f, 17.4937f)
                curveTo(15.3562f, 17.4937f, 15.0329f, 17.457f, 14.7562f, 17.3837f)
                curveTo(14.4796f, 17.3104f, 14.2496f, 17.212f, 14.0662f, 17.0887f)
                curveTo(13.8829f, 16.9654f, 13.7446f, 16.822f, 13.6512f, 16.6587f)
                curveTo(13.5612f, 16.4987f, 13.5162f, 16.3304f, 13.5162f, 16.1537f)
                curveTo(13.5162f, 15.9037f, 13.5946f, 15.692f, 13.7512f, 15.5187f)
                curveTo(13.9079f, 15.3454f, 14.1229f, 15.207f, 14.3962f, 15.1037f)
                curveTo(14.2462f, 15.037f, 14.1262f, 14.9487f, 14.0362f, 14.8387f)
                curveTo(13.9496f, 14.7254f, 13.9062f, 14.5737f, 13.9062f, 14.3837f)
                curveTo(13.9062f, 14.3104f, 13.9196f, 14.2354f, 13.9462f, 14.1587f)
                curveTo(13.9729f, 14.0787f, 14.0129f, 14.0004f, 14.0662f, 13.9237f)
                curveTo(14.1229f, 13.8437f, 14.1912f, 13.7687f, 14.2712f, 13.6987f)
                curveTo(14.3512f, 13.6287f, 14.4446f, 13.567f, 14.5512f, 13.5137f)
                curveTo(14.3012f, 13.3737f, 14.1062f, 13.1887f, 13.9662f, 12.9587f)
                curveTo(13.8262f, 12.7254f, 13.7562f, 12.4554f, 13.7562f, 12.1487f)
                curveTo(13.7562f, 11.902f, 13.8029f, 11.6787f, 13.8962f, 11.4787f)
                curveTo(13.9929f, 11.2754f, 14.1262f, 11.1037f, 14.2962f, 10.9637f)
                curveTo(14.4662f, 10.8204f, 14.6679f, 10.7104f, 14.9012f, 10.6337f)
                curveTo(15.1379f, 10.557f, 15.3962f, 10.5187f, 15.6762f, 10.5187f)
                curveTo(15.8962f, 10.5187f, 16.1012f, 10.5437f, 16.2912f, 10.5937f)
                curveTo(16.4812f, 10.6404f, 16.6546f, 10.7104f, 16.8112f, 10.8037f)
                horizontalLineTo(18.1862f)
                close()
            }
        }
        .build()
        return _log!!
    }

private var _log: ImageVector? = null
