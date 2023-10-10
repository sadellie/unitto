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
val UnittoIcons.RootWide: ImageVector
    get() {
        if (_rootwide != null) {
            return _rootwide!!
        }
        _rootwide = Builder(name = "Rootwide", defaultWidth = 274.0.dp, defaultHeight = 170.0.dp,
                viewportWidth = 274.0f, viewportHeight = 170.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(177.328f, 20.008f)
                lineTo(140.83f, 133.0f)
                horizontalLineTo(130.93f)
                lineTo(114.43f, 89.242f)
                horizontalLineTo(103.144f)
                curveTo(101.912f, 89.242f, 100.834f, 88.89f, 99.91f, 88.186f)
                curveTo(99.03f, 87.438f, 98.59f, 86.162f, 98.59f, 84.358f)
                verticalLineTo(80.596f)
                horizontalLineTo(120.7f)
                curveTo(121.712f, 80.596f, 122.526f, 80.838f, 123.142f, 81.322f)
                curveTo(123.758f, 81.806f, 124.176f, 82.378f, 124.396f, 83.038f)
                lineTo(134.23f, 110.164f)
                curveTo(134.714f, 111.616f, 135.088f, 113.068f, 135.352f, 114.52f)
                curveTo(135.66f, 115.972f, 135.924f, 117.446f, 136.144f, 118.942f)
                curveTo(136.364f, 117.754f, 136.584f, 116.566f, 136.804f, 115.378f)
                curveTo(137.068f, 114.146f, 137.376f, 112.892f, 137.728f, 111.616f)
                lineTo(166.306f, 22.45f)
                curveTo(166.526f, 21.746f, 166.944f, 21.174f, 167.56f, 20.734f)
                curveTo(168.176f, 20.25f, 168.902f, 20.008f, 169.738f, 20.008f)
                horizontalLineTo(177.328f)
                close()
            }
        }
        .build()
        return _rootwide!!
    }

private var _rootwide: ImageVector? = null
