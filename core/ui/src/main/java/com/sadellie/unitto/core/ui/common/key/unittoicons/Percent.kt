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

val @receiver:Suppress("UNUSED") UnittoIcons.Percent: ImageVector
    get() {
        if (_percent != null) {
            return _percent!!
        }
        _percent = Builder(name = "Percent", defaultWidth = 124.0.dp, defaultHeight = 124.0.dp,
                viewportWidth = 124.0f, viewportHeight = 124.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 8.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(85.768f, 28.336f)
                lineTo(86.685f, 29.026f)
                lineTo(35.993f, 95.366f)
                lineTo(35.076f, 94.676f)
                lineTo(85.768f, 28.336f)
                close()
                moveTo(54.0f, 40.0f)
                curveTo(54.0f, 47.732f, 47.732f, 54.0f, 40.0f, 54.0f)
                curveTo(32.268f, 54.0f, 26.0f, 47.732f, 26.0f, 40.0f)
                curveTo(26.0f, 32.268f, 32.268f, 26.0f, 40.0f, 26.0f)
                curveTo(47.732f, 26.0f, 54.0f, 32.268f, 54.0f, 40.0f)
                close()
                moveTo(98.0f, 84.0f)
                curveTo(98.0f, 91.732f, 91.732f, 98.0f, 84.0f, 98.0f)
                curveTo(76.268f, 98.0f, 70.0f, 91.732f, 70.0f, 84.0f)
                curveTo(70.0f, 76.268f, 76.268f, 70.0f, 84.0f, 70.0f)
                curveTo(91.732f, 70.0f, 98.0f, 76.268f, 98.0f, 84.0f)
                close()
            }
        }
        .build()
        return _percent!!
    }

private var _percent: ImageVector? = null
