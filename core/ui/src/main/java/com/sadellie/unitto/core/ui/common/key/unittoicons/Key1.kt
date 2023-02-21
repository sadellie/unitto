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
                moveTo(103.027f, 116.296f)
                verticalLineTo(125.0f)
                horizontalLineTo(53.875f)
                verticalLineTo(116.296f)
                horizontalLineTo(73.523f)
                verticalLineTo(53.768f)
                curveTo(73.523f, 51.891f, 73.587f, 49.992f, 73.715f, 48.072f)
                lineTo(57.395f, 62.088f)
                curveTo(56.84f, 62.557f, 56.286f, 62.856f, 55.731f, 62.984f)
                curveTo(55.176f, 63.069f, 54.664f, 63.069f, 54.195f, 62.984f)
                curveTo(53.726f, 62.899f, 53.278f, 62.728f, 52.851f, 62.472f)
                curveTo(52.467f, 62.216f, 52.168f, 61.939f, 51.955f, 61.64f)
                lineTo(48.371f, 56.712f)
                lineTo(75.699f, 33.096f)
                horizontalLineTo(84.979f)
                verticalLineTo(116.296f)
                horizontalLineTo(103.027f)
                close()
            }
        }
        .build()
        return _key1!!
    }

private var _key1: ImageVector? = null
