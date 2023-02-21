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

val @receiver:Suppress("UNUSED") UnittoIcons.KeyB: ImageVector
    get() {
        if (_keyb != null) {
            return _keyb!!
        }
        _keyb = Builder(name = "KeyB", defaultWidth = 150.0.dp, defaultHeight = 150.0.dp,
                viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(77.529f, 115.144f)
                curveTo(81.07f, 115.144f, 84.121f, 114.739f, 86.681f, 113.928f)
                curveTo(89.241f, 113.117f, 91.332f, 111.987f, 92.953f, 110.536f)
                curveTo(94.617f, 109.043f, 95.833f, 107.293f, 96.601f, 105.288f)
                curveTo(97.369f, 103.24f, 97.753f, 101.0f, 97.753f, 98.568f)
                curveTo(97.753f, 93.832f, 96.068f, 90.099f, 92.697f, 87.368f)
                curveTo(89.326f, 84.595f, 84.27f, 83.208f, 77.529f, 83.208f)
                horizontalLineTo(57.689f)
                verticalLineTo(115.144f)
                horizontalLineTo(77.529f)
                close()
                moveTo(57.689f, 43.08f)
                verticalLineTo(74.376f)
                horizontalLineTo(74.073f)
                curveTo(77.572f, 74.376f, 80.601f, 73.992f, 83.161f, 73.224f)
                curveTo(85.764f, 72.456f, 87.897f, 71.389f, 89.561f, 70.024f)
                curveTo(91.268f, 68.659f, 92.526f, 67.016f, 93.337f, 65.096f)
                curveTo(94.148f, 63.133f, 94.553f, 61.0f, 94.553f, 58.696f)
                curveTo(94.553f, 53.277f, 92.932f, 49.331f, 89.689f, 46.856f)
                curveTo(86.446f, 44.339f, 81.412f, 43.08f, 74.585f, 43.08f)
                horizontalLineTo(57.689f)
                close()
                moveTo(74.585f, 33.288f)
                curveTo(80.217f, 33.288f, 85.06f, 33.843f, 89.113f, 34.952f)
                curveTo(93.209f, 36.061f, 96.558f, 37.64f, 99.161f, 39.688f)
                curveTo(101.806f, 41.736f, 103.748f, 44.253f, 104.985f, 47.24f)
                curveTo(106.222f, 50.184f, 106.841f, 53.512f, 106.841f, 57.224f)
                curveTo(106.841f, 59.485f, 106.478f, 61.661f, 105.753f, 63.752f)
                curveTo(105.07f, 65.8f, 104.025f, 67.72f, 102.617f, 69.512f)
                curveTo(101.209f, 71.304f, 99.417f, 72.925f, 97.241f, 74.376f)
                curveTo(95.108f, 75.784f, 92.59f, 76.936f, 89.689f, 77.832f)
                curveTo(96.43f, 79.112f, 101.486f, 81.523f, 104.857f, 85.064f)
                curveTo(108.27f, 88.563f, 109.977f, 93.171f, 109.977f, 98.888f)
                curveTo(109.977f, 102.771f, 109.252f, 106.312f, 107.801f, 109.512f)
                curveTo(106.393f, 112.712f, 104.302f, 115.464f, 101.529f, 117.768f)
                curveTo(98.798f, 120.072f, 95.428f, 121.864f, 91.417f, 123.144f)
                curveTo(87.406f, 124.381f, 82.841f, 125.0f, 77.721f, 125.0f)
                horizontalLineTo(45.337f)
                verticalLineTo(33.288f)
                horizontalLineTo(74.585f)
                close()
            }
        }
        .build()
        return _keyb!!
    }

private var _keyb: ImageVector? = null
