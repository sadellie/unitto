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
val UnittoIcons.KeyE: ImageVector
    get() {
        if (_keye != null) {
            return _keye!!
        }
        _keye = Builder(name = "KeyE", defaultWidth = 150.0.dp, defaultHeight = 150.0.dp,
                viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(106.351f, 114.888f)
                lineTo(106.287f, 125.0f)
                horizontalLineTo(49.775f)
                verticalLineTo(33.288f)
                horizontalLineTo(106.287f)
                verticalLineTo(43.4f)
                horizontalLineTo(62.19f)
                verticalLineTo(73.864f)
                horizontalLineTo(97.902f)
                verticalLineTo(83.592f)
                horizontalLineTo(62.19f)
                verticalLineTo(114.888f)
                horizontalLineTo(106.351f)
                close()
            }
        }
        .build()
        return _keye!!
    }

private var _keye: ImageVector? = null
