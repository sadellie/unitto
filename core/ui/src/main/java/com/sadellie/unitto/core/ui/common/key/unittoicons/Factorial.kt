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
val UnittoIcons.Factorial: ImageVector
    get() {
        if (_factorial != null) {
            return _factorial!!
        }
        _factorial = Builder(name = "Factorial", defaultWidth = 274.0.dp, defaultHeight = 170.0.dp,
                viewportWidth = 274.0f, viewportHeight = 170.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(142.705f, 38.422f)
                verticalLineTo(76.174f)
                curveTo(142.705f, 78.154f, 142.661f, 80.09f, 142.573f, 81.982f)
                curveTo(142.485f, 83.874f, 142.375f, 85.788f, 142.243f, 87.724f)
                curveTo(142.111f, 89.616f, 141.935f, 91.574f, 141.715f, 93.598f)
                curveTo(141.539f, 95.578f, 141.319f, 97.712f, 141.055f, 100.0f)
                horizontalLineTo(133.135f)
                curveTo(132.871f, 97.712f, 132.629f, 95.578f, 132.409f, 93.598f)
                curveTo(132.233f, 91.574f, 132.079f, 89.616f, 131.947f, 87.724f)
                curveTo(131.815f, 85.788f, 131.705f, 83.874f, 131.617f, 81.982f)
                curveTo(131.529f, 80.09f, 131.485f, 78.154f, 131.485f, 76.174f)
                verticalLineTo(38.422f)
                horizontalLineTo(142.705f)
                close()
                moveTo(128.647f, 125.74f)
                curveTo(128.647f, 124.596f, 128.845f, 123.518f, 129.241f, 122.506f)
                curveTo(129.681f, 121.494f, 130.275f, 120.614f, 131.023f, 119.866f)
                curveTo(131.771f, 119.118f, 132.629f, 118.524f, 133.597f, 118.084f)
                curveTo(134.609f, 117.644f, 135.709f, 117.424f, 136.897f, 117.424f)
                curveTo(138.041f, 117.424f, 139.097f, 117.644f, 140.065f, 118.084f)
                curveTo(141.077f, 118.524f, 141.957f, 119.118f, 142.705f, 119.866f)
                curveTo(143.453f, 120.614f, 144.047f, 121.494f, 144.487f, 122.506f)
                curveTo(144.927f, 123.518f, 145.147f, 124.596f, 145.147f, 125.74f)
                curveTo(145.147f, 126.928f, 144.927f, 128.028f, 144.487f, 129.04f)
                curveTo(144.047f, 130.008f, 143.453f, 130.866f, 142.705f, 131.614f)
                curveTo(141.957f, 132.362f, 141.077f, 132.934f, 140.065f, 133.33f)
                curveTo(139.097f, 133.77f, 138.041f, 133.99f, 136.897f, 133.99f)
                curveTo(135.709f, 133.99f, 134.609f, 133.77f, 133.597f, 133.33f)
                curveTo(132.629f, 132.934f, 131.771f, 132.362f, 131.023f, 131.614f)
                curveTo(130.275f, 130.866f, 129.681f, 130.008f, 129.241f, 129.04f)
                curveTo(128.845f, 128.028f, 128.647f, 126.928f, 128.647f, 125.74f)
                close()
            }
        }
        .build()
        return _factorial!!
    }

private var _factorial: ImageVector? = null
