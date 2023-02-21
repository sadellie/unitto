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
                moveTo(68.82f, 140.488f)
                curveTo(68.479f, 140.189f, 68.244f, 139.912f, 68.116f, 139.656f)
                curveTo(67.988f, 139.4f, 67.924f, 139.08f, 67.924f, 138.696f)
                curveTo(67.924f, 138.397f, 68.009f, 138.099f, 68.18f, 137.8f)
                curveTo(68.393f, 137.501f, 68.628f, 137.224f, 68.884f, 136.968f)
                curveTo(69.311f, 136.499f, 69.844f, 135.88f, 70.484f, 135.112f)
                curveTo(71.167f, 134.344f, 71.849f, 133.427f, 72.532f, 132.36f)
                curveTo(73.215f, 131.336f, 73.833f, 130.184f, 74.388f, 128.904f)
                curveTo(74.985f, 127.667f, 75.412f, 126.344f, 75.668f, 124.936f)
                curveTo(75.54f, 124.979f, 75.391f, 125.0f, 75.22f, 125.0f)
                curveTo(75.092f, 125.0f, 74.964f, 125.0f, 74.836f, 125.0f)
                curveTo(72.617f, 125.0f, 70.804f, 124.275f, 69.396f, 122.824f)
                curveTo(68.031f, 121.331f, 67.348f, 119.432f, 67.348f, 117.128f)
                curveTo(67.348f, 115.123f, 68.031f, 113.416f, 69.396f, 112.008f)
                curveTo(70.804f, 110.6f, 72.66f, 109.896f, 74.964f, 109.896f)
                curveTo(76.244f, 109.896f, 77.375f, 110.131f, 78.356f, 110.6f)
                curveTo(79.337f, 111.069f, 80.148f, 111.731f, 80.788f, 112.584f)
                curveTo(81.471f, 113.395f, 81.983f, 114.355f, 82.324f, 115.464f)
                curveTo(82.665f, 116.531f, 82.836f, 117.704f, 82.836f, 118.984f)
                curveTo(82.836f, 120.904f, 82.559f, 122.909f, 82.004f, 125.0f)
                curveTo(81.492f, 127.048f, 80.703f, 129.075f, 79.636f, 131.08f)
                curveTo(78.612f, 133.128f, 77.353f, 135.112f, 75.86f, 137.032f)
                curveTo(74.367f, 138.952f, 72.66f, 140.723f, 70.74f, 142.344f)
                lineTo(68.82f, 140.488f)
                close()
            }
        }
        .build()
        return _comma!!
    }

private var _comma: ImageVector? = null
