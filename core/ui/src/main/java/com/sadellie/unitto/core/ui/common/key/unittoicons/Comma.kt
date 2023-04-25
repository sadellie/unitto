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
        _comma = Builder(name = "Comma", defaultWidth = 124.0.dp, defaultHeight = 124.0.dp,
                viewportWidth = 124.0f, viewportHeight = 124.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(56.356f, 83.156f)
                curveTo(56.041f, 82.881f, 55.825f, 82.625f, 55.707f, 82.389f)
                curveTo(55.589f, 82.154f, 55.53f, 81.859f, 55.53f, 81.505f)
                curveTo(55.53f, 81.23f, 55.609f, 80.955f, 55.766f, 80.68f)
                curveTo(55.963f, 80.405f, 56.179f, 80.149f, 56.415f, 79.914f)
                curveTo(56.808f, 79.481f, 57.299f, 78.912f, 57.888f, 78.204f)
                curveTo(58.517f, 77.497f, 59.146f, 76.652f, 59.775f, 75.67f)
                curveTo(60.403f, 74.727f, 60.973f, 73.665f, 61.484f, 72.487f)
                curveTo(62.034f, 71.347f, 62.427f, 70.129f, 62.663f, 68.832f)
                curveTo(62.545f, 68.871f, 62.407f, 68.891f, 62.25f, 68.891f)
                curveTo(62.132f, 68.891f, 62.014f, 68.891f, 61.896f, 68.891f)
                curveTo(59.853f, 68.891f, 58.183f, 68.223f, 56.886f, 66.887f)
                curveTo(55.629f, 65.511f, 55.0f, 63.763f, 55.0f, 61.641f)
                curveTo(55.0f, 59.794f, 55.629f, 58.222f, 56.886f, 56.925f)
                curveTo(58.183f, 55.628f, 59.892f, 54.98f, 62.014f, 54.98f)
                curveTo(63.193f, 54.98f, 64.235f, 55.196f, 65.138f, 55.628f)
                curveTo(66.042f, 56.061f, 66.789f, 56.67f, 67.378f, 57.456f)
                curveTo(68.007f, 58.202f, 68.479f, 59.086f, 68.793f, 60.108f)
                curveTo(69.107f, 61.091f, 69.265f, 62.171f, 69.265f, 63.35f)
                curveTo(69.265f, 65.118f, 69.009f, 66.965f, 68.498f, 68.891f)
                curveTo(68.027f, 70.777f, 67.3f, 72.644f, 66.317f, 74.491f)
                curveTo(65.374f, 76.377f, 64.215f, 78.204f, 62.84f, 79.973f)
                curveTo(61.464f, 81.741f, 59.892f, 83.372f, 58.124f, 84.865f)
                lineTo(56.356f, 83.156f)
                close()
            }
        }
        .build()
        return _comma!!
    }

private var _comma: ImageVector? = null
