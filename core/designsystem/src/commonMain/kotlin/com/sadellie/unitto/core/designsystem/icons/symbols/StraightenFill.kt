package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.StraightenFill: ImageVector
  get() {
    if (_StraightenFill != null) {
      return _StraightenFill!!
    }
    _StraightenFill =
      ImageVector.Builder(
          name = "StraightenFill",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 960f,
          viewportHeight = 960f,
        )
        .apply {
          path(fill = SolidColor(Color(0xFFE3E3E3))) {
            moveTo(160f, 720f)
            quadToRelative(-33f, 0f, -56.5f, -23.5f)
            reflectiveQuadTo(80f, 640f)
            verticalLineToRelative(-320f)
            quadToRelative(0f, -33f, 23.5f, -56.5f)
            reflectiveQuadTo(160f, 240f)
            horizontalLineToRelative(120f)
            verticalLineToRelative(200f)
            quadToRelative(0f, 17f, 11.5f, 28.5f)
            reflectiveQuadTo(320f, 480f)
            quadToRelative(17f, 0f, 28.5f, -11.5f)
            reflectiveQuadTo(360f, 440f)
            verticalLineToRelative(-200f)
            horizontalLineToRelative(80f)
            verticalLineToRelative(200f)
            quadToRelative(0f, 17f, 11.5f, 28.5f)
            reflectiveQuadTo(480f, 480f)
            quadToRelative(17f, 0f, 28.5f, -11.5f)
            reflectiveQuadTo(520f, 440f)
            verticalLineToRelative(-200f)
            horizontalLineToRelative(80f)
            verticalLineToRelative(200f)
            quadToRelative(0f, 17f, 11.5f, 28.5f)
            reflectiveQuadTo(640f, 480f)
            quadToRelative(17f, 0f, 28.5f, -11.5f)
            reflectiveQuadTo(680f, 440f)
            verticalLineToRelative(-200f)
            horizontalLineToRelative(120f)
            quadToRelative(33f, 0f, 56.5f, 23.5f)
            reflectiveQuadTo(880f, 320f)
            verticalLineToRelative(320f)
            quadToRelative(0f, 33f, -23.5f, 56.5f)
            reflectiveQuadTo(800f, 720f)
            lineTo(160f, 720f)
            close()
          }
        }
        .build()

    return _StraightenFill!!
  }

@Suppress("ObjectPropertyName") private var _StraightenFill: ImageVector? = null
