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

val @receiver:Suppress("UNUSED") UnittoIcons.Factorial: ImageVector
    get() {
        if (_factorial != null) {
            return _factorial!!
        }
        _factorial = Builder(name = "Factorial", defaultWidth = 150.0.dp, defaultHeight = 150.0.dp,
                viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(80.533f, 33.288f)
                verticalLineTo(69.896f)
                curveTo(80.533f, 71.816f, 80.49f, 73.693f, 80.405f, 75.528f)
                curveTo(80.319f, 77.363f, 80.213f, 79.219f, 80.085f, 81.096f)
                curveTo(79.956f, 82.931f, 79.786f, 84.829f, 79.573f, 86.792f)
                curveTo(79.402f, 88.712f, 79.188f, 90.781f, 78.933f, 93.0f)
                horizontalLineTo(71.253f)
                curveTo(70.996f, 90.781f, 70.762f, 88.712f, 70.549f, 86.792f)
                curveTo(70.378f, 84.829f, 70.229f, 82.931f, 70.101f, 81.096f)
                curveTo(69.973f, 79.219f, 69.866f, 77.363f, 69.781f, 75.528f)
                curveTo(69.695f, 73.693f, 69.652f, 71.816f, 69.652f, 69.896f)
                verticalLineTo(33.288f)
                horizontalLineTo(80.533f)
                close()
                moveTo(66.9f, 117.96f)
                curveTo(66.9f, 116.851f, 67.092f, 115.805f, 67.477f, 114.824f)
                curveTo(67.903f, 113.843f, 68.479f, 112.989f, 69.204f, 112.264f)
                curveTo(69.93f, 111.539f, 70.762f, 110.963f, 71.701f, 110.536f)
                curveTo(72.682f, 110.109f, 73.748f, 109.896f, 74.9f, 109.896f)
                curveTo(76.01f, 109.896f, 77.034f, 110.109f, 77.973f, 110.536f)
                curveTo(78.954f, 110.963f, 79.807f, 111.539f, 80.533f, 112.264f)
                curveTo(81.258f, 112.989f, 81.834f, 113.843f, 82.26f, 114.824f)
                curveTo(82.687f, 115.805f, 82.9f, 116.851f, 82.9f, 117.96f)
                curveTo(82.9f, 119.112f, 82.687f, 120.179f, 82.26f, 121.16f)
                curveTo(81.834f, 122.099f, 81.258f, 122.931f, 80.533f, 123.656f)
                curveTo(79.807f, 124.381f, 78.954f, 124.936f, 77.973f, 125.32f)
                curveTo(77.034f, 125.747f, 76.01f, 125.96f, 74.9f, 125.96f)
                curveTo(73.748f, 125.96f, 72.682f, 125.747f, 71.701f, 125.32f)
                curveTo(70.762f, 124.936f, 69.93f, 124.381f, 69.204f, 123.656f)
                curveTo(68.479f, 122.931f, 67.903f, 122.099f, 67.477f, 121.16f)
                curveTo(67.092f, 120.179f, 66.9f, 119.112f, 66.9f, 117.96f)
                close()
            }
        }
        .build()
        return _factorial!!
    }

private var _factorial: ImageVector? = null
