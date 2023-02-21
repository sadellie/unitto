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

val @receiver:Suppress("UNUSED") UnittoIcons.Equal: ImageVector
    get() {
        if (_equal != null) {
            return _equal!!
        }
        _equal = Builder(name = "Equal", defaultWidth = 150.0.dp, defaultHeight = 150.0.dp,
                viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(47.475f, 88.264f)
                horizontalLineTo(102.515f)
                verticalLineTo(96.904f)
                horizontalLineTo(47.475f)
                verticalLineTo(88.264f)
                close()
                moveTo(47.475f, 66.824f)
                horizontalLineTo(102.515f)
                verticalLineTo(75.464f)
                horizontalLineTo(47.475f)
                verticalLineTo(66.824f)
                close()
            }
        }
        .build()
        return _equal!!
    }

private var _equal: ImageVector? = null
