package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.SpeedFill: ImageVector
  get() {
    if (_SpeedFill != null) {
      return _SpeedFill!!
    }
    _SpeedFill =
      ImageVector.Builder(
          name = "SpeedFill",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 960f,
          viewportHeight = 960f,
        )
        .apply {
          path(fill = SolidColor(Color(0xFFE3E3E3))) {
            moveTo(418f, 620f)
            quadToRelative(25f, 25f, 63f, 23.5f)
            reflectiveQuadToRelative(55f, -27.5f)
            lineToRelative(169f, -253f)
            quadToRelative(9f, -14f, -2.5f, -25.5f)
            reflectiveQuadTo(677f, 335f)
            lineTo(424f, 504f)
            quadToRelative(-26f, 18f, -28.5f, 54.5f)
            reflectiveQuadTo(418f, 620f)
            close()
            moveTo(204f, 800f)
            quadToRelative(-22f, 0f, -40.5f, -9.5f)
            reflectiveQuadTo(134f, 762f)
            quadToRelative(-26f, -47f, -40f, -97.5f)
            reflectiveQuadTo(80f, 560f)
            quadToRelative(0f, -83f, 31.5f, -156f)
            reflectiveQuadTo(197f, 277f)
            quadToRelative(54f, -54f, 127f, -85.5f)
            reflectiveQuadTo(480f, 160f)
            quadToRelative(82f, 0f, 154f, 31f)
            reflectiveQuadToRelative(126f, 84.5f)
            quadToRelative(54f, 53.5f, 86f, 125f)
            reflectiveQuadTo(879f, 554f)
            quadToRelative(1f, 55f, -12.5f, 107.5f)
            reflectiveQuadTo(825f, 762f)
            quadToRelative(-11f, 19f, -29.5f, 28.5f)
            reflectiveQuadTo(755f, 800f)
            lineTo(204f, 800f)
            close()
          }
        }
        .build()

    return _SpeedFill!!
  }

@Suppress("ObjectPropertyName") private var _SpeedFill: ImageVector? = null
