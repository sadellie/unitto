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
val UnittoIcons.LeftBracket: ImageVector
    get() {
        if (_leftbracket != null) {
            return _leftbracket!!
        }
        _leftbracket = Builder(name = "Leftbracket", defaultWidth = 124.0.dp, defaultHeight =
                124.0.dp, viewportWidth = 124.0f, viewportHeight = 124.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(60.966f, 62.0f)
                curveTo(60.966f, 67.996f, 61.708f, 73.863f, 63.193f, 79.602f)
                curveTo(64.677f, 85.312f, 66.833f, 90.694f, 69.66f, 95.747f)
                curveTo(69.916f, 96.261f, 70.074f, 96.718f, 70.131f, 97.118f)
                curveTo(70.188f, 97.518f, 70.159f, 97.86f, 70.045f, 98.146f)
                curveTo(69.931f, 98.46f, 69.759f, 98.717f, 69.531f, 98.916f)
                curveTo(69.331f, 99.116f, 69.103f, 99.302f, 68.846f, 99.473f)
                lineTo(64.777f, 102.0f)
                curveTo(62.636f, 98.717f, 60.823f, 95.448f, 59.338f, 92.193f)
                curveTo(57.854f, 88.966f, 56.64f, 85.712f, 55.698f, 82.428f)
                curveTo(54.756f, 79.116f, 54.071f, 75.776f, 53.642f, 72.407f)
                curveTo(53.214f, 69.009f, 53.0f, 65.54f, 53.0f, 62.0f)
                curveTo(53.0f, 58.488f, 53.214f, 55.048f, 53.642f, 51.679f)
                curveTo(54.071f, 48.281f, 54.756f, 44.941f, 55.698f, 41.657f)
                curveTo(56.64f, 38.346f, 57.854f, 35.062f, 59.338f, 31.807f)
                curveTo(60.823f, 28.552f, 62.636f, 25.283f, 64.777f, 22.0f)
                lineTo(68.846f, 24.527f)
                curveTo(69.103f, 24.698f, 69.331f, 24.884f, 69.531f, 25.083f)
                curveTo(69.759f, 25.283f, 69.931f, 25.54f, 70.045f, 25.854f)
                curveTo(70.159f, 26.14f, 70.188f, 26.483f, 70.131f, 26.882f)
                curveTo(70.074f, 27.282f, 69.916f, 27.739f, 69.66f, 28.253f)
                curveTo(66.833f, 33.335f, 64.677f, 38.731f, 63.193f, 44.441f)
                curveTo(61.708f, 50.151f, 60.966f, 56.004f, 60.966f, 62.0f)
                close()
            }
        }
        .build()
        return _leftbracket!!
    }

private var _leftbracket: ImageVector? = null
