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
val UnittoIcons.Backspace: ImageVector
    get() {
        if (_backspace != null) {
            return _backspace!!
        }
        _backspace = Builder(
            name = "Backspace", defaultWidth = 170.0.dp, defaultHeight = 170.0.dp,
            viewportWidth = 170.0f, viewportHeight = 170.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(81.344f, 109.525f)
                lineTo(97.186f, 93.683f)
                lineTo(113.029f, 109.525f)
                lineTo(121.559f, 100.995f)
                lineTo(105.717f, 85.152f)
                lineTo(121.559f, 69.31f)
                lineTo(113.029f, 60.78f)
                lineTo(97.186f, 76.622f)
                lineTo(81.344f, 60.78f)
                lineTo(72.813f, 69.31f)
                lineTo(88.656f, 85.152f)
                lineTo(72.813f, 100.995f)
                lineTo(81.344f, 109.525f)
                close()
                moveTo(30.161f, 85.152f)
                lineTo(56.667f, 47.679f)
                curveTo(57.784f, 46.054f, 59.231f, 44.785f, 61.008f, 43.871f)
                curveTo(62.785f, 42.957f, 64.689f, 42.5f, 66.72f, 42.5f)
                horizontalLineTo(127.652f)
                curveTo(131.003f, 42.5f, 133.872f, 43.693f, 136.259f, 46.08f)
                curveTo(138.645f, 48.466f, 139.839f, 51.335f, 139.839f, 54.686f)
                verticalLineTo(115.618f)
                curveTo(139.839f, 118.97f, 138.645f, 121.838f, 136.259f, 124.225f)
                curveTo(133.872f, 126.611f, 131.003f, 127.805f, 127.652f, 127.805f)
                horizontalLineTo(66.72f)
                curveTo(64.689f, 127.805f, 62.785f, 127.348f, 61.008f, 126.434f)
                curveTo(59.231f, 125.52f, 57.784f, 124.25f, 56.667f, 122.625f)
                lineTo(30.161f, 85.152f)
                close()
            }
        }
            .build()
        return _backspace!!
    }

private var _backspace: ImageVector? = null
