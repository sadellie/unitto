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

val @receiver:Suppress("UNUSED") UnittoIcons.Backspace: ImageVector
    get() {
        if (_backspace != null) {
            return _backspace!!
        }
        _backspace = Builder(name = "Backspace", defaultWidth = 124.0.dp, defaultHeight = 124.0.dp,
                viewportWidth = 124.0f, viewportHeight = 124.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(59.333f, 79.889f)
                lineTo(70.889f, 68.333f)
                lineTo(82.445f, 79.889f)
                lineTo(88.667f, 73.667f)
                lineTo(77.111f, 62.111f)
                lineTo(88.667f, 50.556f)
                lineTo(82.445f, 44.333f)
                lineTo(70.889f, 55.889f)
                lineTo(59.333f, 44.333f)
                lineTo(53.111f, 50.556f)
                lineTo(64.667f, 62.111f)
                lineTo(53.111f, 73.667f)
                lineTo(59.333f, 79.889f)
                close()
                moveTo(22.0f, 62.111f)
                lineTo(41.333f, 34.778f)
                curveTo(42.148f, 33.593f, 43.204f, 32.667f, 44.5f, 32.0f)
                curveTo(45.796f, 31.333f, 47.185f, 31.0f, 48.667f, 31.0f)
                horizontalLineTo(93.111f)
                curveTo(95.556f, 31.0f, 97.648f, 31.87f, 99.389f, 33.611f)
                curveTo(101.13f, 35.352f, 102.0f, 37.444f, 102.0f, 39.889f)
                verticalLineTo(84.333f)
                curveTo(102.0f, 86.778f, 101.13f, 88.87f, 99.389f, 90.611f)
                curveTo(97.648f, 92.352f, 95.556f, 93.222f, 93.111f, 93.222f)
                horizontalLineTo(48.667f)
                curveTo(47.185f, 93.222f, 45.796f, 92.889f, 44.5f, 92.222f)
                curveTo(43.204f, 91.556f, 42.148f, 90.63f, 41.333f, 89.444f)
                lineTo(22.0f, 62.111f)
                close()
            }
        }
        .build()
        return _backspace!!
    }

private var _backspace: ImageVector? = null
