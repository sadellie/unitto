package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.Speed: ImageVector
  get() {
    if (_Speed != null) {
      return _Speed!!
    }
    _Speed =
      ImageVector.Builder(
          name = "Speed",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 960f,
          viewportHeight = 960f,
        )
        .apply {
          path(fill = SolidColor(Color(0xFFE3E3E3))) {
            moveTo(480f, 643.5f)
            quadToRelative(38f, -0.5f, 56f, -27.5f)
            lineToRelative(169f, -253f)
            quadToRelative(9f, -14f, -2.5f, -25.5f)
            reflectiveQuadTo(677f, 335f)
            lineTo(424f, 504f)
            quadToRelative(-27f, 18f, -28.5f, 55f)
            reflectiveQuadToRelative(22.5f, 61f)
            quadToRelative(24f, 24f, 62f, 23.5f)
            close()
            moveTo(480f, 160f)
            quadToRelative(36f, 0f, 71f, 6f)
            reflectiveQuadToRelative(68f, 19f)
            quadToRelative(16f, 6f, 34f, 22.5f)
            reflectiveQuadToRelative(10f, 31.5f)
            quadToRelative(-8f, 15f, -36f, 20f)
            reflectiveQuadToRelative(-45f, -1f)
            quadToRelative(-25f, -9f, -50.5f, -13.5f)
            reflectiveQuadTo(480f, 240f)
            quadToRelative(-133f, 0f, -226.5f, 93.5f)
            reflectiveQuadTo(160f, 560f)
            quadToRelative(0f, 42f, 11.5f, 83f)
            reflectiveQuadToRelative(32.5f, 77f)
            horizontalLineToRelative(552f)
            quadToRelative(23f, -38f, 33.5f, -79f)
            reflectiveQuadToRelative(10.5f, -85f)
            quadToRelative(0f, -26f, -4.5f, -51f)
            reflectiveQuadTo(782f, 456f)
            quadToRelative(-6f, -17f, -2f, -33f)
            reflectiveQuadToRelative(18f, -27f)
            quadToRelative(13f, -10f, 28.5f, -6f)
            reflectiveQuadToRelative(21.5f, 18f)
            quadToRelative(15f, 35f, 23f, 71.5f)
            reflectiveQuadToRelative(9f, 74.5f)
            quadToRelative(1f, 57f, -13f, 109f)
            reflectiveQuadToRelative(-41f, 99f)
            quadToRelative(-11f, 18f, -30f, 28f)
            reflectiveQuadToRelative(-40f, 10f)
            lineTo(204f, 800f)
            quadToRelative(-21f, 0f, -40f, -10f)
            reflectiveQuadToRelative(-30f, -28f)
            quadToRelative(-26f, -45f, -40f, -95.5f)
            reflectiveQuadTo(80f, 560f)
            quadToRelative(0f, -83f, 31.5f, -155.5f)
            reflectiveQuadToRelative(86f, -127f)
            quadTo(252f, 223f, 325f, 191.5f)
            reflectiveQuadTo(480f, 160f)
            close()
            moveTo(487f, 473f)
            close()
          }
        }
        .build()

    return _Speed!!
  }

@Suppress("ObjectPropertyName") private var _Speed: ImageVector? = null
