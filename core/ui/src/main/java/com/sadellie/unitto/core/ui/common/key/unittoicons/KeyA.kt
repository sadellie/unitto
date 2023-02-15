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
                moveTo(89.762f, 90.056f)
                lineTo(78.498f, 59.272f)
                curveTo(77.944f, 57.907f, 77.368f, 56.285f, 76.771f, 54.408f)
                curveTo(76.173f, 52.531f, 75.576f, 50.504f, 74.979f, 48.328f)
                curveTo(74.424f, 50.504f, 73.848f, 52.552f, 73.251f, 54.472f)
                curveTo(72.653f, 56.349f, 72.077f, 57.992f, 71.522f, 59.4f)
                lineTo(60.322f, 90.056f)
                horizontalLineTo(89.762f)
                close()
                moveTo(120.163f, 125.0f)
                horizontalLineTo(106.851f)
                curveTo(105.357f, 125.0f, 104.141f, 124.637f, 103.203f, 123.912f)
                curveTo(102.264f, 123.144f, 101.56f, 122.205f, 101.09f, 121.096f)
                lineTo(94.178f, 102.216f)
                horizontalLineTo(55.842f)
                lineTo(48.931f, 121.096f)
                curveTo(48.589f, 122.077f, 47.928f, 122.973f, 46.946f, 123.784f)
                curveTo(45.965f, 124.595f, 44.749f, 125.0f, 43.299f, 125.0f)
                horizontalLineTo(29.858f)
                lineTo(66.274f, 32.456f)
                horizontalLineTo(83.811f)
                lineTo(120.163f, 125.0f)
                close()
            }
        }
        .build()
        return _keya!!
    }

private var _keya: ImageVector? = null
