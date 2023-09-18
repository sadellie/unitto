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
val UnittoIcons.Dot: ImageVector
    get() {
        if (_dot != null) {
            return _dot!!
        }
_dot = Builder(name = "Dot", defaultWidth = 124.0.dp, defaultHeight = 124.0.dp,
                viewportWidth = 124.0f, viewportHeight = 124.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(54.0f, 62.914f)
                curveTo(54.0f, 61.822f, 54.189f, 60.794f, 54.567f, 59.828f)
                curveTo(54.986f, 58.863f, 55.532f, 58.023f, 56.204f, 57.31f)
                curveTo(56.917f, 56.596f, 57.757f, 56.029f, 58.723f, 55.61f)
                curveTo(59.688f, 55.19f, 60.717f, 54.98f, 61.808f, 54.98f)
                curveTo(62.9f, 54.98f, 63.928f, 55.19f, 64.894f, 55.61f)
                curveTo(65.859f, 56.029f, 66.699f, 56.596f, 67.412f, 57.31f)
                curveTo(68.126f, 58.023f, 68.693f, 58.863f, 69.112f, 59.828f)
                curveTo(69.532f, 60.794f, 69.742f, 61.822f, 69.742f, 62.914f)
                curveTo(69.742f, 64.047f, 69.532f, 65.097f, 69.112f, 66.062f)
                curveTo(68.693f, 66.986f, 68.126f, 67.804f, 67.412f, 68.518f)
                curveTo(66.699f, 69.232f, 65.859f, 69.777f, 64.894f, 70.155f)
                curveTo(63.928f, 70.575f, 62.9f, 70.785f, 61.808f, 70.785f)
                curveTo(60.717f, 70.785f, 59.688f, 70.575f, 58.723f, 70.155f)
                curveTo(57.757f, 69.777f, 56.917f, 69.232f, 56.204f, 68.518f)
                curveTo(55.532f, 67.804f, 54.986f, 66.986f, 54.567f, 66.062f)
                curveTo(54.189f, 65.097f, 54.0f, 64.047f, 54.0f, 62.914f)
                close()
            }
        }
        .build()
        return _dot!!
    }

private var _dot: ImageVector? = null
