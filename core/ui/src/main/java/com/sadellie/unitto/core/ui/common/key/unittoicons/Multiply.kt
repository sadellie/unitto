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

@Suppress("UnusedReceiverParameter")
val UnittoIcons.Multiply: ImageVector
    get() {
        if (_multiply != null) {
            return _multiply!!
        }
        _multiply = Builder(name = "Multiply", defaultWidth = 170.0.dp, defaultHeight = 170.0.dp,
                viewportWidth = 170.0f, viewportHeight = 170.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(37.0f, 124.46f)
                lineTo(124.46f, 37.0f)
                lineTo(133.69f, 46.229f)
                lineTo(46.229f, 133.69f)
                lineTo(37.0f, 124.46f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(46.229f, 37.0f)
                lineTo(133.69f, 124.461f)
                lineTo(124.461f, 133.69f)
                lineTo(37.0f, 46.229f)
                lineTo(46.229f, 37.0f)
                close()
            }
        }
        .build()
        return _multiply!!
    }

private var _multiply: ImageVector? = null
