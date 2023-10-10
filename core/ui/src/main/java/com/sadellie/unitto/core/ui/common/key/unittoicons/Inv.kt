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
val UnittoIcons.Inv: ImageVector
    get() {
        if (_inv != null) {
            return _inv!!
        }
        _inv = Builder(name = "Inv", defaultWidth = 274.0.dp, defaultHeight = 170.0.dp,
                viewportWidth = 274.0f, viewportHeight = 170.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(50.956f, 133.0f)
                horizontalLineTo(38.152f)
                verticalLineTo(38.422f)
                horizontalLineTo(50.956f)
                verticalLineTo(133.0f)
                close()
                moveTo(149.134f, 38.422f)
                verticalLineTo(133.0f)
                horizontalLineTo(142.732f)
                curveTo(141.72f, 133.0f, 140.862f, 132.824f, 140.158f, 132.472f)
                curveTo(139.498f, 132.12f, 138.838f, 131.526f, 138.178f, 130.69f)
                lineTo(83.464f, 59.41f)
                curveTo(83.552f, 60.51f, 83.618f, 61.588f, 83.662f, 62.644f)
                curveTo(83.706f, 63.7f, 83.728f, 64.69f, 83.728f, 65.614f)
                verticalLineTo(133.0f)
                horizontalLineTo(72.508f)
                verticalLineTo(38.422f)
                horizontalLineTo(79.108f)
                curveTo(79.68f, 38.422f, 80.164f, 38.466f, 80.56f, 38.554f)
                curveTo(80.956f, 38.598f, 81.308f, 38.708f, 81.616f, 38.884f)
                curveTo(81.924f, 39.016f, 82.232f, 39.236f, 82.54f, 39.544f)
                curveTo(82.848f, 39.808f, 83.178f, 40.16f, 83.53f, 40.6f)
                lineTo(138.244f, 111.814f)
                curveTo(138.156f, 110.67f, 138.068f, 109.57f, 137.98f, 108.514f)
                curveTo(137.936f, 107.414f, 137.914f, 106.38f, 137.914f, 105.412f)
                verticalLineTo(38.422f)
                horizontalLineTo(149.134f)
                close()
                moveTo(247.562f, 38.422f)
                lineTo(209.018f, 133.0f)
                horizontalLineTo(197.534f)
                lineTo(158.99f, 38.422f)
                horizontalLineTo(169.22f)
                curveTo(170.364f, 38.422f, 171.288f, 38.708f, 171.992f, 39.28f)
                curveTo(172.696f, 39.852f, 173.224f, 40.578f, 173.576f, 41.458f)
                lineTo(200.24f, 108.184f)
                curveTo(200.812f, 109.68f, 201.362f, 111.308f, 201.89f, 113.068f)
                curveTo(202.462f, 114.828f, 202.968f, 116.676f, 203.408f, 118.612f)
                curveTo(203.848f, 116.676f, 204.31f, 114.828f, 204.794f, 113.068f)
                curveTo(205.278f, 111.308f, 205.806f, 109.68f, 206.378f, 108.184f)
                lineTo(232.976f, 41.458f)
                curveTo(233.24f, 40.71f, 233.746f, 40.028f, 234.494f, 39.412f)
                curveTo(235.286f, 38.752f, 236.232f, 38.422f, 237.332f, 38.422f)
                horizontalLineTo(247.562f)
                close()
            }
        }
        .build()
        return _inv!!
    }

private var _inv: ImageVector? = null
