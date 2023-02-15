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
                moveTo(46.643f, 87.368f)
                horizontalLineTo(103.347f)
                verticalLineTo(99.144f)
                horizontalLineTo(46.643f)
                verticalLineTo(87.368f)
                close()
                moveTo(46.643f, 64.84f)
                horizontalLineTo(103.347f)
                verticalLineTo(76.552f)
                horizontalLineTo(46.643f)
                verticalLineTo(64.84f)
                close()
            }
        }
        .build()
        return _equal!!
    }

private var _equal: ImageVector? = null
