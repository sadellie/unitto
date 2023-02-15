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

val @receiver:Suppress("UNUSED") UnittoIcons.KeyE: ImageVector
    get() {
        if (_keye != null) {
            return _keye!!
        }
        _keye = Builder(name = "KeyE", defaultWidth = 150.0.dp, defaultHeight = 150.0.dp,
                viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(65.771f, 46.152f)
                verticalLineTo(71.816f)
                horizontalLineTo(98.156f)
                verticalLineTo(85.064f)
                horizontalLineTo(65.771f)
                verticalLineTo(111.24f)
                horizontalLineTo(106.86f)
                verticalLineTo(125.0f)
                horizontalLineTo(48.492f)
                verticalLineTo(32.456f)
                horizontalLineTo(106.86f)
                verticalLineTo(46.152f)
                horizontalLineTo(65.771f)
                close()
            }
        }
        .build()
        return _keye!!
    }

private var _keye: ImageVector? = null
