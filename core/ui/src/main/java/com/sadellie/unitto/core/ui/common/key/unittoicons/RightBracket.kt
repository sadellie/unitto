package com.sadellie.unitto.core.ui.common.key.unittoicons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.ui.common.key.UnittoIcons

val @receiver:Suppress("UNUSED") UnittoIcons.RightBracket: ImageVector
    get() {
        if (_rightbracket != null) {
            return _rightbracket!!
        }
        _rightbracket = Builder(name = "RightBracket", defaultWidth = 150.0.dp, defaultHeight =
                150.0.dp, viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(74.142f, 84.872f)
                curveTo(74.142f, 76.083f, 73.075f, 67.443f, 70.941f, 58.952f)
                curveTo(68.851f, 50.419f, 65.822f, 42.44f, 61.854f, 35.016f)
                curveTo(61.384f, 34.12f, 61.107f, 33.352f, 61.021f, 32.712f)
                curveTo(60.979f, 32.029f, 61.043f, 31.453f, 61.214f, 30.984f)
                curveTo(61.384f, 30.472f, 61.64f, 30.045f, 61.981f, 29.704f)
                curveTo(62.366f, 29.32f, 62.792f, 29.0f, 63.262f, 28.744f)
                lineTo(70.301f, 24.52f)
                curveTo(73.459f, 29.384f, 76.147f, 34.269f, 78.366f, 39.176f)
                curveTo(80.584f, 44.04f, 82.397f, 48.968f, 83.805f, 53.96f)
                curveTo(85.214f, 58.952f, 86.238f, 64.029f, 86.878f, 69.192f)
                curveTo(87.518f, 74.312f, 87.838f, 79.539f, 87.838f, 84.872f)
                curveTo(87.838f, 90.205f, 87.518f, 95.432f, 86.878f, 100.552f)
                curveTo(86.238f, 105.672f, 85.214f, 110.728f, 83.805f, 115.72f)
                curveTo(82.397f, 120.712f, 80.584f, 125.661f, 78.366f, 130.568f)
                curveTo(76.147f, 135.475f, 73.459f, 140.36f, 70.301f, 145.224f)
                lineTo(63.262f, 140.936f)
                curveTo(62.792f, 140.68f, 62.366f, 140.36f, 61.981f, 139.976f)
                curveTo(61.64f, 139.635f, 61.384f, 139.208f, 61.214f, 138.696f)
                curveTo(61.043f, 138.227f, 60.979f, 137.651f, 61.021f, 136.968f)
                curveTo(61.107f, 136.328f, 61.384f, 135.581f, 61.854f, 134.728f)
                curveTo(65.822f, 127.261f, 68.851f, 119.283f, 70.941f, 110.792f)
                curveTo(73.075f, 102.301f, 74.142f, 93.661f, 74.142f, 84.872f)
                close()
            }
        }
        .build()
        return _rightbracket!!
    }

private var _rightbracket: ImageVector? = null
