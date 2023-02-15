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

val @receiver:Suppress("UNUSED") UnittoIcons.LeftBracket: ImageVector
    get() {
        if (_leftbracket != null) {
            return _leftbracket!!
        }
        _leftbracket = Builder(name = "LeftBracket", defaultWidth = 150.0.dp, defaultHeight =
                150.0.dp, viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(75.805f, 84.872f)
                curveTo(75.805f, 93.661f, 76.872f, 102.301f, 79.006f, 110.792f)
                curveTo(81.139f, 119.283f, 84.189f, 127.261f, 88.158f, 134.728f)
                curveTo(88.584f, 135.581f, 88.819f, 136.328f, 88.862f, 136.968f)
                curveTo(88.947f, 137.651f, 88.904f, 138.227f, 88.733f, 138.696f)
                curveTo(88.563f, 139.208f, 88.285f, 139.635f, 87.901f, 139.976f)
                curveTo(87.56f, 140.36f, 87.176f, 140.68f, 86.749f, 140.936f)
                lineTo(79.71f, 145.224f)
                curveTo(76.552f, 140.36f, 73.864f, 135.475f, 71.646f, 130.568f)
                curveTo(69.427f, 125.661f, 67.614f, 120.712f, 66.205f, 115.72f)
                curveTo(64.798f, 110.728f, 63.752f, 105.672f, 63.069f, 100.552f)
                curveTo(62.43f, 95.432f, 62.11f, 90.205f, 62.11f, 84.872f)
                curveTo(62.11f, 79.539f, 62.43f, 74.312f, 63.069f, 69.192f)
                curveTo(63.752f, 64.029f, 64.798f, 58.952f, 66.205f, 53.96f)
                curveTo(67.614f, 48.968f, 69.427f, 44.04f, 71.646f, 39.176f)
                curveTo(73.864f, 34.269f, 76.552f, 29.384f, 79.71f, 24.52f)
                lineTo(86.749f, 28.744f)
                curveTo(87.176f, 29.0f, 87.56f, 29.32f, 87.901f, 29.704f)
                curveTo(88.285f, 30.045f, 88.563f, 30.472f, 88.733f, 30.984f)
                curveTo(88.904f, 31.453f, 88.947f, 32.029f, 88.862f, 32.712f)
                curveTo(88.819f, 33.352f, 88.584f, 34.12f, 88.158f, 35.016f)
                curveTo(84.189f, 42.44f, 81.139f, 50.419f, 79.006f, 58.952f)
                curveTo(76.872f, 67.443f, 75.805f, 76.083f, 75.805f, 84.872f)
                close()
            }
        }
        .build()
        return _leftbracket!!
    }

private var _leftbracket: ImageVector? = null
