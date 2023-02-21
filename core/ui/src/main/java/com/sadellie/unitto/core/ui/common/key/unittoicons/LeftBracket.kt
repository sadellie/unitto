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

val @receiver:Suppress("UNUSED") UnittoIcons.LeftBracket: ImageVector
    get() {
        if (_leftbracket != null) {
            return _leftbracket!!
        }
        _leftbracket = Builder(name = "LeftBracket", defaultWidth = 150.0.dp, defaultHeight =
                150.0.dp, viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(73.618f, 84.744f)
                curveTo(73.618f, 93.875f, 74.77f, 102.749f, 77.074f, 111.368f)
                curveTo(79.42f, 119.987f, 82.834f, 128.179f, 87.313f, 135.944f)
                curveTo(87.996f, 137.267f, 88.167f, 138.269f, 87.826f, 138.952f)
                curveTo(87.484f, 139.677f, 86.993f, 140.253f, 86.354f, 140.68f)
                lineTo(81.298f, 143.752f)
                curveTo(78.098f, 138.845f, 75.367f, 133.981f, 73.105f, 129.16f)
                curveTo(70.887f, 124.339f, 69.074f, 119.496f, 67.665f, 114.632f)
                curveTo(66.3f, 109.768f, 65.298f, 104.883f, 64.658f, 99.976f)
                curveTo(64.018f, 95.027f, 63.697f, 89.949f, 63.697f, 84.744f)
                curveTo(63.697f, 79.581f, 64.018f, 74.525f, 64.658f, 69.576f)
                curveTo(65.298f, 64.627f, 66.3f, 59.741f, 67.665f, 54.92f)
                curveTo(69.074f, 50.056f, 70.887f, 45.213f, 73.105f, 40.392f)
                curveTo(75.367f, 35.571f, 78.098f, 30.685f, 81.298f, 25.736f)
                lineTo(86.354f, 28.872f)
                curveTo(86.993f, 29.299f, 87.484f, 29.875f, 87.826f, 30.6f)
                curveTo(88.167f, 31.283f, 87.996f, 32.264f, 87.313f, 33.544f)
                curveTo(82.834f, 41.309f, 79.42f, 49.501f, 77.074f, 58.12f)
                curveTo(74.77f, 66.739f, 73.618f, 75.613f, 73.618f, 84.744f)
                close()
            }
        }
        .build()
        return _leftbracket!!
    }

private var _leftbracket: ImageVector? = null
