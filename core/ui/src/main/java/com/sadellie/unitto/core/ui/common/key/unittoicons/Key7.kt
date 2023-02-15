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

val @receiver:Suppress("UNUSED") UnittoIcons.Key7: ImageVector
    get() {
        if (_key7 != null) {
            return _key7!!
        }
        _key7 = Builder(name = "Key7", defaultWidth = 150.0.dp, defaultHeight = 150.0.dp,
                viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(107.763f, 32.456f)
                verticalLineTo(39.304f)
                curveTo(107.763f, 41.352f, 107.55f, 43.016f, 107.123f, 44.296f)
                curveTo(106.696f, 45.533f, 106.27f, 46.579f, 105.843f, 47.432f)
                lineTo(70.771f, 119.624f)
                curveTo(70.046f, 121.117f, 69.022f, 122.397f, 67.699f, 123.464f)
                curveTo(66.376f, 124.488f, 64.606f, 125.0f, 62.387f, 125.0f)
                horizontalLineTo(50.931f)
                lineTo(86.707f, 54.28f)
                curveTo(87.518f, 52.829f, 88.307f, 51.507f, 89.075f, 50.312f)
                curveTo(89.886f, 49.075f, 90.782f, 47.901f, 91.763f, 46.792f)
                horizontalLineTo(47.475f)
                curveTo(46.494f, 46.792f, 45.64f, 46.429f, 44.915f, 45.704f)
                curveTo(44.19f, 44.979f, 43.827f, 44.125f, 43.827f, 43.144f)
                verticalLineTo(32.456f)
                horizontalLineTo(107.763f)
                close()
            }
        }
        .build()
        return _key7!!
    }

private var _key7: ImageVector? = null
