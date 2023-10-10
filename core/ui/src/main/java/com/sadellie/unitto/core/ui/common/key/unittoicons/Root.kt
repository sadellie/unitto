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
val UnittoIcons.Root: ImageVector
    get() {
        if (_root != null) {
            return _root!!
        }
        _root = Builder(name = "Root", defaultWidth = 170.0.dp, defaultHeight = 170.0.dp,
                viewportWidth = 170.0f, viewportHeight = 170.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(125.328f, 20.008f)
                lineTo(88.829f, 133.0f)
                horizontalLineTo(78.929f)
                lineTo(62.43f, 89.242f)
                horizontalLineTo(51.144f)
                curveTo(49.911f, 89.242f, 48.833f, 88.89f, 47.91f, 88.186f)
                curveTo(47.029f, 87.438f, 46.59f, 86.162f, 46.59f, 84.358f)
                verticalLineTo(80.596f)
                horizontalLineTo(68.7f)
                curveTo(69.712f, 80.596f, 70.525f, 80.838f, 71.142f, 81.322f)
                curveTo(71.757f, 81.806f, 72.175f, 82.378f, 72.396f, 83.038f)
                lineTo(82.23f, 110.164f)
                curveTo(82.714f, 111.616f, 83.088f, 113.068f, 83.352f, 114.52f)
                curveTo(83.66f, 115.972f, 83.924f, 117.446f, 84.144f, 118.942f)
                curveTo(84.364f, 117.754f, 84.584f, 116.566f, 84.803f, 115.378f)
                curveTo(85.067f, 114.146f, 85.376f, 112.892f, 85.728f, 111.616f)
                lineTo(114.306f, 22.45f)
                curveTo(114.526f, 21.746f, 114.944f, 21.174f, 115.56f, 20.734f)
                curveTo(116.176f, 20.25f, 116.902f, 20.008f, 117.738f, 20.008f)
                horizontalLineTo(125.328f)
                close()
            }
        }
        .build()
        return _root!!
    }

private var _root: ImageVector? = null
