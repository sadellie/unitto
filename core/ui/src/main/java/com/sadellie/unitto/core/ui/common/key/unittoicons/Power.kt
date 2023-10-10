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
val UnittoIcons.Power: ImageVector
    get() {
        if (_power != null) {
            return _power!!
        }
        _power = Builder(name = "Power", defaultWidth = 170.0.dp, defaultHeight = 170.0.dp,
                viewportWidth = 170.0f, viewportHeight = 170.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(114.563f, 87.064f)
                horizontalLineTo(105.983f)
                curveTo(105.235f, 87.064f, 104.575f, 86.866f, 104.003f, 86.47f)
                curveTo(103.475f, 86.03f, 103.057f, 85.48f, 102.749f, 84.82f)
                lineTo(87.569f, 55.582f)
                curveTo(86.425f, 53.25f, 85.545f, 51.116f, 84.929f, 49.18f)
                curveTo(84.621f, 50.192f, 84.269f, 51.226f, 83.873f, 52.282f)
                curveTo(83.477f, 53.338f, 83.037f, 54.438f, 82.553f, 55.582f)
                lineTo(67.835f, 84.82f)
                curveTo(67.571f, 85.436f, 67.153f, 85.964f, 66.581f, 86.404f)
                curveTo(66.009f, 86.844f, 65.283f, 87.064f, 64.403f, 87.064f)
                horizontalLineTo(55.427f)
                lineTo(81.035f, 38.422f)
                horizontalLineTo(88.625f)
                lineTo(114.563f, 87.064f)
                close()
            }
        }
        .build()
        return _power!!
    }

private var _power: ImageVector? = null
