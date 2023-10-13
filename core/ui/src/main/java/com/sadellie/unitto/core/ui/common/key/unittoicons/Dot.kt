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
        _dot = Builder(
            name = "Dot", defaultWidth = 170.0.dp, defaultHeight = 170.0.dp,
            viewportWidth = 170.0f, viewportHeight = 170.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(75.0f, 84.824f)
                curveTo(75.0f, 83.473f, 75.234f, 82.199f, 75.702f, 81.004f)
                curveTo(76.221f, 79.808f, 76.897f, 78.769f, 77.729f, 77.885f)
                curveTo(78.613f, 77.001f, 79.652f, 76.299f, 80.848f, 75.78f)
                curveTo(82.043f, 75.26f, 83.317f, 75.0f, 84.668f, 75.0f)
                curveTo(86.02f, 75.0f, 87.293f, 75.26f, 88.489f, 75.78f)
                curveTo(89.684f, 76.299f, 90.724f, 77.001f, 91.607f, 77.885f)
                curveTo(92.491f, 78.769f, 93.193f, 79.808f, 93.712f, 81.004f)
                curveTo(94.232f, 82.199f, 94.492f, 83.473f, 94.492f, 84.824f)
                curveTo(94.492f, 86.227f, 94.232f, 87.527f, 93.712f, 88.722f)
                curveTo(93.193f, 89.866f, 92.491f, 90.88f, 91.607f, 91.763f)
                curveTo(90.724f, 92.647f, 89.684f, 93.323f, 88.489f, 93.79f)
                curveTo(87.293f, 94.31f, 86.02f, 94.57f, 84.668f, 94.57f)
                curveTo(83.317f, 94.57f, 82.043f, 94.31f, 80.848f, 93.79f)
                curveTo(79.652f, 93.323f, 78.613f, 92.647f, 77.729f, 91.763f)
                curveTo(76.897f, 90.88f, 76.221f, 89.866f, 75.702f, 88.722f)
                curveTo(75.234f, 87.527f, 75.0f, 86.227f, 75.0f, 84.824f)
                close()
            }
        }
            .build()
        return _dot!!
    }

private var _dot: ImageVector? = null
