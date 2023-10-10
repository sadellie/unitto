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
val UnittoIcons.Comma: ImageVector
    get() {
        if (_comma != null) {
            return _comma!!
        }
        _comma = Builder(name = "Comma", defaultWidth = 170.0.dp, defaultHeight = 170.0.dp,
                viewportWidth = 170.0f, viewportHeight = 170.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(78.518f, 108.548f)
                curveTo(78.166f, 108.24f, 77.924f, 107.954f, 77.792f, 107.69f)
                curveTo(77.66f, 107.426f, 77.594f, 107.096f, 77.594f, 106.7f)
                curveTo(77.594f, 106.392f, 77.682f, 106.084f, 77.858f, 105.776f)
                curveTo(78.078f, 105.468f, 78.32f, 105.182f, 78.584f, 104.918f)
                curveTo(79.024f, 104.434f, 79.574f, 103.796f, 80.234f, 103.004f)
                curveTo(80.938f, 102.212f, 81.642f, 101.266f, 82.346f, 100.166f)
                curveTo(83.05f, 99.11f, 83.688f, 97.922f, 84.26f, 96.602f)
                curveTo(84.876f, 95.326f, 85.316f, 93.962f, 85.58f, 92.51f)
                curveTo(85.448f, 92.554f, 85.294f, 92.576f, 85.118f, 92.576f)
                curveTo(84.986f, 92.576f, 84.854f, 92.576f, 84.722f, 92.576f)
                curveTo(82.434f, 92.576f, 80.564f, 91.828f, 79.112f, 90.332f)
                curveTo(77.704f, 88.792f, 77.0f, 86.834f, 77.0f, 84.458f)
                curveTo(77.0f, 82.39f, 77.704f, 80.63f, 79.112f, 79.178f)
                curveTo(80.564f, 77.726f, 82.478f, 77.0f, 84.854f, 77.0f)
                curveTo(86.174f, 77.0f, 87.34f, 77.242f, 88.352f, 77.726f)
                curveTo(89.364f, 78.21f, 90.2f, 78.892f, 90.86f, 79.772f)
                curveTo(91.564f, 80.608f, 92.092f, 81.598f, 92.444f, 82.742f)
                curveTo(92.796f, 83.842f, 92.972f, 85.052f, 92.972f, 86.372f)
                curveTo(92.972f, 88.352f, 92.686f, 90.42f, 92.114f, 92.576f)
                curveTo(91.586f, 94.688f, 90.772f, 96.778f, 89.672f, 98.846f)
                curveTo(88.616f, 100.958f, 87.318f, 103.004f, 85.778f, 104.984f)
                curveTo(84.238f, 106.964f, 82.478f, 108.79f, 80.498f, 110.462f)
                lineTo(78.518f, 108.548f)
                close()
            }
        }
        .build()
        return _comma!!
    }

private var _comma: ImageVector? = null
