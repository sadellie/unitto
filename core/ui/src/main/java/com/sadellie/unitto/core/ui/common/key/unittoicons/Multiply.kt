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
                moveTo(97.779f, 110.856f)
                lineTo(75.123f, 88.136f)
                lineTo(52.275f, 110.984f)
                lineTo(46.195f, 104.84f)
                lineTo(68.979f, 82.056f)
                lineTo(46.899f, 59.976f)
                lineTo(52.979f, 53.832f)
                lineTo(75.059f, 75.912f)
                lineTo(97.011f, 53.96f)
                lineTo(103.155f, 60.104f)
                lineTo(81.203f, 82.056f)
                lineTo(103.859f, 104.712f)
                lineTo(97.779f, 110.856f)
                close()
            }
        }
        .build()
        return _multiply!!
    }

private var _multiply: ImageVector? = null
