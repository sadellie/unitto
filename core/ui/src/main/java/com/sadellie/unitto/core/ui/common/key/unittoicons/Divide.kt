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

val @receiver:Suppress("UNUSED") UnittoIcons.Divide: ImageVector
    get() {
        if (_divide != null) {
            return _divide!!
        }
        _divide = Builder(name = "Divide", defaultWidth = 150.0.dp, defaultHeight = 150.0.dp,
                viewportWidth = 150.0f, viewportHeight = 150.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(43.763f, 76.168f)
                horizontalLineTo(106.163f)
                verticalLineTo(87.88f)
                horizontalLineTo(43.763f)
                verticalLineTo(76.168f)
                close()
                moveTo(65.203f, 59.272f)
                curveTo(65.203f, 57.949f, 65.438f, 56.691f, 65.907f, 55.496f)
                curveTo(66.419f, 54.301f, 67.102f, 53.277f, 67.955f, 52.424f)
                curveTo(68.851f, 51.571f, 69.896f, 50.888f, 71.091f, 50.376f)
                curveTo(72.286f, 49.864f, 73.566f, 49.608f, 74.931f, 49.608f)
                curveTo(76.254f, 49.608f, 77.512f, 49.864f, 78.707f, 50.376f)
                curveTo(79.902f, 50.888f, 80.926f, 51.571f, 81.779f, 52.424f)
                curveTo(82.675f, 53.277f, 83.379f, 54.301f, 83.891f, 55.496f)
                curveTo(84.403f, 56.691f, 84.659f, 57.949f, 84.659f, 59.272f)
                curveTo(84.659f, 60.637f, 84.403f, 61.917f, 83.891f, 63.112f)
                curveTo(83.379f, 64.264f, 82.675f, 65.267f, 81.779f, 66.12f)
                curveTo(80.926f, 66.973f, 79.902f, 67.656f, 78.707f, 68.168f)
                curveTo(77.512f, 68.637f, 76.254f, 68.872f, 74.931f, 68.872f)
                curveTo(73.566f, 68.872f, 72.286f, 68.637f, 71.091f, 68.168f)
                curveTo(69.896f, 67.656f, 68.851f, 66.973f, 67.955f, 66.12f)
                curveTo(67.102f, 65.267f, 66.419f, 64.264f, 65.907f, 63.112f)
                curveTo(65.438f, 61.917f, 65.203f, 60.637f, 65.203f, 59.272f)
                close()
                moveTo(65.203f, 104.84f)
                curveTo(65.203f, 103.517f, 65.438f, 102.259f, 65.907f, 101.064f)
                curveTo(66.419f, 99.869f, 67.102f, 98.845f, 67.955f, 97.992f)
                curveTo(68.851f, 97.139f, 69.896f, 96.456f, 71.091f, 95.944f)
                curveTo(72.286f, 95.432f, 73.566f, 95.176f, 74.931f, 95.176f)
                curveTo(76.254f, 95.176f, 77.512f, 95.432f, 78.707f, 95.944f)
                curveTo(79.902f, 96.456f, 80.926f, 97.139f, 81.779f, 97.992f)
                curveTo(82.675f, 98.845f, 83.379f, 99.869f, 83.891f, 101.064f)
                curveTo(84.403f, 102.259f, 84.659f, 103.517f, 84.659f, 104.84f)
                curveTo(84.659f, 106.205f, 84.403f, 107.485f, 83.891f, 108.68f)
                curveTo(83.379f, 109.832f, 82.675f, 110.835f, 81.779f, 111.688f)
                curveTo(80.926f, 112.541f, 79.902f, 113.224f, 78.707f, 113.736f)
                curveTo(77.512f, 114.205f, 76.254f, 114.44f, 74.931f, 114.44f)
                curveTo(73.566f, 114.44f, 72.286f, 114.205f, 71.091f, 113.736f)
                curveTo(69.896f, 113.224f, 68.851f, 112.541f, 67.955f, 111.688f)
                curveTo(67.102f, 110.835f, 66.419f, 109.832f, 65.907f, 108.68f)
                curveTo(65.438f, 107.485f, 65.203f, 106.205f, 65.203f, 104.84f)
                close()
            }
        }
        .build()
        return _divide!!
    }

private var _divide: ImageVector? = null
