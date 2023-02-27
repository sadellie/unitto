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
        if (_squareRoot != null) {
            return _squareRoot!!
        }
        _squareRoot = Builder(name = "Squareroot", defaultWidth = 274.0.dp, defaultHeight =
                150.0.dp, viewportWidth = 274.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(176.106f, 15.432f)
                lineTo(140.714f, 125.0f)
                horizontalLineTo(131.114f)
                lineTo(115.114f, 82.568f)
                horizontalLineTo(104.17f)
                curveTo(102.975f, 82.568f, 101.93f, 82.227f, 101.034f, 81.544f)
                curveTo(100.18f, 80.819f, 99.754f, 79.581f, 99.754f, 77.832f)
                verticalLineTo(74.184f)
                horizontalLineTo(121.194f)
                curveTo(122.175f, 74.184f, 122.964f, 74.419f, 123.562f, 74.888f)
                curveTo(124.159f, 75.357f, 124.564f, 75.912f, 124.778f, 76.552f)
                lineTo(134.314f, 102.856f)
                curveTo(134.783f, 104.264f, 135.146f, 105.672f, 135.402f, 107.08f)
                curveTo(135.7f, 108.488f, 135.956f, 109.917f, 136.17f, 111.368f)
                curveTo(136.383f, 110.216f, 136.596f, 109.064f, 136.81f, 107.912f)
                curveTo(137.066f, 106.717f, 137.364f, 105.501f, 137.706f, 104.264f)
                lineTo(165.418f, 17.8f)
                curveTo(165.631f, 17.117f, 166.036f, 16.563f, 166.634f, 16.136f)
                curveTo(167.231f, 15.667f, 167.935f, 15.432f, 168.746f, 15.432f)
                horizontalLineTo(176.106f)
                close()
            }
        }
        .build()
        return _squareRoot!!
    }

private var _squareRoot: ImageVector? = null
