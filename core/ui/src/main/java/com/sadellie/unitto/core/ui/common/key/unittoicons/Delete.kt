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
                moveTo(72.307f, 79.56f)
                curveTo(70.302f, 80.541f, 68.104f, 81.309f, 65.715f, 81.864f)
                curveTo(68.104f, 82.419f, 70.302f, 83.187f, 72.307f, 84.168f)
                lineTo(95.539f, 96.136f)
                curveTo(96.52f, 96.605f, 97.224f, 97.203f, 97.651f, 97.928f)
                curveTo(98.078f, 98.653f, 98.291f, 99.443f, 98.291f, 100.296f)
                verticalLineTo(111.56f)
                lineTo(47.539f, 85.064f)
                verticalLineTo(78.664f)
                lineTo(98.291f, 52.232f)
                verticalLineTo(63.496f)
                curveTo(98.291f, 64.349f, 98.078f, 65.139f, 97.651f, 65.864f)
                curveTo(97.224f, 66.547f, 96.52f, 67.144f, 95.539f, 67.656f)
                lineTo(72.307f, 79.56f)
                close()
            }
        }
        .build()
        return _delete!!
    }

private var _delete: ImageVector? = null
