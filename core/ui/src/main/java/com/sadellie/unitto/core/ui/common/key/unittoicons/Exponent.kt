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
        _exponent = Builder(name = "Exponent", defaultWidth = 150.0.dp, defaultHeight = 150.0.dp,
                viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(105.779f, 81.416f)
                horizontalLineTo(94.195f)
                curveTo(93.214f, 81.416f, 92.403f, 81.16f, 91.763f, 80.648f)
                curveTo(91.123f, 80.136f, 90.611f, 79.496f, 90.227f, 78.728f)
                lineTo(78.067f, 54.152f)
                curveTo(76.702f, 51.379f, 75.656f, 48.861f, 74.931f, 46.6f)
                curveTo(74.59f, 47.837f, 74.163f, 49.075f, 73.651f, 50.312f)
                curveTo(73.182f, 51.507f, 72.648f, 52.787f, 72.051f, 54.152f)
                lineTo(60.531f, 78.728f)
                curveTo(60.19f, 79.496f, 59.678f, 80.136f, 58.995f, 80.648f)
                curveTo(58.312f, 81.16f, 57.438f, 81.416f, 56.371f, 81.416f)
                horizontalLineTo(44.211f)
                lineTo(69.619f, 32.456f)
                horizontalLineTo(79.859f)
                lineTo(105.779f, 81.416f)
                close()
            }
        }
        .build()
        return _exponent!!
    }

private var _exponent: ImageVector? = null
