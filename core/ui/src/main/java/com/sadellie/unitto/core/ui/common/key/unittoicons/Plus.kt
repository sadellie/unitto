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

val @receiver:Suppress("UNUSED") UnittoIcons.Plus: ImageVector
    get() {
        if (_plus != null) {
            return _plus!!
        }
        _plus = Builder(name = "Plus", defaultWidth = 150.0.dp, defaultHeight = 150.0.dp,
                viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(105.715f, 86.344f)
                horizontalLineTo(79.603f)
                verticalLineTo(113.864f)
                horizontalLineTo(70.323f)
                verticalLineTo(86.344f)
                horizontalLineTo(44.339f)
                verticalLineTo(77.704f)
                horizontalLineTo(70.323f)
                verticalLineTo(50.376f)
                horizontalLineTo(79.603f)
                verticalLineTo(77.704f)
                horizontalLineTo(105.715f)
                verticalLineTo(86.344f)
                close()
            }
        }
        .build()
        return _plus!!
    }

private var _plus: ImageVector? = null
