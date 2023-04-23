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

val @receiver:Suppress("UNUSED") UnittoIcons.Key1: ImageVector
    get() {
        if (_key1 != null) {
            return _key1!!
        }
        _key1 = Builder(name = "Key1", defaultWidth = 124.0.dp, defaultHeight = 124.0.dp,
                viewportWidth = 124.0f, viewportHeight = 124.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(85.577f, 94.423f)
                verticalLineTo(102.0f)
                horizontalLineTo(42.791f)
                verticalLineTo(94.423f)
                horizontalLineTo(59.894f)
                verticalLineTo(39.994f)
                curveTo(59.894f, 38.36f, 59.95f, 36.708f, 60.061f, 35.036f)
                lineTo(45.855f, 47.237f)
                curveTo(45.372f, 47.645f, 44.889f, 47.905f, 44.407f, 48.017f)
                curveTo(43.924f, 48.091f, 43.478f, 48.091f, 43.07f, 48.017f)
                curveTo(42.661f, 47.942f, 42.271f, 47.794f, 41.9f, 47.571f)
                curveTo(41.565f, 47.348f, 41.306f, 47.107f, 41.12f, 46.847f)
                lineTo(38.0f, 42.557f)
                lineTo(61.788f, 22.0f)
                horizontalLineTo(69.866f)
                verticalLineTo(94.423f)
                horizontalLineTo(85.577f)
                close()
            }
        }
        .build()
        return _key1!!
    }

private var _key1: ImageVector? = null
