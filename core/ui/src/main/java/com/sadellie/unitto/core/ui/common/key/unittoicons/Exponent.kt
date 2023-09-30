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
val UnittoIcons.Exponent: ImageVector
    get() {
        if (_exponent != null) {
            return _exponent!!
        }
        _exponent = Builder(name = "Exponent", defaultWidth = 150.0.dp, defaultHeight = 150.0.dp,
            viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero) {
                moveTo(103.667f, 80.456f)
                horizontalLineTo(95.347f)
                curveTo(94.622f, 80.456f, 93.982f, 80.264f, 93.427f, 79.88f)
                curveTo(92.915f, 79.453f, 92.51f, 78.92f, 92.211f, 78.28f)
                lineTo(77.491f, 49.928f)
                curveTo(76.382f, 47.667f, 75.528f, 45.597f, 74.931f, 43.72f)
                curveTo(74.632f, 44.701f, 74.291f, 45.704f, 73.907f, 46.728f)
                curveTo(73.523f, 47.752f, 73.096f, 48.819f, 72.627f, 49.928f)
                lineTo(58.355f, 78.28f)
                curveTo(58.099f, 78.877f, 57.694f, 79.389f, 57.139f, 79.816f)
                curveTo(56.584f, 80.243f, 55.88f, 80.456f, 55.027f, 80.456f)
                horizontalLineTo(46.323f)
                lineTo(71.155f, 33.288f)
                horizontalLineTo(78.515f)
                lineTo(103.667f, 80.456f)
                close()
            }
        }
            .build()
        return _exponent!!
    }

private var _exponent: ImageVector? = null