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
        _dot = Builder(name = "Dot", defaultWidth = 170.0.dp, defaultHeight = 170.0.dp,
                viewportWidth = 170.0f, viewportHeight = 170.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(77.0f, 85.316f)
                curveTo(77.0f, 84.172f, 77.198f, 83.094f, 77.594f, 82.082f)
                curveTo(78.034f, 81.07f, 78.606f, 80.19f, 79.31f, 79.442f)
                curveTo(80.058f, 78.694f, 80.938f, 78.1f, 81.95f, 77.66f)
                curveTo(82.962f, 77.22f, 84.04f, 77.0f, 85.184f, 77.0f)
                curveTo(86.328f, 77.0f, 87.406f, 77.22f, 88.418f, 77.66f)
                curveTo(89.43f, 78.1f, 90.31f, 78.694f, 91.058f, 79.442f)
                curveTo(91.806f, 80.19f, 92.4f, 81.07f, 92.84f, 82.082f)
                curveTo(93.28f, 83.094f, 93.5f, 84.172f, 93.5f, 85.316f)
                curveTo(93.5f, 86.504f, 93.28f, 87.604f, 92.84f, 88.616f)
                curveTo(92.4f, 89.584f, 91.806f, 90.442f, 91.058f, 91.19f)
                curveTo(90.31f, 91.938f, 89.43f, 92.51f, 88.418f, 92.906f)
                curveTo(87.406f, 93.346f, 86.328f, 93.566f, 85.184f, 93.566f)
                curveTo(84.04f, 93.566f, 82.962f, 93.346f, 81.95f, 92.906f)
                curveTo(80.938f, 92.51f, 80.058f, 91.938f, 79.31f, 91.19f)
                curveTo(78.606f, 90.442f, 78.034f, 89.584f, 77.594f, 88.616f)
                curveTo(77.198f, 87.604f, 77.0f, 86.504f, 77.0f, 85.316f)
                close()
            }
        }
        .build()
        return _dot!!
    }

private var _dot: ImageVector? = null
