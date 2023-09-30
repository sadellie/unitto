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
val UnittoIcons.Factorial: ImageVector
    get() {
        if (_factorial != null) {
            return _factorial!!
        }
        _factorial = Builder(name = "Factorial", defaultWidth = 274.0.dp, defaultHeight = 150.0.dp,
                viewportWidth = 274.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(142.533f, 33.288f)
                verticalLineTo(69.896f)
                curveTo(142.533f, 71.816f, 142.49f, 73.693f, 142.405f, 75.528f)
                curveTo(142.319f, 77.363f, 142.213f, 79.219f, 142.085f, 81.096f)
                curveTo(141.957f, 82.931f, 141.786f, 84.829f, 141.573f, 86.792f)
                curveTo(141.402f, 88.712f, 141.189f, 90.781f, 140.933f, 93.0f)
                horizontalLineTo(133.253f)
                curveTo(132.997f, 90.781f, 132.762f, 88.712f, 132.549f, 86.792f)
                curveTo(132.378f, 84.829f, 132.229f, 82.931f, 132.101f, 81.096f)
                curveTo(131.973f, 79.219f, 131.866f, 77.363f, 131.781f, 75.528f)
                curveTo(131.695f, 73.693f, 131.653f, 71.816f, 131.653f, 69.896f)
                verticalLineTo(33.288f)
                horizontalLineTo(142.533f)
                close()
                moveTo(128.901f, 117.96f)
                curveTo(128.901f, 116.851f, 129.093f, 115.805f, 129.477f, 114.824f)
                curveTo(129.903f, 113.843f, 130.479f, 112.989f, 131.205f, 112.264f)
                curveTo(131.93f, 111.539f, 132.762f, 110.963f, 133.701f, 110.536f)
                curveTo(134.682f, 110.109f, 135.749f, 109.896f, 136.901f, 109.896f)
                curveTo(138.01f, 109.896f, 139.034f, 110.109f, 139.973f, 110.536f)
                curveTo(140.954f, 110.963f, 141.807f, 111.539f, 142.533f, 112.264f)
                curveTo(143.258f, 112.989f, 143.834f, 113.843f, 144.261f, 114.824f)
                curveTo(144.687f, 115.805f, 144.901f, 116.851f, 144.901f, 117.96f)
                curveTo(144.901f, 119.112f, 144.687f, 120.179f, 144.261f, 121.16f)
                curveTo(143.834f, 122.099f, 143.258f, 122.931f, 142.533f, 123.656f)
                curveTo(141.807f, 124.381f, 140.954f, 124.936f, 139.973f, 125.32f)
                curveTo(139.034f, 125.747f, 138.01f, 125.96f, 136.901f, 125.96f)
                curveTo(135.749f, 125.96f, 134.682f, 125.747f, 133.701f, 125.32f)
                curveTo(132.762f, 124.936f, 131.93f, 124.381f, 131.205f, 123.656f)
                curveTo(130.479f, 122.931f, 129.903f, 122.099f, 129.477f, 121.16f)
                curveTo(129.093f, 120.179f, 128.901f, 119.112f, 128.901f, 117.96f)
                close()
            }
        }
        .build()
        return _factorial!!
    }

private var _factorial: ImageVector? = null
