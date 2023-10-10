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
        _keyd = Builder(name = "KeyD", defaultWidth = 170.0.dp, defaultHeight = 170.0.dp,
                viewportWidth = 170.0f, viewportHeight = 170.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(128.841f, 85.744f)
                curveTo(128.841f, 92.828f, 127.719f, 99.274f, 125.475f, 105.082f)
                curveTo(123.231f, 110.89f, 120.063f, 115.862f, 115.971f, 119.998f)
                curveTo(111.879f, 124.134f, 106.951f, 127.346f, 101.187f, 129.634f)
                curveTo(95.467f, 131.878f, 89.131f, 133.0f, 82.179f, 133.0f)
                horizontalLineTo(46.869f)
                verticalLineTo(38.422f)
                horizontalLineTo(82.179f)
                curveTo(89.131f, 38.422f, 95.467f, 39.566f, 101.187f, 41.854f)
                curveTo(106.951f, 44.098f, 111.879f, 47.31f, 115.971f, 51.49f)
                curveTo(120.063f, 55.626f, 123.231f, 60.598f, 125.475f, 66.406f)
                curveTo(127.719f, 72.214f, 128.841f, 78.66f, 128.841f, 85.744f)
                close()
                moveTo(115.707f, 85.744f)
                curveTo(115.707f, 79.936f, 114.915f, 74.744f, 113.331f, 70.168f)
                curveTo(111.747f, 65.592f, 109.503f, 61.72f, 106.599f, 58.552f)
                curveTo(103.695f, 55.384f, 100.175f, 52.964f, 96.039f, 51.292f)
                curveTo(91.903f, 49.62f, 87.283f, 48.784f, 82.179f, 48.784f)
                horizontalLineTo(59.673f)
                verticalLineTo(122.638f)
                horizontalLineTo(82.179f)
                curveTo(87.283f, 122.638f, 91.903f, 121.802f, 96.039f, 120.13f)
                curveTo(100.175f, 118.458f, 103.695f, 116.06f, 106.599f, 112.936f)
                curveTo(109.503f, 109.768f, 111.747f, 105.896f, 113.331f, 101.32f)
                curveTo(114.915f, 96.744f, 115.707f, 91.552f, 115.707f, 85.744f)
                close()
            }
        }
        .build()
        return _keyd!!
    }

private var _keyd: ImageVector? = null
