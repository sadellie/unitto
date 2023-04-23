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

val @receiver:Suppress("UNUSED") UnittoIcons.Key4: ImageVector
    get() {
        if (_key4 != null) {
            return _key4!!
        }
        _key4 = Builder(name = "Key4", defaultWidth = 124.0.dp, defaultHeight = 124.0.dp,
                viewportWidth = 124.0f, viewportHeight = 124.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(71.481f, 72.757f)
                verticalLineTo(39.763f)
                curveTo(71.481f, 38.795f, 71.518f, 37.734f, 71.592f, 36.581f)
                curveTo(71.667f, 35.427f, 71.778f, 34.255f, 71.927f, 33.063f)
                lineTo(42.786f, 72.757f)
                horizontalLineTo(71.481f)
                close()
                moveTo(92.304f, 72.757f)
                verticalLineTo(78.451f)
                curveTo(92.304f, 79.046f, 92.118f, 79.549f, 91.746f, 79.958f)
                curveTo(91.411f, 80.368f, 90.871f, 80.572f, 90.127f, 80.572f)
                horizontalLineTo(80.19f)
                verticalLineTo(101.619f)
                horizontalLineTo(71.481f)
                verticalLineTo(80.572f)
                horizontalLineTo(35.975f)
                curveTo(35.23f, 80.572f, 34.579f, 80.368f, 34.021f, 79.958f)
                curveTo(33.5f, 79.512f, 33.165f, 78.972f, 33.016f, 78.339f)
                lineTo(32.011f, 73.259f)
                lineTo(70.922f, 21.619f)
                horizontalLineTo(80.19f)
                verticalLineTo(72.757f)
                horizontalLineTo(92.304f)
                close()
            }
        }
        .build()
        return _key4!!
    }

private var _key4: ImageVector? = null
