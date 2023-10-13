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
val UnittoIcons.Percent: ImageVector
    get() {
        if (_percent != null) {
            return _percent!!
        }
        _percent = Builder(
            name = "Percent", defaultWidth = 170.0.dp, defaultHeight = 170.0.dp,
            viewportWidth = 170.0f, viewportHeight = 170.0f
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 8.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(113.58f, 43.477f)
                lineTo(115.868f, 45.198f)
                lineTo(54.761f, 125.169f)
                lineTo(52.473f, 123.448f)
                lineTo(113.58f, 43.477f)
                close()
                moveTo(76.75f, 58.375f)
                curveTo(76.75f, 67.971f, 68.971f, 75.75f, 59.375f, 75.75f)
                curveTo(49.779f, 75.75f, 42.0f, 67.971f, 42.0f, 58.375f)
                curveTo(42.0f, 48.779f, 49.779f, 41.0f, 59.375f, 41.0f)
                curveTo(68.971f, 41.0f, 76.75f, 48.779f, 76.75f, 58.375f)
                close()
                moveTo(129.0f, 110.625f)
                curveTo(129.0f, 120.221f, 121.221f, 128.0f, 111.625f, 128.0f)
                curveTo(102.029f, 128.0f, 94.25f, 120.221f, 94.25f, 110.625f)
                curveTo(94.25f, 101.029f, 102.029f, 93.25f, 111.625f, 93.25f)
                curveTo(121.221f, 93.25f, 129.0f, 101.029f, 129.0f, 110.625f)
                close()
            }
        }
            .build()
        return _percent!!
    }

private var _percent: ImageVector? = null
