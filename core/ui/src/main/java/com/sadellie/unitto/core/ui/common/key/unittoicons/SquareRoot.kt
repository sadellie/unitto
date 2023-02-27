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
        _squareRoot = Builder(name = "SquareRoot", defaultWidth = 150.0.dp, defaultHeight =
        150.0.dp, viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero) {
                moveTo(114.106f, 15.432f)
                lineTo(78.714f, 125.0f)
                horizontalLineTo(69.114f)
                lineTo(53.113f, 82.568f)
                horizontalLineTo(42.169f)
                curveTo(40.975f, 82.568f, 39.93f, 82.227f, 39.034f, 81.544f)
                curveTo(38.18f, 80.819f, 37.754f, 79.581f, 37.754f, 77.832f)
                verticalLineTo(74.184f)
                horizontalLineTo(59.194f)
                curveTo(60.175f, 74.184f, 60.964f, 74.419f, 61.562f, 74.888f)
                curveTo(62.159f, 75.357f, 62.564f, 75.912f, 62.778f, 76.552f)
                lineTo(72.313f, 102.856f)
                curveTo(72.783f, 104.264f, 73.146f, 105.672f, 73.401f, 107.08f)
                curveTo(73.7f, 108.488f, 73.956f, 109.917f, 74.17f, 111.368f)
                curveTo(74.383f, 110.216f, 74.596f, 109.064f, 74.81f, 107.912f)
                curveTo(75.065f, 106.717f, 75.364f, 105.501f, 75.705f, 104.264f)
                lineTo(103.418f, 17.8f)
                curveTo(103.631f, 17.117f, 104.036f, 16.563f, 104.634f, 16.136f)
                curveTo(105.231f, 15.667f, 105.935f, 15.432f, 106.746f, 15.432f)
                horizontalLineTo(114.106f)
                close()
            }
        }
            .build()
        return _squareRoot!!
    }

private var _squareRoot: ImageVector? = null