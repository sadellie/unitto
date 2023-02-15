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

val @receiver:Suppress("UNUSED") UnittoIcons.Dot: ImageVector
    get() {
        if (_dot != null) {
            return _dot!!
        }
        _dot = Builder(name = "Dot", defaultWidth = 150.0.dp, defaultHeight = 150.0.dp,
                viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(65.316f, 116.296f)
                curveTo(65.316f, 114.973f, 65.551f, 113.715f, 66.021f, 112.52f)
                curveTo(66.533f, 111.325f, 67.215f, 110.301f, 68.068f, 109.448f)
                curveTo(68.922f, 108.595f, 69.946f, 107.912f, 71.141f, 107.4f)
                curveTo(72.335f, 106.888f, 73.615f, 106.632f, 74.98f, 106.632f)
                curveTo(76.346f, 106.632f, 77.604f, 106.888f, 78.757f, 107.4f)
                curveTo(79.951f, 107.912f, 80.975f, 108.595f, 81.828f, 109.448f)
                curveTo(82.725f, 110.301f, 83.428f, 111.325f, 83.94f, 112.52f)
                curveTo(84.452f, 113.715f, 84.709f, 114.973f, 84.709f, 116.296f)
                curveTo(84.709f, 117.661f, 84.452f, 118.941f, 83.94f, 120.136f)
                curveTo(83.428f, 121.288f, 82.725f, 122.291f, 81.828f, 123.144f)
                curveTo(80.975f, 123.997f, 79.951f, 124.659f, 78.757f, 125.128f)
                curveTo(77.604f, 125.64f, 76.346f, 125.896f, 74.98f, 125.896f)
                curveTo(73.615f, 125.896f, 72.335f, 125.64f, 71.141f, 125.128f)
                curveTo(69.946f, 124.659f, 68.922f, 123.997f, 68.068f, 123.144f)
                curveTo(67.215f, 122.291f, 66.533f, 121.288f, 66.021f, 120.136f)
                curveTo(65.551f, 118.941f, 65.316f, 117.661f, 65.316f, 116.296f)
                close()
            }
        }
        .build()
        return _dot!!
    }

private var _dot: ImageVector? = null
