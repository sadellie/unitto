package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.WeightFill: ImageVector
  get() {
    if (_WeightFill != null) {
      return _WeightFill!!
    }
    _WeightFill =
      ImageVector.Builder(
          name = "WeightFill",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 960f,
          viewportHeight = 960f,
        )
        .apply {
          path(fill = SolidColor(Color(0xFFE3E3E3))) {
            moveTo(480f, 280f)
            quadToRelative(17f, 0f, 28.5f, -11.5f)
            reflectiveQuadTo(520f, 240f)
            quadToRelative(0f, -17f, -11.5f, -28.5f)
            reflectiveQuadTo(480f, 200f)
            quadToRelative(-17f, 0f, -28.5f, 11.5f)
            reflectiveQuadTo(440f, 240f)
            quadToRelative(0f, 17f, 11.5f, 28.5f)
            reflectiveQuadTo(480f, 280f)
            close()
            moveTo(593f, 280f)
            horizontalLineToRelative(70f)
            quadToRelative(30f, 0f, 52f, 20f)
            reflectiveQuadToRelative(27f, 49f)
            lineToRelative(57f, 400f)
            quadToRelative(5f, 36f, -18.5f, 63.5f)
            reflectiveQuadTo(720f, 840f)
            lineTo(240f, 840f)
            quadToRelative(-37f, 0f, -60.5f, -27.5f)
            reflectiveQuadTo(161f, 749f)
            lineToRelative(57f, -400f)
            quadToRelative(5f, -29f, 27f, -49f)
            reflectiveQuadToRelative(52f, -20f)
            horizontalLineToRelative(70f)
            quadToRelative(-3f, -10f, -5f, -19.5f)
            reflectiveQuadToRelative(-2f, -20.5f)
            quadToRelative(0f, -50f, 35f, -85f)
            reflectiveQuadToRelative(85f, -35f)
            quadToRelative(50f, 0f, 85f, 35f)
            reflectiveQuadToRelative(35f, 85f)
            quadToRelative(0f, 11f, -2f, 20.5f)
            reflectiveQuadToRelative(-5f, 19.5f)
            close()
          }
        }
        .build()

    return _WeightFill!!
  }

@Suppress("ObjectPropertyName") private var _WeightFill: ImageVector? = null
