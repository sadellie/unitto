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
val UnittoIcons.PowerWide: ImageVector
    get() {
        if (_powerwide != null) {
            return _powerwide!!
        }
        _powerwide = Builder(name = "PowerWide", defaultWidth = 274.0.dp, defaultHeight = 170.0.dp,
                viewportWidth = 274.0f, viewportHeight = 170.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(166.563f, 87.064f)
                horizontalLineTo(157.983f)
                curveTo(157.235f, 87.064f, 156.575f, 86.866f, 156.003f, 86.47f)
                curveTo(155.475f, 86.03f, 155.057f, 85.48f, 154.749f, 84.82f)
                lineTo(139.569f, 55.582f)
                curveTo(138.425f, 53.25f, 137.545f, 51.116f, 136.929f, 49.18f)
                curveTo(136.621f, 50.192f, 136.269f, 51.226f, 135.873f, 52.282f)
                curveTo(135.477f, 53.338f, 135.037f, 54.438f, 134.553f, 55.582f)
                lineTo(119.835f, 84.82f)
                curveTo(119.571f, 85.436f, 119.153f, 85.964f, 118.581f, 86.404f)
                curveTo(118.009f, 86.844f, 117.283f, 87.064f, 116.403f, 87.064f)
                horizontalLineTo(107.427f)
                lineTo(133.035f, 38.422f)
                horizontalLineTo(140.625f)
                lineTo(166.563f, 87.064f)
                close()
            }
        }
        .build()
        return _powerwide!!
    }

private var _powerwide: ImageVector? = null
