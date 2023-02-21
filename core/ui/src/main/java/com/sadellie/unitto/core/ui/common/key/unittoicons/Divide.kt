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

val @receiver:Suppress("UNUSED") UnittoIcons.Divide: ImageVector
    get() {
        if (_divide != null) {
            return _divide!!
        }
        _divide = Builder(name = "Divide", defaultWidth = 150.0.dp, defaultHeight = 150.0.dp,
                viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(44.275f, 77.704f)
                horizontalLineTo(105.651f)
                verticalLineTo(86.344f)
                horizontalLineTo(44.275f)
                verticalLineTo(77.704f)
                close()
                moveTo(66.931f, 59.336f)
                curveTo(66.931f, 58.227f, 67.123f, 57.181f, 67.507f, 56.2f)
                curveTo(67.934f, 55.219f, 68.51f, 54.365f, 69.235f, 53.64f)
                curveTo(69.96f, 52.915f, 70.792f, 52.339f, 71.731f, 51.912f)
                curveTo(72.712f, 51.485f, 73.779f, 51.272f, 74.931f, 51.272f)
                curveTo(76.04f, 51.272f, 77.064f, 51.485f, 78.003f, 51.912f)
                curveTo(78.984f, 52.339f, 79.838f, 52.915f, 80.563f, 53.64f)
                curveTo(81.288f, 54.365f, 81.864f, 55.219f, 82.291f, 56.2f)
                curveTo(82.718f, 57.181f, 82.931f, 58.227f, 82.931f, 59.336f)
                curveTo(82.931f, 60.488f, 82.718f, 61.555f, 82.291f, 62.536f)
                curveTo(81.864f, 63.475f, 81.288f, 64.307f, 80.563f, 65.032f)
                curveTo(79.838f, 65.757f, 78.984f, 66.333f, 78.003f, 66.76f)
                curveTo(77.064f, 67.144f, 76.04f, 67.336f, 74.931f, 67.336f)
                curveTo(73.779f, 67.336f, 72.712f, 67.144f, 71.731f, 66.76f)
                curveTo(70.792f, 66.333f, 69.96f, 65.757f, 69.235f, 65.032f)
                curveTo(68.51f, 64.307f, 67.934f, 63.475f, 67.507f, 62.536f)
                curveTo(67.123f, 61.555f, 66.931f, 60.488f, 66.931f, 59.336f)
                close()
                moveTo(66.931f, 104.904f)
                curveTo(66.931f, 103.795f, 67.123f, 102.749f, 67.507f, 101.768f)
                curveTo(67.934f, 100.787f, 68.51f, 99.933f, 69.235f, 99.208f)
                curveTo(69.96f, 98.483f, 70.792f, 97.907f, 71.731f, 97.48f)
                curveTo(72.712f, 97.053f, 73.779f, 96.84f, 74.931f, 96.84f)
                curveTo(76.04f, 96.84f, 77.064f, 97.053f, 78.003f, 97.48f)
                curveTo(78.984f, 97.907f, 79.838f, 98.483f, 80.563f, 99.208f)
                curveTo(81.288f, 99.933f, 81.864f, 100.787f, 82.291f, 101.768f)
                curveTo(82.718f, 102.749f, 82.931f, 103.795f, 82.931f, 104.904f)
                curveTo(82.931f, 106.056f, 82.718f, 107.123f, 82.291f, 108.104f)
                curveTo(81.864f, 109.043f, 81.288f, 109.875f, 80.563f, 110.6f)
                curveTo(79.838f, 111.325f, 78.984f, 111.901f, 78.003f, 112.328f)
                curveTo(77.064f, 112.712f, 76.04f, 112.904f, 74.931f, 112.904f)
                curveTo(73.779f, 112.904f, 72.712f, 112.712f, 71.731f, 112.328f)
                curveTo(70.792f, 111.901f, 69.96f, 111.325f, 69.235f, 110.6f)
                curveTo(68.51f, 109.875f, 67.934f, 109.043f, 67.507f, 108.104f)
                curveTo(67.123f, 107.123f, 66.931f, 106.056f, 66.931f, 104.904f)
                close()
            }
        }
        .build()
        return _divide!!
    }

private var _divide: ImageVector? = null
