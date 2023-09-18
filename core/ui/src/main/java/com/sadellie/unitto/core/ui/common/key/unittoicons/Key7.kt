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
val UnittoIcons.Key7: ImageVector
    get() {
        if (_key7 != null) {
            return _key7!!
        }
        _key7 = Builder(name = "Key7", defaultWidth = 124.0.dp, defaultHeight = 124.0.dp,
                viewportWidth = 124.0f, viewportHeight = 124.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(88.676f, 22.0f)
                verticalLineTo(26.466f)
                curveTo(88.676f, 27.732f, 88.527f, 28.774f, 88.229f, 29.593f)
                curveTo(87.968f, 30.411f, 87.708f, 31.1f, 87.447f, 31.658f)
                lineTo(54.286f, 98.483f)
                curveTo(53.802f, 99.451f, 53.132f, 100.288f, 52.276f, 100.995f)
                curveTo(51.42f, 101.665f, 50.285f, 102.0f, 48.871f, 102.0f)
                horizontalLineTo(41.725f)
                lineTo(75.333f, 36.013f)
                curveTo(75.817f, 35.082f, 76.319f, 34.226f, 76.84f, 33.444f)
                curveTo(77.361f, 32.663f, 77.92f, 31.919f, 78.515f, 31.211f)
                horizontalLineTo(36.757f)
                curveTo(36.124f, 31.211f, 35.565f, 30.969f, 35.082f, 30.486f)
                curveTo(34.598f, 29.965f, 34.356f, 29.388f, 34.356f, 28.755f)
                verticalLineTo(22.0f)
                horizontalLineTo(88.676f)
                close()
            }
        }
        .build()
        return _key7!!
    }

private var _key7: ImageVector? = null
