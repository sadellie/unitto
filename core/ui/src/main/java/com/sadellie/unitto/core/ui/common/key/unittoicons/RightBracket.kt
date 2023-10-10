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

@Suppress("UnusedReceiverParameter")
val UnittoIcons.RightBracket: ImageVector
    get() {
        if (_rightbracket != null) {
            return _rightbracket!!
        }
        _rightbracket = Builder(name = "RightBracket", defaultWidth = 170.0.dp, defaultHeight =
                170.0.dp, viewportWidth = 170.0f, viewportHeight = 170.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(86.111f, 85.345f)
                curveTo(86.111f, 92.592f, 85.214f, 99.683f, 83.419f, 106.619f)
                curveTo(81.625f, 113.52f, 79.019f, 120.025f, 75.603f, 126.133f)
                curveTo(75.293f, 126.754f, 75.103f, 127.306f, 75.034f, 127.789f)
                curveTo(74.965f, 128.272f, 74.999f, 128.686f, 75.137f, 129.031f)
                curveTo(75.275f, 129.411f, 75.482f, 129.722f, 75.758f, 129.963f)
                curveTo(76.0f, 130.205f, 76.276f, 130.429f, 76.587f, 130.636f)
                lineTo(81.504f, 133.69f)
                curveTo(84.092f, 129.722f, 86.283f, 125.771f, 88.078f, 121.837f)
                curveTo(89.872f, 117.937f, 91.339f, 114.003f, 92.477f, 110.035f)
                curveTo(93.616f, 106.032f, 94.444f, 101.995f, 94.962f, 97.923f)
                curveTo(95.479f, 93.817f, 95.738f, 89.624f, 95.738f, 85.345f)
                curveTo(95.738f, 81.101f, 95.479f, 76.942f, 94.962f, 72.87f)
                curveTo(94.444f, 68.764f, 93.616f, 64.727f, 92.477f, 60.758f)
                curveTo(91.339f, 56.756f, 89.872f, 52.787f, 88.078f, 48.853f)
                curveTo(86.283f, 44.919f, 84.092f, 40.968f, 81.504f, 37.0f)
                lineTo(76.587f, 40.054f)
                curveTo(76.276f, 40.261f, 76.0f, 40.485f, 75.758f, 40.727f)
                curveTo(75.482f, 40.968f, 75.275f, 41.279f, 75.137f, 41.659f)
                curveTo(74.999f, 42.004f, 74.965f, 42.418f, 75.034f, 42.901f)
                curveTo(75.103f, 43.384f, 75.293f, 43.936f, 75.603f, 44.557f)
                curveTo(79.019f, 50.7f, 81.625f, 57.221f, 83.419f, 64.123f)
                curveTo(85.214f, 71.024f, 86.111f, 78.098f, 86.111f, 85.345f)
                close()
            }
        }
        .build()
        return _rightbracket!!
    }

private var _rightbracket: ImageVector? = null
