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

val @receiver:Suppress("UNUSED") UnittoIcons.Key4: ImageVector
    get() {
        if (_key4 != null) {
            return _key4!!
        }
        _key4 = Builder(name = "Key4", defaultWidth = 150.0.dp, defaultHeight = 150.0.dp,
                viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(84.595f, 91.912f)
                verticalLineTo(54.088f)
                curveTo(84.595f, 52.979f, 84.638f, 51.763f, 84.723f, 50.44f)
                curveTo(84.808f, 49.117f, 84.936f, 47.773f, 85.107f, 46.408f)
                lineTo(51.699f, 91.912f)
                horizontalLineTo(84.595f)
                close()
                moveTo(108.467f, 91.912f)
                verticalLineTo(98.44f)
                curveTo(108.467f, 99.123f, 108.254f, 99.699f, 107.827f, 100.168f)
                curveTo(107.443f, 100.637f, 106.824f, 100.872f, 105.971f, 100.872f)
                horizontalLineTo(94.579f)
                verticalLineTo(125.0f)
                horizontalLineTo(84.595f)
                verticalLineTo(100.872f)
                horizontalLineTo(43.891f)
                curveTo(43.038f, 100.872f, 42.291f, 100.637f, 41.651f, 100.168f)
                curveTo(41.054f, 99.656f, 40.67f, 99.037f, 40.499f, 98.312f)
                lineTo(39.347f, 92.488f)
                lineTo(83.955f, 33.288f)
                horizontalLineTo(94.579f)
                verticalLineTo(91.912f)
                horizontalLineTo(108.467f)
                close()
            }
        }
        .build()
        return _key4!!
    }

private var _key4: ImageVector? = null
