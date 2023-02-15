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

val @receiver:Suppress("UNUSED") UnittoIcons.Comma: ImageVector
    get() {
        if (_comma != null) {
            return _comma!!
        }
        _comma = Builder(name = "Comma", defaultWidth = 150.0.dp, defaultHeight = 150.0.dp,
                viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(67.552f, 140.68f)
                curveTo(66.784f, 140.04f, 66.4f, 139.251f, 66.4f, 138.312f)
                curveTo(66.4f, 137.971f, 66.507f, 137.587f, 66.72f, 137.16f)
                curveTo(66.976f, 136.733f, 67.275f, 136.349f, 67.616f, 136.008f)
                curveTo(68.085f, 135.496f, 68.661f, 134.856f, 69.344f, 134.088f)
                curveTo(70.027f, 133.363f, 70.709f, 132.509f, 71.392f, 131.528f)
                curveTo(72.075f, 130.589f, 72.693f, 129.544f, 73.248f, 128.392f)
                curveTo(73.845f, 127.283f, 74.293f, 126.088f, 74.592f, 124.808f)
                curveTo(73.227f, 124.808f, 71.989f, 124.573f, 70.88f, 124.104f)
                curveTo(69.771f, 123.592f, 68.811f, 122.909f, 68.0f, 122.056f)
                curveTo(67.232f, 121.203f, 66.613f, 120.2f, 66.144f, 119.048f)
                curveTo(65.717f, 117.896f, 65.504f, 116.637f, 65.504f, 115.272f)
                curveTo(65.504f, 114.077f, 65.717f, 112.968f, 66.144f, 111.944f)
                curveTo(66.613f, 110.877f, 67.253f, 109.96f, 68.064f, 109.192f)
                curveTo(68.875f, 108.381f, 69.856f, 107.763f, 71.008f, 107.336f)
                curveTo(72.16f, 106.867f, 73.419f, 106.632f, 74.784f, 106.632f)
                curveTo(76.405f, 106.632f, 77.813f, 106.931f, 79.008f, 107.528f)
                curveTo(80.245f, 108.083f, 81.248f, 108.872f, 82.016f, 109.896f)
                curveTo(82.827f, 110.877f, 83.424f, 112.029f, 83.808f, 113.352f)
                curveTo(84.235f, 114.632f, 84.448f, 115.997f, 84.448f, 117.448f)
                curveTo(84.448f, 119.496f, 84.128f, 121.651f, 83.488f, 123.912f)
                curveTo(82.891f, 126.173f, 81.995f, 128.435f, 80.8f, 130.696f)
                curveTo(79.605f, 132.957f, 78.112f, 135.155f, 76.32f, 137.288f)
                curveTo(74.571f, 139.421f, 72.565f, 141.384f, 70.304f, 143.176f)
                lineTo(67.552f, 140.68f)
                close()
            }
        }
        .build()
        return _comma!!
    }

private var _comma: ImageVector? = null
