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

val @receiver:Suppress("UNUSED") UnittoIcons.KeyA: ImageVector
    get() {
        if (_keya != null) {
            return _keya!!
        }
        _keya = Builder(name = "KeyA", defaultWidth = 150.0.dp, defaultHeight = 150.0.dp,
                viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(92.104f, 90.952f)
                lineTo(77.703f, 53.64f)
                curveTo(77.277f, 52.531f, 76.829f, 51.251f, 76.359f, 49.8f)
                curveTo(75.89f, 48.307f, 75.442f, 46.728f, 75.016f, 45.064f)
                curveTo(74.119f, 48.52f, 73.202f, 51.4f, 72.263f, 53.704f)
                lineTo(57.863f, 90.952f)
                horizontalLineTo(92.104f)
                close()
                moveTo(117.96f, 125.0f)
                horizontalLineTo(108.36f)
                curveTo(107.25f, 125.0f, 106.354f, 124.723f, 105.672f, 124.168f)
                curveTo(104.989f, 123.613f, 104.477f, 122.909f, 104.136f, 122.056f)
                lineTo(95.56f, 99.912f)
                horizontalLineTo(54.408f)
                lineTo(45.832f, 122.056f)
                curveTo(45.576f, 122.824f, 45.085f, 123.507f, 44.36f, 124.104f)
                curveTo(43.634f, 124.701f, 42.738f, 125.0f, 41.672f, 125.0f)
                horizontalLineTo(32.071f)
                lineTo(68.743f, 33.288f)
                horizontalLineTo(81.287f)
                lineTo(117.96f, 125.0f)
                close()
            }
        }
        .build()
        return _keya!!
    }

private var _keya: ImageVector? = null
