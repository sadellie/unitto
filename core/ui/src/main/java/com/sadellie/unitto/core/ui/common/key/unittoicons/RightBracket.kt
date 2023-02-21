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

val @receiver:Suppress("UNUSED") UnittoIcons.RightBracket: ImageVector
    get() {
        if (_rightbracket != null) {
            return _rightbracket!!
        }
        _rightbracket = Builder(name = "RightBracket", defaultWidth = 150.0.dp, defaultHeight =
                150.0.dp, viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(76.434f, 84.744f)
                curveTo(76.434f, 75.613f, 75.26f, 66.739f, 72.913f, 58.12f)
                curveTo(70.609f, 49.501f, 67.217f, 41.309f, 62.737f, 33.544f)
                curveTo(62.396f, 32.904f, 62.183f, 32.349f, 62.097f, 31.88f)
                curveTo(62.012f, 31.368f, 62.034f, 30.941f, 62.161f, 30.6f)
                curveTo(62.29f, 30.216f, 62.481f, 29.896f, 62.737f, 29.64f)
                curveTo(63.036f, 29.341f, 63.356f, 29.085f, 63.697f, 28.872f)
                lineTo(68.754f, 25.736f)
                curveTo(71.953f, 30.685f, 74.663f, 35.571f, 76.882f, 40.392f)
                curveTo(79.143f, 45.213f, 80.956f, 50.056f, 82.322f, 54.92f)
                curveTo(83.729f, 59.741f, 84.754f, 64.627f, 85.394f, 69.576f)
                curveTo(86.034f, 74.525f, 86.354f, 79.581f, 86.354f, 84.744f)
                curveTo(86.354f, 89.949f, 86.034f, 95.027f, 85.394f, 99.976f)
                curveTo(84.754f, 104.883f, 83.729f, 109.768f, 82.322f, 114.632f)
                curveTo(80.956f, 119.496f, 79.143f, 124.339f, 76.882f, 129.16f)
                curveTo(74.663f, 133.981f, 71.953f, 138.845f, 68.754f, 143.752f)
                lineTo(63.697f, 140.68f)
                curveTo(63.356f, 140.467f, 63.036f, 140.211f, 62.737f, 139.912f)
                curveTo(62.481f, 139.656f, 62.29f, 139.336f, 62.161f, 138.952f)
                curveTo(62.034f, 138.611f, 62.012f, 138.184f, 62.097f, 137.672f)
                curveTo(62.183f, 137.16f, 62.396f, 136.584f, 62.737f, 135.944f)
                curveTo(67.217f, 128.179f, 70.609f, 119.987f, 72.913f, 111.368f)
                curveTo(75.26f, 102.749f, 76.434f, 93.875f, 76.434f, 84.744f)
                close()
            }
        }
        .build()
        return _rightbracket!!
    }

private var _rightbracket: ImageVector? = null
