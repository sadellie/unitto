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

val @receiver:Suppress("UNUSED") UnittoIcons.SquareRoot: ImageVector
    get() {
        if (_squareroot != null) {
            return _squareroot!!
        }
        _squareroot = Builder(name = "Squareroot", defaultWidth = 150.0.dp, defaultHeight =
                150.0.dp, viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(114.674f, 15.816f)
                lineTo(79.474f, 125.0f)
                horizontalLineTo(66.162f)
                lineTo(50.994f, 83.912f)
                horizontalLineTo(43.25f)
                curveTo(41.629f, 83.912f, 40.221f, 83.443f, 39.026f, 82.504f)
                curveTo(37.874f, 81.523f, 37.298f, 79.88f, 37.298f, 77.576f)
                verticalLineTo(72.52f)
                horizontalLineTo(59.826f)
                curveTo(61.021f, 72.52f, 62.002f, 72.797f, 62.77f, 73.352f)
                curveTo(63.581f, 73.907f, 64.114f, 74.611f, 64.37f, 75.464f)
                lineTo(70.898f, 95.368f)
                curveTo(71.495f, 97.16f, 71.965f, 98.952f, 72.306f, 100.744f)
                curveTo(72.69f, 102.536f, 72.989f, 104.371f, 73.202f, 106.248f)
                curveTo(73.458f, 104.797f, 73.735f, 103.347f, 74.034f, 101.896f)
                curveTo(74.375f, 100.403f, 74.759f, 98.888f, 75.186f, 97.352f)
                lineTo(99.89f, 18.76f)
                curveTo(100.189f, 17.907f, 100.722f, 17.203f, 101.49f, 16.648f)
                curveTo(102.258f, 16.093f, 103.175f, 15.816f, 104.242f, 15.816f)
                horizontalLineTo(114.674f)
                close()
            }
        }
        .build()
        return _squareroot!!
    }

private var _squareroot: ImageVector? = null
