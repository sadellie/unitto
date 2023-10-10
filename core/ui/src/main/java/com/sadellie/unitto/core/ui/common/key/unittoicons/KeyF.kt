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
val UnittoIcons.KeyF: ImageVector
    get() {
        if (_keyf != null) {
            return _keyf!!
        }
        _keyf = Builder(name = "KeyF", defaultWidth = 170.0.dp, defaultHeight = 170.0.dp,
                viewportWidth = 170.0f, viewportHeight = 170.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(72.564f, 48.85f)
                verticalLineTo(81.916f)
                horizontalLineTo(111.438f)
                verticalLineTo(92.344f)
                horizontalLineTo(72.564f)
                verticalLineTo(133.0f)
                horizontalLineTo(59.76f)
                verticalLineTo(38.422f)
                horizontalLineTo(118.038f)
                verticalLineTo(48.85f)
                horizontalLineTo(72.564f)
                close()
            }
        }
        .build()
        return _keyf!!
    }

private var _keyf: ImageVector? = null
