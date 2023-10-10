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
val UnittoIcons.Equal: ImageVector
    get() {
        if (_equal != null) {
            return _equal!!
        }
        _equal = Builder(name = "Equal", defaultWidth = 170.0.dp, defaultHeight = 170.0.dp,
                viewportWidth = 170.0f, viewportHeight = 170.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(37.0f, 103.283f)
                horizontalLineTo(133.69f)
                verticalLineTo(115.64f)
                horizontalLineTo(37.0f)
                verticalLineTo(103.283f)
                close()
                moveTo(37.0f, 54.0f)
                horizontalLineTo(133.69f)
                verticalLineTo(66.281f)
                horizontalLineTo(37.0f)
                verticalLineTo(54.0f)
                close()
            }
        }
        .build()
        return _equal!!
    }

private var _equal: ImageVector? = null
