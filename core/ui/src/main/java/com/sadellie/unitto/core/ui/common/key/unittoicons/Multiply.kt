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

val @receiver:Suppress("UNUSED") UnittoIcons.Multiply: ImageVector
    get() {
        if (_multiply != null) {
            return _multiply!!
        }
        _multiply = Builder(name = "Multiply", defaultWidth = 150.0.dp, defaultHeight = 150.0.dp,
                viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(96.819f, 112.072f)
                lineTo(75.123f, 90.376f)
                lineTo(53.235f, 112.2f)
                lineTo(44.851f, 103.944f)
                lineTo(66.739f, 81.992f)
                lineTo(45.875f, 61.128f)
                lineTo(54.195f, 52.808f)
                lineTo(75.059f, 73.672f)
                lineTo(95.795f, 52.936f)
                lineTo(104.243f, 61.256f)
                lineTo(83.443f, 82.056f)
                lineTo(105.203f, 103.816f)
                lineTo(96.819f, 112.072f)
                close()
            }
        }
        .build()
        return _multiply!!
    }

private var _multiply: ImageVector? = null
