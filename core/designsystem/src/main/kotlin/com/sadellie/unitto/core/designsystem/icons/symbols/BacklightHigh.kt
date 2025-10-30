package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.BacklightHigh: ImageVector
  get() {
    if (_BacklightHigh != null) {
      return _BacklightHigh!!
    }
    _BacklightHigh =
      ImageVector.Builder(
          name = "BacklightHigh",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 960f,
          viewportHeight = 960f,
        )
        .apply {
          path(fill = SolidColor(Color(0xFFE3E3E3))) {
            moveTo(80f, 600f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            reflectiveQuadTo(40f, 560f)
            quadToRelative(0f, -17f, 11.5f, -28.5f)
            reflectiveQuadTo(80f, 520f)
            horizontalLineToRelative(80f)
            quadToRelative(17f, 0f, 28.5f, 11.5f)
            reflectiveQuadTo(200f, 560f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(160f, 600f)
            lineTo(80f, 600f)
            close()
            moveTo(282f, 362f)
            quadToRelative(-11f, 11f, -28f, 11f)
            reflectiveQuadToRelative(-28f, -11f)
            lineToRelative(-57f, -57f)
            quadToRelative(-11f, -11f, -11f, -27.5f)
            reflectiveQuadToRelative(11f, -28.5f)
            quadToRelative(12f, -12f, 28f, -12f)
            reflectiveQuadToRelative(28f, 12f)
            lineToRelative(57f, 57f)
            quadToRelative(11f, 11f, 11f, 28f)
            reflectiveQuadToRelative(-11f, 28f)
            close()
            moveTo(340f, 720f)
            quadToRelative(-25f, 0f, -42.5f, -17.5f)
            reflectiveQuadTo(280f, 660f)
            quadToRelative(0f, -25f, 17.5f, -42.5f)
            reflectiveQuadTo(340f, 600f)
            horizontalLineToRelative(280f)
            quadToRelative(25f, 0f, 42.5f, 17.5f)
            reflectiveQuadTo(680f, 660f)
            quadToRelative(0f, 25f, -17.5f, 42.5f)
            reflectiveQuadTo(620f, 720f)
            lineTo(340f, 720f)
            close()
            moveTo(480f, 280f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            reflectiveQuadTo(440f, 240f)
            verticalLineToRelative(-120f)
            quadToRelative(0f, -17f, 11.5f, -28.5f)
            reflectiveQuadTo(480f, 80f)
            quadToRelative(17f, 0f, 28.5f, 11.5f)
            reflectiveQuadTo(520f, 120f)
            verticalLineToRelative(120f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(480f, 280f)
            close()
            moveTo(678f, 362f)
            quadToRelative(-11f, -11f, -11f, -28f)
            reflectiveQuadToRelative(11f, -28f)
            lineToRelative(57f, -57f)
            quadToRelative(11f, -11f, 27.5f, -11f)
            reflectiveQuadToRelative(28.5f, 11f)
            quadToRelative(12f, 12f, 12f, 28f)
            reflectiveQuadToRelative(-12f, 28f)
            lineToRelative(-57f, 57f)
            quadToRelative(-11f, 11f, -28f, 11f)
            reflectiveQuadToRelative(-28f, -11f)
            close()
            moveTo(800f, 600f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            reflectiveQuadTo(760f, 560f)
            quadToRelative(0f, -17f, 11.5f, -28.5f)
            reflectiveQuadTo(800f, 520f)
            horizontalLineToRelative(80f)
            quadToRelative(17f, 0f, 28.5f, 11.5f)
            reflectiveQuadTo(920f, 560f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(880f, 600f)
            horizontalLineToRelative(-80f)
            close()
          }
        }
        .build()

    return _BacklightHigh!!
  }

@Suppress("ObjectPropertyName") private var _BacklightHigh: ImageVector? = null
