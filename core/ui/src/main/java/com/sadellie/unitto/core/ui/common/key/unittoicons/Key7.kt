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

val @receiver:Suppress("UNUSED") UnittoIcons.Key7: ImageVector
    get() {
        if (_key7 != null) {
            return _key7!!
        }
        _key7 = Builder(name = "Key7", defaultWidth = 150.0.dp, defaultHeight = 150.0.dp,
                viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(107.059f, 33.288f)
                verticalLineTo(38.408f)
                curveTo(107.059f, 39.859f, 106.888f, 41.053f, 106.547f, 41.992f)
                curveTo(106.248f, 42.931f, 105.95f, 43.72f, 105.651f, 44.36f)
                lineTo(67.635f, 120.968f)
                curveTo(67.08f, 122.077f, 66.312f, 123.037f, 65.331f, 123.848f)
                curveTo(64.35f, 124.616f, 63.048f, 125.0f, 61.427f, 125.0f)
                horizontalLineTo(53.235f)
                lineTo(91.763f, 49.352f)
                curveTo(92.318f, 48.285f, 92.894f, 47.304f, 93.491f, 46.408f)
                curveTo(94.088f, 45.512f, 94.728f, 44.659f, 95.411f, 43.848f)
                horizontalLineTo(47.539f)
                curveTo(46.814f, 43.848f, 46.174f, 43.571f, 45.619f, 43.016f)
                curveTo(45.064f, 42.419f, 44.787f, 41.757f, 44.787f, 41.032f)
                verticalLineTo(33.288f)
                horizontalLineTo(107.059f)
                close()
            }
        }
        .build()
        return _key7!!
    }

private var _key7: ImageVector? = null
