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
val UnittoIcons.Ln: ImageVector
    get() {
        if (_ln != null) {
            return _ln!!
        }
        _ln = Builder(name = "Ln", defaultWidth = 274.0.dp, defaultHeight = 150.0.dp, viewportWidth
                = 274.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(106.988f, 30.728f)
                verticalLineTo(125.0f)
                horizontalLineTo(95.595f)
                verticalLineTo(30.728f)
                horizontalLineTo(106.988f)
                close()
                moveTo(136.214f, 69.576f)
                curveTo(137.622f, 67.997f, 139.115f, 66.568f, 140.694f, 65.288f)
                curveTo(142.272f, 64.008f, 143.936f, 62.92f, 145.686f, 62.024f)
                curveTo(147.478f, 61.085f, 149.355f, 60.381f, 151.318f, 59.912f)
                curveTo(153.323f, 59.4f, 155.478f, 59.144f, 157.782f, 59.144f)
                curveTo(161.323f, 59.144f, 164.438f, 59.741f, 167.126f, 60.936f)
                curveTo(169.856f, 62.088f, 172.118f, 63.752f, 173.91f, 65.928f)
                curveTo(175.744f, 68.061f, 177.131f, 70.643f, 178.07f, 73.672f)
                curveTo(179.008f, 76.701f, 179.478f, 80.051f, 179.478f, 83.72f)
                verticalLineTo(125.0f)
                horizontalLineTo(168.022f)
                verticalLineTo(83.72f)
                curveTo(168.022f, 78.813f, 166.891f, 75.016f, 164.63f, 72.328f)
                curveTo(162.411f, 69.597f, 159.019f, 68.232f, 154.454f, 68.232f)
                curveTo(151.083f, 68.232f, 147.926f, 69.043f, 144.982f, 70.664f)
                curveTo(142.08f, 72.285f, 139.392f, 74.483f, 136.918f, 77.256f)
                verticalLineTo(125.0f)
                horizontalLineTo(125.462f)
                verticalLineTo(60.168f)
                horizontalLineTo(132.31f)
                curveTo(133.931f, 60.168f, 134.934f, 60.957f, 135.318f, 62.536f)
                lineTo(136.214f, 69.576f)
                close()
            }
        }
        .build()
        return _ln!!
    }

private var _ln: ImageVector? = null
