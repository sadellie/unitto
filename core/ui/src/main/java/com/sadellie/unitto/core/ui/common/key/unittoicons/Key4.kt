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
val UnittoIcons.Key4: ImageVector
    get() {
        if (_key4 != null) {
            return _key4!!
        }
        _key4 = Builder(name = "Key4", defaultWidth = 170.0.dp, defaultHeight = 170.0.dp,
                viewportWidth = 170.0f, viewportHeight = 170.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(94.895f, 98.878f)
                verticalLineTo(59.872f)
                curveTo(94.895f, 58.728f, 94.939f, 57.474f, 95.027f, 56.11f)
                curveTo(95.115f, 54.746f, 95.247f, 53.36f, 95.423f, 51.952f)
                lineTo(60.971f, 98.878f)
                horizontalLineTo(94.895f)
                close()
                moveTo(119.513f, 98.878f)
                verticalLineTo(105.61f)
                curveTo(119.513f, 106.314f, 119.293f, 106.908f, 118.853f, 107.392f)
                curveTo(118.457f, 107.876f, 117.819f, 108.118f, 116.939f, 108.118f)
                horizontalLineTo(105.191f)
                verticalLineTo(133.0f)
                horizontalLineTo(94.895f)
                verticalLineTo(108.118f)
                horizontalLineTo(52.919f)
                curveTo(52.039f, 108.118f, 51.269f, 107.876f, 50.609f, 107.392f)
                curveTo(49.993f, 106.864f, 49.597f, 106.226f, 49.421f, 105.478f)
                lineTo(48.233f, 99.472f)
                lineTo(94.235f, 38.422f)
                horizontalLineTo(105.191f)
                verticalLineTo(98.878f)
                horizontalLineTo(119.513f)
                close()
            }
        }
        .build()
        return _key4!!
    }

private var _key4: ImageVector? = null
