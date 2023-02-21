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
                moveTo(66.979f, 117.96f)
                curveTo(66.979f, 116.851f, 67.171f, 115.805f, 67.555f, 114.824f)
                curveTo(67.982f, 113.843f, 68.536f, 112.989f, 69.219f, 112.264f)
                curveTo(69.944f, 111.539f, 70.798f, 110.963f, 71.779f, 110.536f)
                curveTo(72.76f, 110.109f, 73.806f, 109.896f, 74.915f, 109.896f)
                curveTo(76.024f, 109.896f, 77.07f, 110.109f, 78.051f, 110.536f)
                curveTo(79.032f, 110.963f, 79.886f, 111.539f, 80.611f, 112.264f)
                curveTo(81.336f, 112.989f, 81.912f, 113.843f, 82.339f, 114.824f)
                curveTo(82.766f, 115.805f, 82.979f, 116.851f, 82.979f, 117.96f)
                curveTo(82.979f, 119.112f, 82.766f, 120.179f, 82.339f, 121.16f)
                curveTo(81.912f, 122.099f, 81.336f, 122.931f, 80.611f, 123.656f)
                curveTo(79.886f, 124.381f, 79.032f, 124.936f, 78.051f, 125.32f)
                curveTo(77.07f, 125.747f, 76.024f, 125.96f, 74.915f, 125.96f)
                curveTo(73.806f, 125.96f, 72.76f, 125.747f, 71.779f, 125.32f)
                curveTo(70.798f, 124.936f, 69.944f, 124.381f, 69.219f, 123.656f)
                curveTo(68.536f, 122.931f, 67.982f, 122.099f, 67.555f, 121.16f)
                curveTo(67.171f, 120.179f, 66.979f, 119.112f, 66.979f, 117.96f)
                close()
            }
        }
        .build()
        return _dot!!
    }

private var _dot: ImageVector? = null
