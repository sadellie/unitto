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

val @receiver:Suppress("UNUSED") UnittoIcons.RightBracket: ImageVector
    get() {
        if (_rightbracket != null) {
            return _rightbracket!!
        }
        _rightbracket = Builder(name = "Rightbracket", defaultWidth = 124.0.dp, defaultHeight =
                124.0.dp, viewportWidth = 124.0f, viewportHeight = 124.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(62.193f, 62.0f)
                curveTo(62.193f, 56.004f, 61.451f, 50.137f, 59.966f, 44.398f)
                curveTo(58.482f, 38.688f, 56.326f, 33.306f, 53.5f, 28.253f)
                curveTo(53.243f, 27.739f, 53.086f, 27.282f, 53.029f, 26.882f)
                curveTo(52.971f, 26.483f, 53.0f, 26.14f, 53.114f, 25.854f)
                curveTo(53.228f, 25.54f, 53.4f, 25.283f, 53.628f, 25.083f)
                curveTo(53.828f, 24.884f, 54.056f, 24.698f, 54.313f, 24.527f)
                lineTo(58.382f, 22.0f)
                curveTo(60.523f, 25.283f, 62.336f, 28.552f, 63.821f, 31.807f)
                curveTo(65.305f, 35.034f, 66.519f, 38.288f, 67.461f, 41.572f)
                curveTo(68.403f, 44.884f, 69.089f, 48.224f, 69.517f, 51.593f)
                curveTo(69.945f, 54.991f, 70.159f, 58.46f, 70.159f, 62.0f)
                curveTo(70.159f, 65.512f, 69.945f, 68.952f, 69.517f, 72.321f)
                curveTo(69.089f, 75.719f, 68.403f, 79.059f, 67.461f, 82.343f)
                curveTo(66.519f, 85.655f, 65.305f, 88.938f, 63.821f, 92.193f)
                curveTo(62.336f, 95.448f, 60.523f, 98.717f, 58.382f, 102.0f)
                lineTo(54.313f, 99.473f)
                curveTo(54.056f, 99.302f, 53.828f, 99.116f, 53.628f, 98.916f)
                curveTo(53.4f, 98.717f, 53.228f, 98.46f, 53.114f, 98.146f)
                curveTo(53.0f, 97.86f, 52.971f, 97.518f, 53.029f, 97.118f)
                curveTo(53.086f, 96.718f, 53.243f, 96.261f, 53.5f, 95.747f)
                curveTo(56.326f, 90.665f, 58.482f, 85.269f, 59.966f, 79.559f)
                curveTo(61.451f, 73.849f, 62.193f, 67.996f, 62.193f, 62.0f)
                close()
            }
        }
        .build()
        return _rightbracket!!
    }

private var _rightbracket: ImageVector? = null
