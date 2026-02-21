package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.FastForwardFill: ImageVector
  get() {
    if (_FastForwardFill != null) {
      return _FastForwardFill!!
    }
    _FastForwardFill =
      ImageVector.Builder(
          name = "FastForwardFill",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 960f,
          viewportHeight = 960f,
        )
        .apply {
          path(fill = SolidColor(Color(0xFFE3E3E3))) {
            moveTo(100f, 645f)
            verticalLineToRelative(-330f)
            quadToRelative(0f, -18f, 12f, -29f)
            reflectiveQuadToRelative(28f, -11f)
            quadToRelative(5f, 0f, 11f, 1f)
            reflectiveQuadToRelative(11f, 5f)
            lineToRelative(248f, 166f)
            quadToRelative(9f, 6f, 13.5f, 14.5f)
            reflectiveQuadTo(428f, 480f)
            quadToRelative(0f, 10f, -4.5f, 18.5f)
            reflectiveQuadTo(410f, 513f)
            lineTo(162f, 679f)
            quadToRelative(-5f, 4f, -11f, 5f)
            reflectiveQuadToRelative(-11f, 1f)
            quadToRelative(-16f, 0f, -28f, -11f)
            reflectiveQuadToRelative(-12f, -29f)
            close()
            moveTo(500f, 645f)
            verticalLineToRelative(-330f)
            quadToRelative(0f, -18f, 12f, -29f)
            reflectiveQuadToRelative(28f, -11f)
            quadToRelative(5f, 0f, 11f, 1f)
            reflectiveQuadToRelative(11f, 5f)
            lineToRelative(248f, 166f)
            quadToRelative(9f, 6f, 13.5f, 14.5f)
            reflectiveQuadTo(828f, 480f)
            quadToRelative(0f, 10f, -4.5f, 18.5f)
            reflectiveQuadTo(810f, 513f)
            lineTo(562f, 679f)
            quadToRelative(-5f, 4f, -11f, 5f)
            reflectiveQuadToRelative(-11f, 1f)
            quadToRelative(-16f, 0f, -28f, -11f)
            reflectiveQuadToRelative(-12f, -29f)
            close()
          }
        }
        .build()

    return _FastForwardFill!!
  }

@Suppress("ObjectPropertyName") private var _FastForwardFill: ImageVector? = null
