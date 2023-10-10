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
val UnittoIcons.Key1: ImageVector
    get() {
        if (_key1 != null) {
            return _key1!!
        }
        _key1 = Builder(name = "Key1", defaultWidth = 170.0.dp, defaultHeight = 170.0.dp,
                viewportWidth = 170.0f, viewportHeight = 170.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(113.903f, 124.024f)
                verticalLineTo(133.0f)
                horizontalLineTo(63.215f)
                verticalLineTo(124.024f)
                horizontalLineTo(83.477f)
                verticalLineTo(59.542f)
                curveTo(83.477f, 57.606f, 83.543f, 55.648f, 83.675f, 53.668f)
                lineTo(66.845f, 68.122f)
                curveTo(66.273f, 68.606f, 65.701f, 68.914f, 65.129f, 69.046f)
                curveTo(64.557f, 69.134f, 64.029f, 69.134f, 63.545f, 69.046f)
                curveTo(63.061f, 68.958f, 62.599f, 68.782f, 62.159f, 68.518f)
                curveTo(61.763f, 68.254f, 61.455f, 67.968f, 61.235f, 67.66f)
                lineTo(57.539f, 62.578f)
                lineTo(85.721f, 38.224f)
                horizontalLineTo(95.291f)
                verticalLineTo(124.024f)
                horizontalLineTo(113.903f)
                close()
            }
        }
        .build()
        return _key1!!
    }

private var _key1: ImageVector? = null
