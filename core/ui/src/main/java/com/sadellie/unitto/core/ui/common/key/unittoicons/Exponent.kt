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

val @receiver:Suppress("UNUSED") UnittoIcons.Exponent: ImageVector
    get() {
        if (_exponent != null) {
            return _exponent!!
        }
        _exponent = Builder(name = "Exponent", defaultWidth = 274.0.dp, defaultHeight = 150.0.dp,
                viewportWidth = 274.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(165.667f, 80.456f)
                horizontalLineTo(157.347f)
                curveTo(156.622f, 80.456f, 155.982f, 80.264f, 155.427f, 79.88f)
                curveTo(154.915f, 79.453f, 154.51f, 78.92f, 154.211f, 78.28f)
                lineTo(139.491f, 49.928f)
                curveTo(138.382f, 47.667f, 137.528f, 45.597f, 136.931f, 43.72f)
                curveTo(136.632f, 44.701f, 136.291f, 45.704f, 135.907f, 46.728f)
                curveTo(135.523f, 47.752f, 135.096f, 48.819f, 134.627f, 49.928f)
                lineTo(120.355f, 78.28f)
                curveTo(120.099f, 78.877f, 119.694f, 79.389f, 119.139f, 79.816f)
                curveTo(118.584f, 80.243f, 117.88f, 80.456f, 117.027f, 80.456f)
                horizontalLineTo(108.323f)
                lineTo(133.155f, 33.288f)
                horizontalLineTo(140.515f)
                lineTo(165.667f, 80.456f)
                close()
            }
        }
        .build()
        return _exponent!!
    }

private var _exponent: ImageVector? = null
