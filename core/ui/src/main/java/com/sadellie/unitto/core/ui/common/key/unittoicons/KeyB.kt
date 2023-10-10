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
val UnittoIcons.KeyB: ImageVector
    get() {
        if (_keyb != null) {
            return _keyb!!
        }
        _keyb = Builder(name = "KeyB", defaultWidth = 170.0.dp, defaultHeight = 170.0.dp,
                viewportWidth = 170.0f, viewportHeight = 170.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(87.608f, 122.836f)
                curveTo(91.26f, 122.836f, 94.406f, 122.418f, 97.046f, 121.582f)
                curveTo(99.686f, 120.746f, 101.842f, 119.58f, 103.514f, 118.084f)
                curveTo(105.23f, 116.544f, 106.484f, 114.74f, 107.276f, 112.672f)
                curveTo(108.068f, 110.56f, 108.464f, 108.25f, 108.464f, 105.742f)
                curveTo(108.464f, 100.858f, 106.726f, 97.008f, 103.25f, 94.192f)
                curveTo(99.774f, 91.332f, 94.56f, 89.902f, 87.608f, 89.902f)
                horizontalLineTo(67.148f)
                verticalLineTo(122.836f)
                horizontalLineTo(87.608f)
                close()
                moveTo(67.148f, 48.52f)
                verticalLineTo(80.794f)
                horizontalLineTo(84.044f)
                curveTo(87.652f, 80.794f, 90.776f, 80.398f, 93.416f, 79.606f)
                curveTo(96.1f, 78.814f, 98.3f, 77.714f, 100.016f, 76.306f)
                curveTo(101.776f, 74.898f, 103.074f, 73.204f, 103.91f, 71.224f)
                curveTo(104.746f, 69.2f, 105.164f, 67.0f, 105.164f, 64.624f)
                curveTo(105.164f, 59.036f, 103.492f, 54.966f, 100.148f, 52.414f)
                curveTo(96.804f, 49.818f, 91.612f, 48.52f, 84.572f, 48.52f)
                horizontalLineTo(67.148f)
                close()
                moveTo(84.572f, 38.422f)
                curveTo(90.38f, 38.422f, 95.374f, 38.994f, 99.554f, 40.138f)
                curveTo(103.778f, 41.282f, 107.232f, 42.91f, 109.916f, 45.022f)
                curveTo(112.644f, 47.134f, 114.646f, 49.73f, 115.922f, 52.81f)
                curveTo(117.198f, 55.846f, 117.836f, 59.278f, 117.836f, 63.106f)
                curveTo(117.836f, 65.438f, 117.462f, 67.682f, 116.714f, 69.838f)
                curveTo(116.01f, 71.95f, 114.932f, 73.93f, 113.48f, 75.778f)
                curveTo(112.028f, 77.626f, 110.18f, 79.298f, 107.936f, 80.794f)
                curveTo(105.736f, 82.246f, 103.14f, 83.434f, 100.148f, 84.358f)
                curveTo(107.1f, 85.678f, 112.314f, 88.164f, 115.79f, 91.816f)
                curveTo(119.31f, 95.424f, 121.07f, 100.176f, 121.07f, 106.072f)
                curveTo(121.07f, 110.076f, 120.322f, 113.728f, 118.826f, 117.028f)
                curveTo(117.374f, 120.328f, 115.218f, 123.166f, 112.358f, 125.542f)
                curveTo(109.542f, 127.918f, 106.066f, 129.766f, 101.93f, 131.086f)
                curveTo(97.794f, 132.362f, 93.086f, 133.0f, 87.806f, 133.0f)
                horizontalLineTo(54.41f)
                verticalLineTo(38.422f)
                horizontalLineTo(84.572f)
                close()
            }
        }
        .build()
        return _keyb!!
    }

private var _keyb: ImageVector? = null
