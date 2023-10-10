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
val UnittoIcons.LeftBracket: ImageVector
    get() {
        if (_leftbracket != null) {
            return _leftbracket!!
        }
        _leftbracket = Builder(name = "Leftbracket", defaultWidth = 170.0.dp, defaultHeight =
                170.0.dp, viewportWidth = 170.0f, viewportHeight = 170.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(84.628f, 85.345f)
                curveTo(84.628f, 92.592f, 85.525f, 99.683f, 87.319f, 106.619f)
                curveTo(89.114f, 113.52f, 91.719f, 120.025f, 95.135f, 126.133f)
                curveTo(95.446f, 126.754f, 95.635f, 127.306f, 95.704f, 127.789f)
                curveTo(95.773f, 128.272f, 95.739f, 128.686f, 95.601f, 129.031f)
                curveTo(95.463f, 129.411f, 95.256f, 129.722f, 94.98f, 129.963f)
                curveTo(94.738f, 130.205f, 94.462f, 130.429f, 94.152f, 130.636f)
                lineTo(89.234f, 133.69f)
                curveTo(86.646f, 129.722f, 84.455f, 125.771f, 82.661f, 121.837f)
                curveTo(80.866f, 117.937f, 79.4f, 114.003f, 78.261f, 110.035f)
                curveTo(77.122f, 106.032f, 76.294f, 101.995f, 75.776f, 97.923f)
                curveTo(75.259f, 93.817f, 75.0f, 89.624f, 75.0f, 85.345f)
                curveTo(75.0f, 81.101f, 75.259f, 76.942f, 75.776f, 72.87f)
                curveTo(76.294f, 68.764f, 77.122f, 64.727f, 78.261f, 60.758f)
                curveTo(79.4f, 56.756f, 80.866f, 52.787f, 82.661f, 48.853f)
                curveTo(84.455f, 44.919f, 86.646f, 40.968f, 89.234f, 37.0f)
                lineTo(94.152f, 40.054f)
                curveTo(94.462f, 40.261f, 94.738f, 40.485f, 94.98f, 40.727f)
                curveTo(95.256f, 40.968f, 95.463f, 41.279f, 95.601f, 41.659f)
                curveTo(95.739f, 42.004f, 95.773f, 42.418f, 95.704f, 42.901f)
                curveTo(95.635f, 43.384f, 95.446f, 43.936f, 95.135f, 44.557f)
                curveTo(91.719f, 50.7f, 89.114f, 57.221f, 87.319f, 64.123f)
                curveTo(85.525f, 71.024f, 84.628f, 78.098f, 84.628f, 85.345f)
                close()
            }
        }
        .build()
        return _leftbracket!!
    }

private var _leftbracket: ImageVector? = null
