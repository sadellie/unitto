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

val @receiver:Suppress("UNUSED") UnittoIcons.Delete: ImageVector
    get() {
        if (_delete != null) {
            return _delete!!
        }
        _delete = Builder(name = "Delete", defaultWidth = 150.0.dp, defaultHeight = 150.0.dp,
                viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(66.675f, 79.944f)
                curveTo(65.822f, 80.371f, 64.904f, 80.755f, 63.923f, 81.096f)
                curveTo(62.984f, 81.437f, 61.96f, 81.715f, 60.851f, 81.928f)
                curveTo(61.96f, 82.184f, 62.984f, 82.483f, 63.923f, 82.824f)
                curveTo(64.904f, 83.123f, 65.822f, 83.507f, 66.675f, 83.976f)
                lineTo(95.411f, 98.504f)
                curveTo(96.264f, 98.931f, 96.862f, 99.421f, 97.203f, 99.976f)
                curveTo(97.544f, 100.531f, 97.715f, 101.149f, 97.715f, 101.832f)
                verticalLineTo(110.024f)
                lineTo(48.243f, 84.296f)
                verticalLineTo(79.56f)
                lineTo(97.715f, 53.896f)
                verticalLineTo(62.024f)
                curveTo(97.715f, 62.749f, 97.544f, 63.389f, 97.203f, 63.944f)
                curveTo(96.862f, 64.456f, 96.264f, 64.925f, 95.411f, 65.352f)
                lineTo(66.675f, 79.944f)
                close()
            }
        }
        .build()
        return _delete!!
    }

private var _delete: ImageVector? = null
