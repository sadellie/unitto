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
                moveTo(83.443f, 90.12f)
                verticalLineTo(59.848f)
                curveTo(83.443f, 56.989f, 83.635f, 53.832f, 84.019f, 50.376f)
                lineTo(55.283f, 90.12f)
                horizontalLineTo(83.443f)
                close()
                moveTo(108.851f, 90.12f)
                verticalLineTo(99.272f)
                curveTo(108.851f, 100.125f, 108.574f, 100.872f, 108.019f, 101.512f)
                curveTo(107.464f, 102.109f, 106.675f, 102.408f, 105.651f, 102.408f)
                horizontalLineTo(97.203f)
                verticalLineTo(125.0f)
                horizontalLineTo(83.443f)
                verticalLineTo(102.408f)
                horizontalLineTo(44.403f)
                curveTo(43.336f, 102.408f, 42.398f, 102.088f, 41.587f, 101.448f)
                curveTo(40.776f, 100.765f, 40.264f, 99.933f, 40.051f, 98.952f)
                lineTo(38.387f, 90.952f)
                lineTo(82.227f, 32.392f)
                horizontalLineTo(97.203f)
                verticalLineTo(90.12f)
                horizontalLineTo(108.851f)
                close()
            }
        }
        .build()
        return _key4!!
    }

private var _key4: ImageVector? = null
