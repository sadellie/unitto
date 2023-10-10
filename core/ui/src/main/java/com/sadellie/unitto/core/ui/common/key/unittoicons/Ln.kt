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
val UnittoIcons.Ln: ImageVector
    get() {
        if (_ln != null) {
            return _ln!!
        }
        _ln = Builder(name = "Ln", defaultWidth = 274.0.dp, defaultHeight = 170.0.dp, viewportWidth
                = 274.0f, viewportHeight = 170.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(107.37f, 35.782f)
                verticalLineTo(133.0f)
                horizontalLineTo(95.622f)
                verticalLineTo(35.782f)
                horizontalLineTo(107.37f)
                close()
                moveTo(134.869f, 75.844f)
                curveTo(136.321f, 74.216f, 137.861f, 72.742f, 139.489f, 71.422f)
                curveTo(141.117f, 70.102f, 142.833f, 68.98f, 144.637f, 68.056f)
                curveTo(146.485f, 67.088f, 148.421f, 66.362f, 150.445f, 65.878f)
                curveTo(152.513f, 65.35f, 154.735f, 65.086f, 157.111f, 65.086f)
                curveTo(160.763f, 65.086f, 163.975f, 65.702f, 166.747f, 66.934f)
                curveTo(169.563f, 68.122f, 171.895f, 69.838f, 173.743f, 72.082f)
                curveTo(175.635f, 74.282f, 177.065f, 76.944f, 178.033f, 80.068f)
                curveTo(179.001f, 83.192f, 179.485f, 86.646f, 179.485f, 90.43f)
                verticalLineTo(133.0f)
                horizontalLineTo(167.671f)
                verticalLineTo(90.43f)
                curveTo(167.671f, 85.37f, 166.505f, 81.454f, 164.173f, 78.682f)
                curveTo(161.885f, 75.866f, 158.387f, 74.458f, 153.679f, 74.458f)
                curveTo(150.203f, 74.458f, 146.947f, 75.294f, 143.911f, 76.966f)
                curveTo(140.919f, 78.638f, 138.147f, 80.904f, 135.595f, 83.764f)
                verticalLineTo(133.0f)
                horizontalLineTo(123.781f)
                verticalLineTo(66.142f)
                horizontalLineTo(130.843f)
                curveTo(132.515f, 66.142f, 133.549f, 66.956f, 133.945f, 68.584f)
                lineTo(134.869f, 75.844f)
                close()
            }
        }
        .build()
        return _ln!!
    }

private var _ln: ImageVector? = null
