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
        _keye = Builder(name = "Keye", defaultWidth = 170.0.dp, defaultHeight = 170.0.dp,
                viewportWidth = 170.0f, viewportHeight = 170.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(117.33f, 122.572f)
                lineTo(117.264f, 133.0f)
                horizontalLineTo(58.986f)
                verticalLineTo(38.422f)
                horizontalLineTo(117.264f)
                verticalLineTo(48.85f)
                horizontalLineTo(71.79f)
                verticalLineTo(80.266f)
                horizontalLineTo(108.618f)
                verticalLineTo(90.298f)
                horizontalLineTo(71.79f)
                verticalLineTo(122.572f)
                horizontalLineTo(117.33f)
                close()
            }
        }
        .build()
        return _keye!!
    }

private var _keye: ImageVector? = null
