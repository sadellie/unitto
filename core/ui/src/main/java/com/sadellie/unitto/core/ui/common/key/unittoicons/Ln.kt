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

val @receiver:Suppress("UNUSED") UnittoIcons.Ln: ImageVector
    get() {
        if (_ln != null) {
            return _ln!!
        }
        _ln = Builder(name = "Ln", defaultWidth = 266.0.dp, defaultHeight = 150.0.dp, viewportWidth
                = 266.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(102.988f, 30.728f)
                verticalLineTo(125.0f)
                horizontalLineTo(91.595f)
                verticalLineTo(30.728f)
                horizontalLineTo(102.988f)
                close()
                moveTo(132.214f, 69.576f)
                curveTo(133.622f, 67.997f, 135.115f, 66.568f, 136.694f, 65.288f)
                curveTo(138.272f, 64.008f, 139.936f, 62.92f, 141.686f, 62.024f)
                curveTo(143.478f, 61.085f, 145.355f, 60.381f, 147.318f, 59.912f)
                curveTo(149.323f, 59.4f, 151.478f, 59.144f, 153.782f, 59.144f)
                curveTo(157.323f, 59.144f, 160.438f, 59.741f, 163.126f, 60.936f)
                curveTo(165.856f, 62.088f, 168.118f, 63.752f, 169.91f, 65.928f)
                curveTo(171.744f, 68.061f, 173.131f, 70.643f, 174.07f, 73.672f)
                curveTo(175.008f, 76.701f, 175.478f, 80.051f, 175.478f, 83.72f)
                verticalLineTo(125.0f)
                horizontalLineTo(164.022f)
                verticalLineTo(83.72f)
                curveTo(164.022f, 78.813f, 162.891f, 75.016f, 160.63f, 72.328f)
                curveTo(158.411f, 69.597f, 155.019f, 68.232f, 150.454f, 68.232f)
                curveTo(147.083f, 68.232f, 143.926f, 69.043f, 140.982f, 70.664f)
                curveTo(138.08f, 72.285f, 135.392f, 74.483f, 132.918f, 77.256f)
                verticalLineTo(125.0f)
                horizontalLineTo(121.462f)
                verticalLineTo(60.168f)
                horizontalLineTo(128.31f)
                curveTo(129.931f, 60.168f, 130.934f, 60.957f, 131.318f, 62.536f)
                lineTo(132.214f, 69.576f)
                close()
            }
        }
        .build()
        return _ln!!
    }

private var _ln: ImageVector? = null
