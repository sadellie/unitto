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
val IconPack.Pop: ImageVector
    get() {
        if (_pop != null) {
            return _pop!!
        }
        _pop = Builder(name = "Pop", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp, viewportWidth
                = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(5.2202f, 12.2187f)
                curveTo(5.4969f, 12.2187f, 5.7402f, 12.182f, 5.9502f, 12.1087f)
                curveTo(6.1636f, 12.0354f, 6.3419f, 11.9337f, 6.4852f, 11.8037f)
                curveTo(6.6319f, 11.6704f, 6.7419f, 11.512f, 6.8152f, 11.3287f)
                curveTo(6.8886f, 11.1454f, 6.9252f, 10.9437f, 6.9252f, 10.7237f)
                curveTo(6.9252f, 10.267f, 6.7836f, 9.9104f, 6.5002f, 9.6537f)
                curveTo(6.2202f, 9.397f, 5.7936f, 9.2687f, 5.2202f, 9.2687f)
                horizontalLineTo(4.0702f)
                verticalLineTo(12.2187f)
                horizontalLineTo(5.2202f)
                close()
                moveTo(5.2202f, 8.5037f)
                curveTo(5.6736f, 8.5037f, 6.0669f, 8.557f, 6.4002f, 8.6637f)
                curveTo(6.7369f, 8.767f, 7.0152f, 8.9154f, 7.2352f, 9.1087f)
                curveTo(7.4552f, 9.302f, 7.6186f, 9.5354f, 7.7252f, 9.8087f)
                curveTo(7.8352f, 10.082f, 7.8902f, 10.387f, 7.8902f, 10.7237f)
                curveTo(7.8902f, 11.057f, 7.8319f, 11.362f, 7.7152f, 11.6387f)
                curveTo(7.5986f, 11.9154f, 7.4269f, 12.1537f, 7.2002f, 12.3537f)
                curveTo(6.9769f, 12.5537f, 6.6986f, 12.7104f, 6.3652f, 12.8237f)
                curveTo(6.0352f, 12.9337f, 5.6536f, 12.9887f, 5.2202f, 12.9887f)
                horizontalLineTo(4.0702f)
                verticalLineTo(15.6687f)
                horizontalLineTo(3.1052f)
                verticalLineTo(8.5037f)
                horizontalLineTo(5.2202f)
                close()
                moveTo(15.5311f, 12.0887f)
                curveTo(15.5311f, 12.6254f, 15.4461f, 13.1187f, 15.2761f, 13.5687f)
                curveTo(15.1061f, 14.0154f, 14.8661f, 14.4004f, 14.5561f, 14.7237f)
                curveTo(14.2461f, 15.047f, 13.8728f, 15.2987f, 13.4361f, 15.4787f)
                curveTo(13.0028f, 15.6554f, 12.5228f, 15.7437f, 11.9961f, 15.7437f)
                curveTo(11.4694f, 15.7437f, 10.9894f, 15.6554f, 10.5561f, 15.4787f)
                curveTo(10.1228f, 15.2987f, 9.7511f, 15.047f, 9.4411f, 14.7237f)
                curveTo(9.1311f, 14.4004f, 8.8911f, 14.0154f, 8.7211f, 13.5687f)
                curveTo(8.5511f, 13.1187f, 8.4661f, 12.6254f, 8.4661f, 12.0887f)
                curveTo(8.4661f, 11.552f, 8.5511f, 11.0604f, 8.7211f, 10.6137f)
                curveTo(8.8911f, 10.1637f, 9.1311f, 9.777f, 9.4411f, 9.4537f)
                curveTo(9.7511f, 9.127f, 10.1228f, 8.8737f, 10.5561f, 8.6937f)
                curveTo(10.9894f, 8.5137f, 11.4694f, 8.4237f, 11.9961f, 8.4237f)
                curveTo(12.5228f, 8.4237f, 13.0028f, 8.5137f, 13.4361f, 8.6937f)
                curveTo(13.8728f, 8.8737f, 14.2461f, 9.127f, 14.5561f, 9.4537f)
                curveTo(14.8661f, 9.777f, 15.1061f, 10.1637f, 15.2761f, 10.6137f)
                curveTo(15.4461f, 11.0604f, 15.5311f, 11.552f, 15.5311f, 12.0887f)
                close()
                moveTo(14.5361f, 12.0887f)
                curveTo(14.5361f, 11.6487f, 14.4761f, 11.2537f, 14.3561f, 10.9037f)
                curveTo(14.2361f, 10.5537f, 14.0661f, 10.2587f, 13.8461f, 10.0187f)
                curveTo(13.6261f, 9.7754f, 13.3594f, 9.5887f, 13.0461f, 9.4587f)
                curveTo(12.7328f, 9.3287f, 12.3828f, 9.2637f, 11.9961f, 9.2637f)
                curveTo(11.6128f, 9.2637f, 11.2644f, 9.3287f, 10.9511f, 9.4587f)
                curveTo(10.6378f, 9.5887f, 10.3694f, 9.7754f, 10.1461f, 10.0187f)
                curveTo(9.9261f, 10.2587f, 9.7561f, 10.5537f, 9.6361f, 10.9037f)
                curveTo(9.5161f, 11.2537f, 9.4561f, 11.6487f, 9.4561f, 12.0887f)
                curveTo(9.4561f, 12.5287f, 9.5161f, 12.9237f, 9.6361f, 13.2737f)
                curveTo(9.7561f, 13.6204f, 9.9261f, 13.9154f, 10.1461f, 14.1587f)
                curveTo(10.3694f, 14.3987f, 10.6378f, 14.5837f, 10.9511f, 14.7137f)
                curveTo(11.2644f, 14.8404f, 11.6128f, 14.9037f, 11.9961f, 14.9037f)
                curveTo(12.3828f, 14.9037f, 12.7328f, 14.8404f, 13.0461f, 14.7137f)
                curveTo(13.3594f, 14.5837f, 13.6261f, 14.3987f, 13.8461f, 14.1587f)
                curveTo(14.0661f, 13.9154f, 14.2361f, 13.6204f, 14.3561f, 13.2737f)
                curveTo(14.4761f, 12.9237f, 14.5361f, 12.5287f, 14.5361f, 12.0887f)
                close()
                moveTo(18.8339f, 12.2187f)
                curveTo(19.1106f, 12.2187f, 19.3539f, 12.182f, 19.5639f, 12.1087f)
                curveTo(19.7772f, 12.0354f, 19.9556f, 11.9337f, 20.0989f, 11.8037f)
                curveTo(20.2456f, 11.6704f, 20.3556f, 11.512f, 20.4289f, 11.3287f)
                curveTo(20.5022f, 11.1454f, 20.5389f, 10.9437f, 20.5389f, 10.7237f)
                curveTo(20.5389f, 10.267f, 20.3972f, 9.9104f, 20.1139f, 9.6537f)
                curveTo(19.8339f, 9.397f, 19.4072f, 9.2687f, 18.8339f, 9.2687f)
                horizontalLineTo(17.6839f)
                verticalLineTo(12.2187f)
                horizontalLineTo(18.8339f)
                close()
                moveTo(18.8339f, 8.5037f)
                curveTo(19.2872f, 8.5037f, 19.6806f, 8.557f, 20.0139f, 8.6637f)
                curveTo(20.3506f, 8.767f, 20.6289f, 8.9154f, 20.8489f, 9.1087f)
                curveTo(21.0689f, 9.302f, 21.2322f, 9.5354f, 21.3389f, 9.8087f)
                curveTo(21.4489f, 10.082f, 21.5039f, 10.387f, 21.5039f, 10.7237f)
                curveTo(21.5039f, 11.057f, 21.4456f, 11.362f, 21.3289f, 11.6387f)
                curveTo(21.2122f, 11.9154f, 21.0406f, 12.1537f, 20.8139f, 12.3537f)
                curveTo(20.5906f, 12.5537f, 20.3122f, 12.7104f, 19.9789f, 12.8237f)
                curveTo(19.6489f, 12.9337f, 19.2672f, 12.9887f, 18.8339f, 12.9887f)
                horizontalLineTo(17.6839f)
                verticalLineTo(15.6687f)
                horizontalLineTo(16.7189f)
                verticalLineTo(8.5037f)
                horizontalLineTo(18.8339f)
                close()
            }
        }
        .build()
        return _pop!!
    }

private var _pop: ImageVector? = null
