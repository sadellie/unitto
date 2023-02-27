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

val @receiver:Suppress("UNUSED") UnittoIcons.Inv: ImageVector
    get() {
        if (_inv != null) {
            return _inv!!
        }
        _inv = Builder(name = "Inv", defaultWidth = 274.0.dp, defaultHeight = 150.0.dp,
                viewportWidth = 274.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(51.003f, 125.0f)
                horizontalLineTo(38.587f)
                verticalLineTo(33.288f)
                horizontalLineTo(51.003f)
                verticalLineTo(125.0f)
                close()
                moveTo(148.766f, 33.288f)
                verticalLineTo(125.0f)
                horizontalLineTo(142.558f)
                curveTo(141.577f, 125.0f, 140.745f, 124.829f, 140.062f, 124.488f)
                curveTo(139.422f, 124.147f, 138.782f, 123.571f, 138.142f, 122.76f)
                lineTo(85.086f, 53.64f)
                curveTo(85.171f, 54.707f, 85.235f, 55.752f, 85.278f, 56.776f)
                curveTo(85.321f, 57.8f, 85.342f, 58.76f, 85.342f, 59.656f)
                verticalLineTo(125.0f)
                horizontalLineTo(74.462f)
                verticalLineTo(33.288f)
                horizontalLineTo(80.862f)
                curveTo(81.417f, 33.288f, 81.886f, 33.331f, 82.27f, 33.416f)
                curveTo(82.654f, 33.459f, 82.995f, 33.565f, 83.294f, 33.736f)
                curveTo(83.593f, 33.864f, 83.891f, 34.077f, 84.19f, 34.376f)
                curveTo(84.489f, 34.632f, 84.809f, 34.973f, 85.15f, 35.4f)
                lineTo(138.206f, 104.456f)
                curveTo(138.121f, 103.347f, 138.035f, 102.28f, 137.95f, 101.256f)
                curveTo(137.907f, 100.189f, 137.886f, 99.187f, 137.886f, 98.248f)
                verticalLineTo(33.288f)
                horizontalLineTo(148.766f)
                close()
                moveTo(246.772f, 33.288f)
                lineTo(209.396f, 125.0f)
                horizontalLineTo(198.26f)
                lineTo(160.884f, 33.288f)
                horizontalLineTo(170.804f)
                curveTo(171.913f, 33.288f, 172.809f, 33.565f, 173.492f, 34.12f)
                curveTo(174.175f, 34.675f, 174.687f, 35.379f, 175.028f, 36.232f)
                lineTo(200.884f, 100.936f)
                curveTo(201.439f, 102.387f, 201.972f, 103.965f, 202.484f, 105.672f)
                curveTo(203.039f, 107.379f, 203.529f, 109.171f, 203.956f, 111.048f)
                curveTo(204.383f, 109.171f, 204.831f, 107.379f, 205.3f, 105.672f)
                curveTo(205.769f, 103.965f, 206.281f, 102.387f, 206.836f, 100.936f)
                lineTo(232.628f, 36.232f)
                curveTo(232.884f, 35.507f, 233.375f, 34.845f, 234.1f, 34.248f)
                curveTo(234.868f, 33.608f, 235.785f, 33.288f, 236.852f, 33.288f)
                horizontalLineTo(246.772f)
                close()
            }
        }
        .build()
        return _inv!!
    }

private var _inv: ImageVector? = null
