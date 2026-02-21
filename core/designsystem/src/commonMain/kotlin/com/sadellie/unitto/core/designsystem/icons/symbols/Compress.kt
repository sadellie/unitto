package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.Compress: ImageVector
  get() {
    if (_Compress != null) {
      return _Compress!!
    }
    _Compress =
      ImageVector.Builder(
          name = "Compress",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 960f,
          viewportHeight = 960f,
        )
        .apply {
          path(fill = SolidColor(Color(0xFFE3E3E3))) {
            moveTo(200f, 560f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            reflectiveQuadTo(160f, 520f)
            quadToRelative(0f, -17f, 11.5f, -28.5f)
            reflectiveQuadTo(200f, 480f)
            horizontalLineToRelative(560f)
            quadToRelative(17f, 0f, 28.5f, 11.5f)
            reflectiveQuadTo(800f, 520f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(760f, 560f)
            lineTo(200f, 560f)
            close()
            moveTo(200f, 440f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            reflectiveQuadTo(160f, 400f)
            quadToRelative(0f, -17f, 11.5f, -28.5f)
            reflectiveQuadTo(200f, 360f)
            horizontalLineToRelative(560f)
            quadToRelative(17f, 0f, 28.5f, 11.5f)
            reflectiveQuadTo(800f, 400f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(760f, 440f)
            lineTo(200f, 440f)
            close()
            moveTo(451.5f, 868.5f)
            quadTo(440f, 857f, 440f, 840f)
            verticalLineToRelative(-88f)
            lineToRelative(-36f, 36f)
            quadToRelative(-11f, 11f, -28f, 11f)
            reflectiveQuadToRelative(-28f, -11f)
            quadToRelative(-11f, -11f, -11f, -28f)
            reflectiveQuadToRelative(11f, -28f)
            lineToRelative(104f, -104f)
            quadToRelative(6f, -6f, 13f, -8.5f)
            reflectiveQuadToRelative(15f, -2.5f)
            quadToRelative(8f, 0f, 15f, 2.5f)
            reflectiveQuadToRelative(13f, 8.5f)
            lineToRelative(104f, 104f)
            quadToRelative(11f, 11f, 11.5f, 27.5f)
            reflectiveQuadTo(612f, 788f)
            quadToRelative(-11f, 11f, -27.5f, 11.5f)
            reflectiveQuadTo(556f, 789f)
            lineToRelative(-36f, -35f)
            verticalLineToRelative(86f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(480f, 880f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            close()
            moveTo(465f, 300.5f)
            quadToRelative(-7f, -2.5f, -13f, -8.5f)
            lineTo(348f, 188f)
            quadToRelative(-11f, -11f, -11f, -28f)
            reflectiveQuadToRelative(11f, -28f)
            quadToRelative(11f, -11f, 28f, -11f)
            reflectiveQuadToRelative(28f, 11f)
            lineToRelative(36f, 36f)
            verticalLineToRelative(-88f)
            quadToRelative(0f, -17f, 11.5f, -28.5f)
            reflectiveQuadTo(480f, 40f)
            quadToRelative(17f, 0f, 28.5f, 11.5f)
            reflectiveQuadTo(520f, 80f)
            verticalLineToRelative(88f)
            lineToRelative(36f, -36f)
            quadToRelative(11f, -11f, 28f, -11f)
            reflectiveQuadToRelative(28f, 11f)
            quadToRelative(11f, 11f, 11f, 28f)
            reflectiveQuadToRelative(-11f, 28f)
            lineTo(508f, 292f)
            quadToRelative(-6f, 6f, -13f, 8.5f)
            reflectiveQuadToRelative(-15f, 2.5f)
            quadToRelative(-8f, 0f, -15f, -2.5f)
            close()
          }
        }
        .build()

    return _Compress!!
  }

@Suppress("ObjectPropertyName") private var _Compress: ImageVector? = null
