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

val @receiver:Suppress("UNUSED") UnittoIcons.KeyD: ImageVector
    get() {
        if (_keyd != null) {
            return _keyd!!
        }
        _keyd = Builder(name = "KeyD", defaultWidth = 150.0.dp, defaultHeight = 150.0.dp,
                viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(118.793f, 78.728f)
                curveTo(118.793f, 85.512f, 117.662f, 91.741f, 115.401f, 97.416f)
                curveTo(113.139f, 103.091f, 109.961f, 107.976f, 105.865f, 112.072f)
                curveTo(101.769f, 116.168f, 96.84f, 119.347f, 91.08f, 121.608f)
                curveTo(85.321f, 123.869f, 78.921f, 125.0f, 71.881f, 125.0f)
                horizontalLineTo(36.617f)
                verticalLineTo(32.456f)
                horizontalLineTo(71.881f)
                curveTo(78.921f, 32.456f, 85.321f, 33.608f, 91.08f, 35.912f)
                curveTo(96.84f, 38.173f, 101.769f, 41.352f, 105.865f, 45.448f)
                curveTo(109.961f, 49.501f, 113.139f, 54.365f, 115.401f, 60.04f)
                curveTo(117.662f, 65.715f, 118.793f, 71.944f, 118.793f, 78.728f)
                close()
                moveTo(101.129f, 78.728f)
                curveTo(101.129f, 73.651f, 100.446f, 69.107f, 99.08f, 65.096f)
                curveTo(97.758f, 61.043f, 95.816f, 57.629f, 93.257f, 54.856f)
                curveTo(90.739f, 52.04f, 87.667f, 49.885f, 84.04f, 48.392f)
                curveTo(80.456f, 46.899f, 76.403f, 46.152f, 71.881f, 46.152f)
                horizontalLineTo(53.896f)
                verticalLineTo(111.304f)
                horizontalLineTo(71.881f)
                curveTo(76.403f, 111.304f, 80.456f, 110.557f, 84.04f, 109.064f)
                curveTo(87.667f, 107.571f, 90.739f, 105.437f, 93.257f, 102.664f)
                curveTo(95.816f, 99.848f, 97.758f, 96.435f, 99.08f, 92.424f)
                curveTo(100.446f, 88.371f, 101.129f, 83.805f, 101.129f, 78.728f)
                close()
            }
        }
        .build()
        return _keyd!!
    }

private var _keyd: ImageVector? = null
