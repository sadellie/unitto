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
val UnittoIcons.Comma: ImageVector
    get() {
        if (_comma != null) {
            return _comma!!
        }
        _comma = Builder(
            name = "Comma", defaultWidth = 170.0.dp, defaultHeight = 170.0.dp,
            viewportWidth = 170.0f, viewportHeight = 170.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(77.654f, 101.375f)
                curveTo(77.271f, 101.039f, 77.007f, 100.727f, 76.863f, 100.44f)
                curveTo(76.719f, 100.152f, 76.647f, 99.792f, 76.647f, 99.361f)
                curveTo(76.647f, 99.025f, 76.743f, 98.69f, 76.935f, 98.354f)
                curveTo(77.175f, 98.019f, 77.438f, 97.707f, 77.726f, 97.419f)
                curveTo(78.205f, 96.892f, 78.805f, 96.197f, 79.524f, 95.334f)
                curveTo(80.291f, 94.471f, 81.058f, 93.44f, 81.825f, 92.242f)
                curveTo(82.592f, 91.091f, 83.287f, 89.797f, 83.91f, 88.358f)
                curveTo(84.582f, 86.968f, 85.061f, 85.482f, 85.349f, 83.9f)
                curveTo(85.205f, 83.948f, 85.037f, 83.971f, 84.845f, 83.971f)
                curveTo(84.701f, 83.971f, 84.558f, 83.971f, 84.414f, 83.971f)
                curveTo(81.921f, 83.971f, 79.883f, 83.157f, 78.301f, 81.526f)
                curveTo(76.767f, 79.849f, 76.0f, 77.715f, 76.0f, 75.126f)
                curveTo(76.0f, 72.873f, 76.767f, 70.955f, 78.301f, 69.373f)
                curveTo(79.883f, 67.791f, 81.969f, 67.0f, 84.558f, 67.0f)
                curveTo(85.996f, 67.0f, 87.266f, 67.264f, 88.369f, 67.791f)
                curveTo(89.472f, 68.318f, 90.383f, 69.062f, 91.102f, 70.02f)
                curveTo(91.869f, 70.931f, 92.444f, 72.01f, 92.828f, 73.257f)
                curveTo(93.211f, 74.455f, 93.403f, 75.773f, 93.403f, 77.212f)
                curveTo(93.403f, 79.369f, 93.091f, 81.622f, 92.468f, 83.971f)
                curveTo(91.893f, 86.273f, 91.006f, 88.55f, 89.807f, 90.803f)
                curveTo(88.657f, 93.104f, 87.242f, 95.334f, 85.564f, 97.491f)
                curveTo(83.886f, 99.649f, 81.969f, 101.638f, 79.811f, 103.46f)
                lineTo(77.654f, 101.375f)
                close()
            }
        }
            .build()
        return _comma!!
    }

private var _comma: ImageVector? = null
