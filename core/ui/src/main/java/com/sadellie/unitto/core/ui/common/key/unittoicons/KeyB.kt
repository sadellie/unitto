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

val @receiver:Suppress("UNUSED") UnittoIcons.KeyB: ImageVector
    get() {
        if (_keyb != null) {
            return _keyb!!
        }
        _keyb = Builder(name = "KeyB", defaultWidth = 150.0.dp, defaultHeight = 150.0.dp,
                viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(77.803f, 111.56f)
                curveTo(81.003f, 111.56f, 83.691f, 111.176f, 85.867f, 110.408f)
                curveTo(88.043f, 109.64f, 89.771f, 108.616f, 91.051f, 107.336f)
                curveTo(92.374f, 106.056f, 93.312f, 104.563f, 93.867f, 102.856f)
                curveTo(94.464f, 101.149f, 94.763f, 99.336f, 94.763f, 97.416f)
                curveTo(94.763f, 95.411f, 94.443f, 93.619f, 93.803f, 92.04f)
                curveTo(93.163f, 90.419f, 92.16f, 89.053f, 90.795f, 87.944f)
                curveTo(89.43f, 86.792f, 87.659f, 85.917f, 85.483f, 85.32f)
                curveTo(83.35f, 84.723f, 80.768f, 84.424f, 77.739f, 84.424f)
                horizontalLineTo(60.395f)
                verticalLineTo(111.56f)
                horizontalLineTo(77.803f)
                close()
                moveTo(60.395f, 45.768f)
                verticalLineTo(72.52f)
                horizontalLineTo(74.091f)
                curveTo(79.979f, 72.52f, 84.416f, 71.453f, 87.403f, 69.32f)
                curveTo(90.432f, 67.187f, 91.947f, 63.795f, 91.947f, 59.144f)
                curveTo(91.947f, 54.323f, 90.582f, 50.888f, 87.851f, 48.84f)
                curveTo(85.12f, 46.792f, 80.854f, 45.768f, 75.051f, 45.768f)
                horizontalLineTo(60.395f)
                close()
                moveTo(75.051f, 32.456f)
                curveTo(81.11f, 32.456f, 86.294f, 33.032f, 90.603f, 34.184f)
                curveTo(94.912f, 35.336f, 98.432f, 36.979f, 101.163f, 39.112f)
                curveTo(103.936f, 41.245f, 105.963f, 43.827f, 107.243f, 46.856f)
                curveTo(108.523f, 49.885f, 109.163f, 53.299f, 109.163f, 57.096f)
                curveTo(109.163f, 59.272f, 108.843f, 61.363f, 108.203f, 63.368f)
                curveTo(107.563f, 65.331f, 106.56f, 67.187f, 105.195f, 68.936f)
                curveTo(103.872f, 70.643f, 102.166f, 72.2f, 100.075f, 73.608f)
                curveTo(98.027f, 75.016f, 95.574f, 76.211f, 92.715f, 77.192f)
                curveTo(105.43f, 80.051f, 111.787f, 86.92f, 111.787f, 97.8f)
                curveTo(111.787f, 101.725f, 111.04f, 105.352f, 109.547f, 108.68f)
                curveTo(108.054f, 112.008f, 105.878f, 114.888f, 103.019f, 117.32f)
                curveTo(100.16f, 119.709f, 96.64f, 121.587f, 92.459f, 122.952f)
                curveTo(88.278f, 124.317f, 83.499f, 125.0f, 78.123f, 125.0f)
                horizontalLineTo(43.179f)
                verticalLineTo(32.456f)
                horizontalLineTo(75.051f)
                close()
            }
        }
        .build()
        return _keyb!!
    }

private var _keyb: ImageVector? = null
