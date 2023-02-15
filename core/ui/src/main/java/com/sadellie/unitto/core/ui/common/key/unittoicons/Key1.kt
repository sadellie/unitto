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

val @receiver:Suppress("UNUSED") UnittoIcons.Key1: ImageVector
    get() {
        if (_key1 != null) {
            return _key1!!
        }
        _key1 = Builder(name = "Key1", defaultWidth = 150.0.dp, defaultHeight = 150.0.dp,
                viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(104.115f, 113.16f)
                verticalLineTo(125.0f)
                horizontalLineTo(52.915f)
                verticalLineTo(113.16f)
                horizontalLineTo(71.667f)
                verticalLineTo(59.208f)
                curveTo(71.667f, 57.075f, 71.731f, 54.877f, 71.859f, 52.616f)
                lineTo(58.547f, 63.752f)
                curveTo(57.779f, 64.349f, 57.011f, 64.733f, 56.243f, 64.904f)
                curveTo(55.518f, 65.032f, 54.814f, 65.032f, 54.131f, 64.904f)
                curveTo(53.491f, 64.776f, 52.915f, 64.563f, 52.403f, 64.264f)
                curveTo(51.891f, 63.923f, 51.507f, 63.56f, 51.251f, 63.176f)
                lineTo(46.259f, 56.328f)
                lineTo(74.483f, 32.328f)
                horizontalLineTo(87.475f)
                verticalLineTo(113.16f)
                horizontalLineTo(104.115f)
                close()
            }
        }
        .build()
        return _key1!!
    }

private var _key1: ImageVector? = null
