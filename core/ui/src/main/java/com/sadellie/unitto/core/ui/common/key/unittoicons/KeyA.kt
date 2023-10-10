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
val UnittoIcons.KeyA: ImageVector
    get() {
        if (_keya != null) {
            return _keya!!
        }
        _keya = Builder(name = "KeyA", defaultWidth = 170.0.dp, defaultHeight = 170.0.dp,
                viewportWidth = 170.0f, viewportHeight = 170.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(102.638f, 97.888f)
                lineTo(87.788f, 59.41f)
                curveTo(87.348f, 58.266f, 86.886f, 56.946f, 86.402f, 55.45f)
                curveTo(85.918f, 53.91f, 85.456f, 52.282f, 85.016f, 50.566f)
                curveTo(84.092f, 54.13f, 83.146f, 57.1f, 82.178f, 59.476f)
                lineTo(67.328f, 97.888f)
                horizontalLineTo(102.638f)
                close()
                moveTo(129.302f, 133.0f)
                horizontalLineTo(119.402f)
                curveTo(118.258f, 133.0f, 117.334f, 132.714f, 116.63f, 132.142f)
                curveTo(115.926f, 131.57f, 115.398f, 130.844f, 115.046f, 129.964f)
                lineTo(106.202f, 107.128f)
                horizontalLineTo(63.764f)
                lineTo(54.92f, 129.964f)
                curveTo(54.656f, 130.756f, 54.15f, 131.46f, 53.402f, 132.076f)
                curveTo(52.654f, 132.692f, 51.73f, 133.0f, 50.63f, 133.0f)
                horizontalLineTo(40.73f)
                lineTo(78.548f, 38.422f)
                horizontalLineTo(91.484f)
                lineTo(129.302f, 133.0f)
                close()
            }
        }
        .build()
        return _keya!!
    }

private var _keya: ImageVector? = null
