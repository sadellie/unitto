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
        _key7 = Builder(name = "Key7", defaultWidth = 170.0.dp, defaultHeight = 170.0.dp,
                viewportWidth = 170.0f, viewportHeight = 170.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(118.061f, 38.422f)
                verticalLineTo(43.702f)
                curveTo(118.061f, 45.198f, 117.885f, 46.43f, 117.533f, 47.398f)
                curveTo(117.225f, 48.366f, 116.917f, 49.18f, 116.609f, 49.84f)
                lineTo(77.405f, 128.842f)
                curveTo(76.833f, 129.986f, 76.041f, 130.976f, 75.029f, 131.812f)
                curveTo(74.017f, 132.604f, 72.675f, 133.0f, 71.003f, 133.0f)
                horizontalLineTo(62.555f)
                lineTo(102.287f, 54.988f)
                curveTo(102.859f, 53.888f, 103.453f, 52.876f, 104.069f, 51.952f)
                curveTo(104.685f, 51.028f, 105.345f, 50.148f, 106.049f, 49.312f)
                horizontalLineTo(56.681f)
                curveTo(55.933f, 49.312f, 55.273f, 49.026f, 54.701f, 48.454f)
                curveTo(54.129f, 47.838f, 53.843f, 47.156f, 53.843f, 46.408f)
                verticalLineTo(38.422f)
                horizontalLineTo(118.061f)
                close()
            }
        }
        .build()
        return _key7!!
    }

private var _key7: ImageVector? = null
