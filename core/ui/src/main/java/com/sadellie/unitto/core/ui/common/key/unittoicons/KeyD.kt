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
val UnittoIcons.KeyD: ImageVector
    get() {
        if (_keyd != null) {
            return _keyd!!
        }
        _keyd = Builder(name = "KeyD", defaultWidth = 150.0.dp, defaultHeight = 150.0.dp,
                viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(117.513f, 79.176f)
                curveTo(117.513f, 86.045f, 116.425f, 92.296f, 114.249f, 97.928f)
                curveTo(112.073f, 103.56f, 109.001f, 108.381f, 105.033f, 112.392f)
                curveTo(101.065f, 116.403f, 96.286f, 119.517f, 90.697f, 121.736f)
                curveTo(85.15f, 123.912f, 79.006f, 125.0f, 72.265f, 125.0f)
                horizontalLineTo(38.025f)
                verticalLineTo(33.288f)
                horizontalLineTo(72.265f)
                curveTo(79.006f, 33.288f, 85.15f, 34.397f, 90.697f, 36.616f)
                curveTo(96.286f, 38.792f, 101.065f, 41.907f, 105.033f, 45.96f)
                curveTo(109.001f, 49.971f, 112.073f, 54.792f, 114.249f, 60.424f)
                curveTo(116.425f, 66.056f, 117.513f, 72.307f, 117.513f, 79.176f)
                close()
                moveTo(104.777f, 79.176f)
                curveTo(104.777f, 73.544f, 104.009f, 68.509f, 102.473f, 64.072f)
                curveTo(100.937f, 59.635f, 98.76f, 55.88f, 95.945f, 52.808f)
                curveTo(93.129f, 49.736f, 89.715f, 47.389f, 85.704f, 45.768f)
                curveTo(81.694f, 44.147f, 77.214f, 43.336f, 72.265f, 43.336f)
                horizontalLineTo(50.44f)
                verticalLineTo(114.952f)
                horizontalLineTo(72.265f)
                curveTo(77.214f, 114.952f, 81.694f, 114.141f, 85.704f, 112.52f)
                curveTo(89.715f, 110.899f, 93.129f, 108.573f, 95.945f, 105.544f)
                curveTo(98.76f, 102.472f, 100.937f, 98.717f, 102.473f, 94.28f)
                curveTo(104.009f, 89.843f, 104.777f, 84.808f, 104.777f, 79.176f)
                close()
            }
        }
        .build()
        return _keyd!!
    }

private var _keyd: ImageVector? = null
