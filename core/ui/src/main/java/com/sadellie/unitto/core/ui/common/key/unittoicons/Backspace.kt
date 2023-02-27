package com.sadellie.unitto.core.ui.common.key.unittoicons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.ui.common.key.UnittoIcons

val @receiver:Suppress("UNUSED") UnittoIcons.Backspace: ImageVector
    get() {
        if (_backspace != null) {
            return _backspace!!
        }
        _backspace = Builder(name = "Backspace", defaultWidth = 150.0.dp, defaultHeight = 150.0.dp,
            viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            group {
                path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                    moveTo(70.0f, 99.063f)
                    lineTo(87.5f, 81.563f)
                    lineTo(105.0f, 99.063f)
                    lineTo(111.719f, 92.344f)
                    lineTo(94.063f, 75.0f)
                    lineTo(111.406f, 57.656f)
                    lineTo(104.688f, 50.938f)
                    lineTo(87.5f, 68.438f)
                    lineTo(70.0f, 50.938f)
                    lineTo(63.281f, 57.656f)
                    lineTo(80.938f, 75.0f)
                    lineTo(63.281f, 92.344f)
                    lineTo(70.0f, 99.063f)
                    close()
                    moveTo(18.75f, 75.0f)
                    lineTo(45.156f, 37.656f)
                    curveTo(46.51f, 35.781f, 48.125f, 34.245f, 50.0f, 33.047f)
                    curveTo(51.875f, 31.849f, 53.958f, 31.25f, 56.25f, 31.25f)
                    horizontalLineTo(121.875f)
                    curveTo(124.453f, 31.25f, 126.66f, 32.168f, 128.496f, 34.004f)
                    curveTo(130.332f, 35.84f, 131.25f, 38.047f, 131.25f, 40.625f)
                    verticalLineTo(109.375f)
                    curveTo(131.25f, 111.953f, 130.332f, 114.16f, 128.496f, 115.996f)
                    curveTo(126.66f, 117.832f, 124.453f, 118.75f, 121.875f, 118.75f)
                    horizontalLineTo(56.25f)
                    curveTo(53.958f, 118.75f, 51.875f, 118.151f, 50.0f, 116.953f)
                    curveTo(48.125f, 115.755f, 46.51f, 114.219f, 45.156f, 112.344f)
                    lineTo(18.75f, 75.0f)
                    close()
                    moveTo(30.469f, 75.0f)
                    lineTo(54.531f, 109.375f)
                    horizontalLineTo(121.875f)
                    verticalLineTo(40.625f)
                    horizontalLineTo(54.531f)
                    lineTo(30.469f, 75.0f)
                    close()
                }
            }
        }
            .build()
        return _backspace!!
    }

private var _backspace: ImageVector? = null
